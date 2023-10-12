package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameEngine {
    private GameBoard gameBoard;
    private Stopwatch stopwatch;
    private List<Tile> latestUpdatedTiles;

    public GameEngine(int width, int height, int numBombs) {
        gameBoard = new GameBoard(width, height, numBombs);
        stopwatch = new Stopwatch();
        latestUpdatedTiles = new ArrayList<>();
    }

    public void resetGame(int width, int height, int numBombs) {
        this.gameBoard = new GameBoard(width, height, numBombs);
        stopwatch.restart();
        latestUpdatedTiles = new ArrayList<>();
    }

    public void handleLeftClick(int x, int y) {
        if (gameBoard.isGameEnded())
            return;

        gameBoard.tileClicked(x, y);
        latestUpdatedTiles = new ArrayList<>();

        Tile clickedTile = getTile(x, y);
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
        if (!gameBoard.gameStarted() || isGameLost())
            return;

        Tile clickedTile = gameBoard.getTile(x, y);
        if (clickedTile.isRevealed() || !canToggleFlag(clickedTile))
            return;

        toggleTileFlag(clickedTile);
    }

    private void handleBombClicked() {
        addBombToLatestUpdated();
        stopwatch.stop();
    }

    private void toggleTileFlag(Tile tile) {
        tile.toggleFlag();

        if (tile.isFlagged())
            gameBoard.flagPlaced();
        else
            gameBoard.flagRemoved();

        this.latestUpdatedTiles = Arrays.asList(tile);
    }

    private boolean canToggleFlag(Tile tile) {
        return gameBoard.hasFlagsLeft() && !tile.isFlagged() || tile.isFlagged();
    }

    private void addRevealedTilesToLatestUpdated() {
        for (int i = 0; i < gameBoard.getWidth(); i++) {
            for (int j = 0; j < gameBoard.getHeight(); j++) {
                Tile tile = getTile(i, j);
                if (tile.isRevealed())
                    latestUpdatedTiles.add(tile);
            }
        }
    }

    private void addBombToLatestUpdated() {
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

    public List<Tile> getLatestUpdatedTiles() {
        return latestUpdatedTiles;
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

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Tile getTile(int x, int y) {
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
}
