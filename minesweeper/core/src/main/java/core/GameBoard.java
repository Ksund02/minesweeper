package core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GameBoard {

    protected List<List<Tile>> board = new ArrayList<>();
    private final int width, height;
    private final int numBombs;
    private final int[] startingCoords;
    private boolean isGameLost;
    private HashSet<String> bombCoords;
    protected int tilesLeft, flagsLeft;

    public GameBoard(int width, int height, int numBombs) {
        validateInput(width, height, numBombs);

        this.height = height;
        this.width = width;
        this.numBombs = numBombs;
        this.startingCoords = new int[] { -1, -1 };
        this.tilesLeft = height * width - numBombs;
        this.flagsLeft = numBombs;

        this.bombCoords = new HashSet<>();

        populateBoardWithTiles();
    }

    private void validateInput(int width, int height, int numBombs) {
        boolean negativeOrZeroArea = width <= 0 || height <= 0;
        boolean negativeAmountOfBombs = numBombs < 0;
        boolean tooManyBombs = numBombs >= width * height;

        if (negativeOrZeroArea)
            throw new IllegalArgumentException(
                    "Width and height must be positive integers");

        if (negativeAmountOfBombs)
            throw new IllegalArgumentException(
                    "Number of bombs cannot be negative");

        if (tooManyBombs)
            throw new IllegalArgumentException(
                    "Cannot have more or equal number of bombs as squares in grid");
    }

    private void populateBoardWithTiles() {
        for (int y = 0; y < height; y++) {
            List<Tile> newRow = new ArrayList<>();
            for (int x = 0; x < width; x++)
                newRow.add(new Tile(x, y));

            board.add(newRow);
        }
    }

    private void setStartingCoords(int x, int y) {
        startingCoords[0] = x;
        startingCoords[1] = y;
    }

    private void placeBombs() {
        Random rand = new Random();
        int bombsPlaced = 0;

        while (bombsPlaced < numBombs) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            Tile tile = getTile(x, y);

            boolean adjacentToStartingX = x >= (startingCoords[0] - 1) && x <= (startingCoords[0] + 1);
            boolean adjacentToStartingY = y >= (startingCoords[1] - 1) && y <= (startingCoords[1] + 1);

            boolean validBombTile = !tile.isBomb() && !(adjacentToStartingX && adjacentToStartingY);
            if (validBombTile) {
                tile.makeBomb();
                incrementNeighborCounts(x, y);
                bombCoords.add(x + "." + y);
                bombsPlaced++;
            }
        }
    }

    private void incrementNeighborCounts(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {

                boolean validCoords = i != -1 && i != width && j != -1 && j != height;
                if (validCoords) {
                    getTile(i, j).incrementNumBombsAround();
                }
            }
        }
    }

    /**
     * <pre>
     * If a tile is clicked:
     * - Checks if it is a new game 
     * - Checks if the tile is already revealed or flagged
     * - Reveals the tile and all tiles around zero-tiles 
     *   (if not revealed or flagged)
     * </pre>
     * 
     * @param x the row that was clicked
     * @param y the column that was clicked
     */
    public void tileClicked(int x, int y) {

        if (isNewGame()) {
            initializeGame(x, y);
        }

        Tile tile = getTile(x, y);

        if (tile.isBomb()) {
            tile.reveal();
            isGameLost = true;
        }

        else if (canRevealTile(tile)) {
            revealTileAndAdjacentIfZero(x, y);
        }
    }

    /**
     * For testing purposes
     * 
     * @param x row
     * @param y column
     */
    void testTileClicked(int x, int y) {
        if (isNewGame()) {
            setStartingCoords(x, y);
        }

        Tile tile = getTile(x, y);

        if (tile.isBomb()) {
            tile.reveal();
            isGameLost = true;
        }

        else if (canRevealTile(tile)) {
            revealTileAndAdjacentIfZero(x, y);
        }
    }

    private void initializeGame(int row, int col) {
        setStartingCoords(row, col);
        placeBombs();
    }

    private void revealTileAndAdjacentIfZero(int x, int y) {
        Tile tile = getTile(x, y);
        tile.reveal();
        tilesLeft--;
        if (!tile.hasAdjacentBomb()) {
            revealAdjacent(x, y);
        }
    }

    private void revealAdjacent(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (isValidCoordinateNotRevealedNotFlagged(i, j)) {
                    revealTileAndAdjacentIfZero(i, j);
                }
            }
        }
    }

    /**
     * Method for testing purposes.
     * 
     * @param gameBoard The custom gameBoard you want to play with.
     */
    protected void setGameboard(List<List<Tile>> gameBoard) {
        this.board = gameBoard;
        List<int[]> bombLocations = findBombLocations();
        placeBombs(bombLocations);
    }

    protected List<int[]> findBombLocations() {
        // I am really not sure about these coordinates, should not try to make some
        // non-square board,
        // since that could quickly become a mess.
        List<int[]> bombLocations = new ArrayList<>();
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                Tile tile = board.get(x).get(y);
                if (tile.isBomb()) {
                    bombLocations.add(new int[] { x, y });
                }
            }
        }
        return bombLocations;
    }

    /**
     * Method for testing purposes.
     * 
     * @param bombLocations The locations of the bombs you want to place, should be
     *                      the same as the ones in the custom gameBoard.
     */
    protected void placeBombs(List<int[]> bombLocations) {
        if (bombLocations.size() != numBombs) {
            throw new IllegalArgumentException(
                    "Number of bombs in custom gameboard does not match numBombs");
        }

        for (int[] location : bombLocations) {
            int x = location[0];
            int y = location[1];
            Tile tile = getTile(x, y);
            tile.makeBomb();
            incrementNeighborCounts(x, y);
        }
    }

    public Tile getTile(int x, int y) {
        return board.get(y).get(x);
    }

    public boolean gameIsWon() {
        return tilesLeft == 0 & !isGameLost;
    }

    public boolean gameStarted() {
        return tilesLeft != width * height - numBombs;
    }

    public boolean canRevealTile(Tile tile) {
        return !tile.isRevealed() && !tile.isFlagged();
    }

    private boolean isValidCoordinateNotRevealedNotFlagged(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height && !getTile(x, y).isRevealed() && !getTile(x, y).isFlagged();
    }

    public boolean isNewGame() {
        return startingCoords[0] == -1;
    }

    public int getFlagsLeft() {
        return flagsLeft;
    }

    public void flagPlaced() {
        flagsLeft--;
    }

    public void flagRemoved() {
        flagsLeft++;
    }

    public boolean hasFlagsLeft() {
        return getFlagsLeft() > 0;
    }

    public boolean isGameEnded() {
        return gameIsWon() || isGameLost();
    }

    public boolean isGameLost() {
        return isGameLost;
    }

    public HashSet<String> getBombCoords() {
        return bombCoords;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public int getTilesLeft() {
        return tilesLeft;
    }
}
