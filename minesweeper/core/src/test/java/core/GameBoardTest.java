package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameBoardTest {

    @Test
    @DisplayName("Ensure that the constructor creates GameBoard objects and handles exceptions appropriately.")
    public void testGameBoard() {
        GameBoard gameBoard = new GameBoard(4, 4, 1);
        Assertions.assertEquals(false, gameBoard.gameIsWon(),
                "A new gameBoard should not be considered as won.");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new GameBoard(0, 5, 2);
        }, "Should not be able to create a GameBoard with zero width");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new GameBoard(5, -5, 2);
        }, "Should not be able to create a GameBoard with negative height");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new GameBoard(5, 5, -2);
        }, "Should not be able to create a GameBoard with negative bombs");

        checkToManyBombs(16);

    }

    private static void checkToManyBombs(int boardLimit) {
        for (int i = 1; i < boardLimit; i++) {
            int heightWeight = i;
            int numberOfBombs = i * i;

            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                new GameBoard(heightWeight, heightWeight, numberOfBombs);
            }, "Should not be able to create a GameBoard with as many tiles as bombs.");

        }
    }

    @Test
    @DisplayName("Ensure that clicking on tiles on the gameBoard working as excpected.")
    public void testClickOnTile() {
        GameBoard gameBoard = new GameBoard(5, 5, 2);
        gameBoard.tileClicked(1, 0);
        Tile clickedTile = gameBoard.getTile(1, 0);

        assertEquals(true, clickedTile.isRevealed(),
                "First click should always be reveald.");
    }

    @Test
    @DisplayName("Ensure game winning works correctly")
    public void testWinningGame() {

        // 'E' equals empty and 'B' equals bomb
        List<List<Character>> board5x5 = Arrays.asList(
                Arrays.asList('E', 'E', 'E', 'E', 'B'),
                Arrays.asList('E', 'B', 'B', 'E', 'E'),
                Arrays.asList('E', 'E', 'B', 'E', 'E'),
                Arrays.asList('E', 'E', 'B', 'E', 'E'),
                Arrays.asList('E', 'E', 'E', 'E', 'E'));
        List<List<Tile>> board = convertBoardToTileBoard(board5x5);

        GameBoard gameBoard = new GameBoard(5, 5, 5);
        gameBoard.tileClicked(0, 0);
        board.get(0).get(0).reveal();
        
        gameBoard.setGameboard(board);

        clickOnAllEmptyTiles(gameBoard, board);

        // TODO: denne testen er ikke ferdigstillt, da den det er noe med place bombs, som 
        // ikke blir gjort riktig, når man kjører setGameboard
        
        // assertEquals(5, gameBoard.tilesLeft,
        //         "Every Tile except bombs should be clicked");

        // assertEquals(true, gameBoard.gameIsWon(),
        //         "When all not bomb tiles is clicked game should be won");

    }

    private static List<List<Tile>> convertBoardToTileBoard(List<List<Character>> boarWithText) {
        List<List<Tile>> convertedBoard = new ArrayList<>();
        for (List<Character> row : boarWithText) {
            List<Tile> newRow = new ArrayList<>();
            for (char tileChar : row) {
                Tile newTile = new Tile();
                if (tileChar == 'B')
                    newTile.makeBomb();
                newRow.add(newTile);
            }
            convertedBoard.add(newRow);
        }
        return convertedBoard;
    }

    private void clickOnAllEmptyTiles(GameBoard gameBoard, List<List<Tile>> board) {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                Tile tile = board.get(i).get(j);
                if (!tile.isBomb()) {
                    gameBoard.tileClicked(i, j);
                }
            }
        }
    }

}
