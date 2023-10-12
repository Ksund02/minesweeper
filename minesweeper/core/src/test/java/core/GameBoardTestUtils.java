package core;

import java.util.ArrayList;
import java.util.List;

public class GameBoardTestUtils {

    public static GameBoard convertCharacterToGameBoard(List<List<Character>> boardWithText) {
        List<List<Tile>> convertedBoard = new ArrayList<>();
        for (List<Character> row : boardWithText) {
            List<Tile> newRow = new ArrayList<>();
            for (char tileChar : row) {
                Tile newTile = new Tile(0, 0);
                if (tileChar == 'B')
                    newTile.makeBomb();
                newRow.add(newTile);
            }
            convertedBoard.add(newRow);
        }

        GameBoard gameBoard = new GameBoard(5, 5, 5);
        gameBoard.setGameboard(convertedBoard);
        return gameBoard;
    }

    public static void clickOnAllEmptyTiles(GameBoard gameBoard) {
        clickOnAllEmptyTiles(gameBoard, 0);
    }

    public static void clickOnAllEmptyTiles(GameBoard gameBoard, int tilesLeft) {
        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {
                if (!gameBoard.getTile(i, j).isBomb() && gameBoard.getTilesLeft() > tilesLeft) {
                    gameBoard.testTileClicked(i, j);
                }
            }
        }

    }

    public static void printGameboard(GameBoard gameBoard) {
        for (List<Tile> row : gameBoard.board) {
            System.out.println(row);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
