package core;

import java.util.ArrayList;
import java.util.List;

import core.settings.SettingsManager;

public class GameEngine {
    private GameBoard gameBoard;
    private Stopwatch stopwatch;
    private List<TileReadable> latestUpdatedTiles = new ArrayList<>();

    public GameEngine() {
        gameBoard = new GameBoard(SettingsManager.getGameDifficulty());
        stopwatch = new Stopwatch();
    }

    public void resetGame() {
        this.gameBoard = new GameBoard(SettingsManager.getGameDifficulty());
        stopwatch.restart();
        latestUpdatedTiles.clear();
    }

    public void handleLeftClick(int x, int y) {
        latestUpdatedTiles.clear();

        TileReadable clickedTile = getTile(x, y);
        boolean tileFlaggedAfterStart = isGameStarted() && clickedTile.isFlagged();
        if (gameBoard.isGameEnded() || tileFlaggedAfterStart)
            return;

        gameBoard.tileClicked(x, y);

        if (clickedTile.isBomb()) {
            handleBombClicked();
            return;
        }

        addRevealedTilesToLatestUpdated();

        if (isGameStarted() && !stopWatchIsStarted())
            stopwatch.start();

        if (isGameEnded())
            stopwatch.stop();
    }

    public void handleRightClick(int x, int y) {
        latestUpdatedTiles.clear();
        if (!gameBoard.isGameStarted() || gameBoard.isGameEnded())
            return;

        Tile clickedTile = gameBoard.getTile(x, y);
        if (canToggleFlag(clickedTile))
            toggleTileFlag(clickedTile);
    }

    public void handleSpaceBarClick(int x, int y) {
        Tile clickedTile = gameBoard.getTile(x, y);
        boolean tileRevealedAndGameRunning = gameBoard.isGameStarted() && !gameBoard.isGameEnded()
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

    /**
     * Handles the event when a bomb is clicked. This stops the game's stopwatch and
     * updates the list of tiles
     * that were affected by the bomb click. The affected tiles are set to be the
     * list of bomb tiles from the game board.
     */
    private void handleBombClicked() {
        List<Tile> bombTiles = gameBoard.getBombTiles();
        bombTiles.forEach(tile -> tile.reveal());
        latestUpdatedTiles.addAll(bombTiles);

        stopwatch.stop();
    }

    private void toggleTileFlag(Tile tile) {
        tile.toggleFlag();

        if (tile.isFlagged())
            gameBoard.decrementFlagsLeft();
        else
            gameBoard.incrementFlagsLeft();

        latestUpdatedTiles.add(tile);
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

    public boolean isGameStarted() {
        return gameBoard.isGameStarted();
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
