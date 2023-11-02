package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import core.settings.SettingsManager;

public class GameEngine {
    private GameBoard gameBoard;
    private Stopwatch stopwatch;
    private List<TileReadable> latestUpdatedTiles;

    public GameEngine() {
        gameBoard = new GameBoard(SettingsManager.getGameDifficulty());
        stopwatch = new Stopwatch();
        latestUpdatedTiles = new ArrayList<>();
    }

    public void resetGame() {
        this.gameBoard = new GameBoard(SettingsManager.getGameDifficulty());
        stopwatch.restart();
        latestUpdatedTiles = new ArrayList<>();
    }

    public void handleLeftClick(int x, int y) {
        boolean tileFlaggedAfterStart = gameIsStarted() && getTile(x, y).isFlagged();
        if (gameBoard.isGameEnded() || tileFlaggedAfterStart)
            return;

        gameBoard.tileClicked(x, y);
        latestUpdatedTiles = new ArrayList<>();

        TileReadable clickedTile = getTile(x, y);
        if (clickedTile.isBomb()) {
            handleBombClicked();
            return;
        }

        addRevealedTilesToLatestUpdated();

        if (gameIsStarted() && !stopWatchIsStarted())
            stopwatch.start();

        if (isGameEnded())
            stopwatch.stop();
    }

    public void handleRightClick(int x, int y) {
        if (!gameBoard.gameStarted() || gameBoard.isGameEnded())
            return;

        Tile clickedTile = gameBoard.getTile(x, y);
        if (canToggleFlag(clickedTile))
            toggleTileFlag(clickedTile);
    }

    public void handleSpaceBarClick(int x, int y) {
        Tile clickedTile = gameBoard.getTile(x, y);
        boolean tileRevealedAndGameRunning = gameBoard.gameStarted() && !gameBoard.isGameEnded()
                && clickedTile.isRevealed();
        if (!tileRevealedAndGameRunning)
            return;

        List<Tile> neighbors = gameBoard.getNeighborTiles(x, y);
        boolean correctNumberOfFlags = clickedTile.getNumBombsAround() == neighbors.stream().filter(Tile::isFlagged)
                .count();

        // The number of flags around the tile must be equal to the number of bombs
        // around the tile
        if (!correctNumberOfFlags) {
            return;
        }

        // Clicking on all the neighbors of the tile which are not flagged.
        // We need to click all of the non-bomb tiles first, such that all clicked tiles
        // are revealed.
        neighbors.stream().filter(tile -> !tile.isFlagged() && !tile.isBomb())
                .forEach(tile -> handleLeftClick(tile.getX(), tile.getY()));
        neighbors.stream().filter(tile -> !tile.isFlagged() && tile.isBomb())
                .forEach(tile -> handleLeftClick(tile.getX(), tile.getY()));
        addRevealedTilesToLatestUpdated();
    }

    private void handleBombClicked() {
        addBombsToLatestUpdated();
        stopwatch.stop();
    }

    private void toggleTileFlag(Tile tile) {
        tile.toggleFlag();

        if (tile.isFlagged())
            gameBoard.decrementFlagsLeft();
        else
            gameBoard.incrementFlagsLeft();

        this.latestUpdatedTiles = Arrays.asList(tile);
    }

    private boolean canToggleFlag(Tile tile) {
        return !tile.isRevealed() && gameBoard.hasFlagsLeft() && !tile.isFlagged() || tile.isFlagged();
    }

    private void addRevealedTilesToLatestUpdated() {
        for (int i = 0; i < gameBoard.getWidth(); i++) {
            for (int j = 0; j < gameBoard.getHeight(); j++) {
                TileReadable tile = getTile(i, j);
                if (tile.isRevealed())
                    latestUpdatedTiles.add(tile);
            }
        }
    }

    private void addBombsToLatestUpdated() {
        latestUpdatedTiles = new ArrayList<>();
        for (int x = 0; x < gameBoard.getWidth(); x++) {
            for (int y = 0; y < gameBoard.getHeight(); y++) {
                Tile tile = gameBoard.getTile(x, y);
                if (!tile.isBomb())
                    continue;
                if (tile.isFlagged())
                    tile.toggleFlag();

                tile.reveal();
                latestUpdatedTiles.add(tile);
            }
        }
    }

    public boolean stopWatchIsStarted() {
        return stopwatch.isStarted();
    }

    public List<TileReadable> getLatestUpdatedTiles() {
        return new ArrayList<>(latestUpdatedTiles);
    }

    public int getFlagsLeft() {
        return gameBoard.getFlagsLeft();
    }

    public boolean isGameWon() {
        return gameBoard.gameIsWon();
    }

    public boolean isGameLost() {
        return gameBoard.isGameLost();
    }

    public TileReadable getTile(int x, int y) {
        return gameBoard.getTile(x, y);
    }

    public boolean gameIsStarted() {
        return gameBoard.gameStarted();
    }

    public int getTime() {
        return stopwatch.getTime();
    }

    public boolean isGameEnded() {
        return gameBoard.isGameEnded();
    }

    public String getDate() {
        return stopwatch.getDate();
    }

    // For testing
    public HashSet<String> getBombCoords() {
        return gameBoard.getBombCoords();
    }

    public Stopwatch getStopwatch() {
        return stopwatch;
    }

    protected GameBoard getGameBoard() {
        return gameBoard;
    }

    // For testing
    public List<Tile> getNeighborTiles(int x, int y) {
        return gameBoard.getNeighborTiles(x, y);
    }

}
