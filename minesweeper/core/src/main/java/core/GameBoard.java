package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {

    private List<List<Tile>> board = new ArrayList<>();
    private final int width, height;
    private final int numBombs;
    private final int[] startingCoords;
    protected int tilesLeft, flagsLeft;

    public GameBoard(int width, int height, int numBombs) {
        validateInput(width, height, numBombs);

        this.height = height;
        this.width = width;
        this.numBombs = numBombs;
        this.startingCoords = new int[] { -1, -1 };
        this.tilesLeft = height * width;
        this.flagsLeft = numBombs;

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
                newRow.add(new Tile());

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

            boolean validBombTile = !tile.isBomb() && (x != startingCoords[0] || y != startingCoords[1]);
            if (validBombTile) {
                tile.makeBomb();
                incrementNeighborCounts(x, y);
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
        Tile tile = getTile(x, y);

        if (isNewGame()) {
            initializeGame(x, y);
        }
        if (canRevealTile(tile)) {
            revealTileAndAdjacentZerosIfZero(x, y);
        }
    }

    private void initializeGame(int row, int col) {
        setStartingCoords(row, col);
        placeBombs();
    }

    private void revealTileAndAdjacentZerosIfZero(int x, int y) {
        Tile tile = getTile(x, y);
        tile.reveal();
        tilesLeft--;
        if (!tile.hasAdjacentBomb()) {
            revealZeros(x, y);
        }
    }

    private void revealZeros(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (isValidCoordinate(i, j))
                    revealTileAndAdjacentZerosIfZero(i, j);
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
    }

    public Tile getTile(int x, int y) {
        return board.get(y).get(x);
    }

    public boolean gameIsWon() {
        return tilesLeft == numBombs;
    }

    public boolean gameStarted() {
        return tilesLeft != width * height;
    }

    public boolean canRevealTile(Tile tile) {
        return !tile.isRevealed() && !tile.isFlagged();
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height && !getTile(x, y).isRevealed();
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

    public static void main(String[] args) {
        // ### Test ###:

        // Initialization:
        int width = 8, height = 8;
        GameBoard gameBoard = new GameBoard(width, height, 10);
        gameBoard.setStartingCoords(0, 0);

        // Print the Initial GameBoard:
        for (List<Tile> row : gameBoard.board) {
            System.out.println(row);
        }
        System.out.println("\n");

        // Click on a tile
        gameBoard.tileClicked(1, 0);

        // Print the Updated GameBoard:
        for (List<Tile> row : gameBoard.board) {
            System.out.println(row);
        }
    }
}
