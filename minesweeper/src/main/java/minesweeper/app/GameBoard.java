package minesweeper.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {

    private final List<List<Tile>> gameBoard = new ArrayList<>();
    private final int width;
    private final int height;
    private final int numBombs;
    private final int[] startingCoords;

    public GameBoard(int width, int height, int numBombs, int[] startingCoords) {
        for (int x = 0; x < width; x++) {
            List<Tile> newColumn = new ArrayList<>();
            for (int y = 0; y < height; y++) {
                newColumn.add(new Tile());
            }
            gameBoard.add(newColumn);
        }
        this.width = width;
        this.height = height;
        this.numBombs = numBombs;
        this.startingCoords = startingCoords;
        placeBombs();
        tileClicked(startingCoords[0], startingCoords[1]);
    }

    public void placeBombs() {
        Random rand = new Random();
        int bombsPlaced = 0;
        while (bombsPlaced < numBombs) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            Tile tile = gameBoard.get(x).get(y);
            if (!tile.isBomb() && (x != startingCoords[0] || y != startingCoords[1])) {
                tile.placeBomb();
                incrementNeighborCounts(tile, x, y);
                bombsPlaced++;
            }
        }
    }

    private void incrementNeighborCounts(Tile tile, int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i != -1 && i != width && j != -1 && j != height) {
                    gameBoard.get(i).get(j).incrementNumBombsAround();
                }
            }
        }
    }

    protected void tileClicked(int x, int y) {
        Tile tile = gameBoard.get(x).get(y);
        if (tile.isRevealed() || tile.isFlagged()) {
            return;
        }
        tile.reveal();
        revealZeros(x, y);
    }

    private void revealZeros(int x, int y) {
        Tile tile = gameBoard.get(x).get(y);
        if (tile.getNumBombsAround() != 0) {
            return;
        }
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i != -1 && i != width && j != -1 && j != height && !gameBoard.get(i).get(j).isRevealed()) {
                    gameBoard.get(i).get(j).reveal();
                    revealZeros(i, j);
                }
            }
        }
    }

    public Tile getTile(int x, int y) {
        return gameBoard.get(x).get(y);
    }

    public void toggleFlag(int x, int y) {
        Tile tile = gameBoard.get(x).get(y);
        if (!tile.isRevealed()) {
            tile.toggleFlag();
        }
    }

    public static void main(String[] args) {
        int width = 8, height = 8;
        GameBoard game = new GameBoard(width, height, 10, new int[]{0, 0});
        for (int i = 0; i < height; i++) {
            System.out.println(game.gameBoard.get(i));
        }
        System.out.println("");
        game.tileClicked(1, 0);
        game.toggleFlag(3, 4);
        game.toggleFlag(1, 0);
        game.toggleFlag(0, 1);
        for (int i = 0; i < height; i++) {
            System.out.println(game.gameBoard.get(i));
        }
    }
}
