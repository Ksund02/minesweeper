package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {

    private final List<List<Tile>> gameBoard = new ArrayList<>();
    private final int width;
    private final int height;
    private final int numBombs;
    private final int[] startingCoords;
    private int tilesLeft;
    private int flagsLeft;

    public GameBoard(int height, int width, int numBombs) {
        for (int y = 0; y < height; y++) {
            List<Tile> newRow = new ArrayList<>();
            for (int x = 0; x < width; x++) {
                newRow.add(new Tile());
            }
            gameBoard.add(newRow);
        }
        this.height = height;
        this.width = width;
        this.numBombs = numBombs;
        this.startingCoords = new int[] { -1, -1 };
        this.tilesLeft = height * width;
        this.flagsLeft = numBombs;
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
                tile.placeBomb();
                incrementNeighborCounts(tile, x, y);
                bombsPlaced++;
            }
        }
    }

    private void incrementNeighborCounts(Tile tile, int x, int y) {
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
        boolean newBoard = startingCoords[0] == -1;
        if (newBoard) {
            setStartingCoords(x, y);
            placeBombs();
        } else if (tile.isRevealed() || tile.isFlagged()) {
            return;
        }
        tile.reveal();
        tilesLeft--;
        revealZeros(x, y);
    }

    public boolean gameIsWon() {
        return tilesLeft == numBombs;
    }

    private void revealZeros(int x, int y) {
        Tile tile = getTile(x, y);
        if (tile.getNumBombsAround() != 0) {
            return;
        }
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                boolean validCoords = i != -1 && i != width && j != -1 && j != height;
                if (validCoords && !getTile(i, j).isRevealed()) {
                    getTile(i, j).reveal();
                    tilesLeft--;
                    revealZeros(i, j);
                }
            }
        }
    }

    public Tile getTile(int x, int y) {
        return gameBoard.get(y).get(x);
    }

    public boolean gameStarted() {
        return tilesLeft != width * height;
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
        int width = 8, height = 8;
        GameBoard game = new GameBoard(width, height, 10);
        game.setStartingCoords(0, 0);
        for (int i = 0; i < height; i++) {
            System.out.println(game.gameBoard.get(i));
        }
        System.out.println("");
        game.tileClicked(1, 0);
        for (int i = 0; i < height; i++) {
            System.out.println(game.gameBoard.get(i));
        }
    }
}
