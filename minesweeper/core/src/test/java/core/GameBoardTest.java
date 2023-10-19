// package core;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.Arrays;
// import java.util.List;

// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;

// public class GameBoardTest {

//     @Test
//     @DisplayName("Ensure that the constructor creates GameBoard objects and handles exceptions appropriately.")
//     public void testGameBoard() {
//         GameBoard gameBoard = new GameBoard();
//         Assertions.assertEquals(false, gameBoard.gameIsWon(),
//                 "A new gameBoard should not be considered as won.");

//         // TODO test for different difficulties        
//         Assertions.assertThrows(IllegalArgumentException.class, () -> {
//             new GameBoard(GameDifficulty.EASY);
//         }, "Should not be able to create a GameBoard with zero width");

//         Assertions.assertThrows(IllegalArgumentException.class, () -> {
//             new GameBoard(GameDifficulty.MEDIUM);
//         }, "Should not be able to create a GameBoard with negative height");

//         Assertions.assertThrows(IllegalArgumentException.class, () -> {
//             new GameBoard(GameDifficulty.HARD);
//         }, "Should not be able to create a GameBoard with negative bombs");

//         checkTooManyBombs(16);
//     }

//     private static void checkTooManyBombs(int boardLimit) {
//         for (int i = 1; i < boardLimit; i++) {
//             int heightWeight = i;
//             int numberOfBombs = i * i;

//             Assertions.assertThrows(IllegalArgumentException.class, () -> {
//                 new GameBoard(heightWeight, heightWeight, numberOfBombs);
//             }, "Should not be able to create a GameBoard with as many bombs as tiles.");

//         }
//     }

//     @Test
//     @DisplayName("Ensure that clicking on tiles on the gameBoard working as excpected.")
//     public void testClickOnTile() {
//         GameBoard gameBoard = new GameBoard(5, 5, 2);
//         gameBoard.tileClicked(1, 0);
//         Tile clickedTile = gameBoard.getTile(1, 0);

//         assertEquals(true, clickedTile.isRevealed(),
//                 "First click should always be reveald.");
//     }

//     @Test
//     @DisplayName("Ensure game winning works correctly")
//     public void testWinningGame() {

//         // 'E' equals empty and 'B' equals bomb
//         List<List<Character>> board5x5 = Arrays.asList(
//                 Arrays.asList('E', 'E', 'E', 'E', 'B'),
//                 Arrays.asList('E', 'B', 'B', 'E', 'E'),
//                 Arrays.asList('E', 'E', 'B', 'E', 'E'),
//                 Arrays.asList('E', 'E', 'B', 'E', 'E'),
//                 Arrays.asList('E', 'E', 'E', 'E', 'E'));
//         GameBoard gameBoard = GameBoardTestUtils.convertCharacterToGameBoard(board5x5);

//         gameBoard.testTileClicked(0, 0);
//         GameBoardTestUtils.clickOnAllEmptyTiles(gameBoard);

//         assertEquals(0, gameBoard.tilesLeft,
//                 "Every Tile except bombs should be clicked");
//         assertEquals(true, gameBoard.gameIsWon(),
//                 "When all not bomb tiles is clicked game should be won");
//     }

//     @Test
//     @DisplayName("Test that the game is lost when a bomb is clicked")
//     public void testLosingGame() {
//         List<List<Character>> board5x5 = Arrays.asList(
//                 Arrays.asList('E', 'E', 'E', 'E', 'B'),
//                 Arrays.asList('E', 'B', 'B', 'E', 'E'),
//                 Arrays.asList('E', 'E', 'B', 'E', 'E'),
//                 Arrays.asList('E', 'E', 'B', 'E', 'E'),
//                 Arrays.asList('E', 'E', 'E', 'E', 'E'));
//         GameBoard gameBoard = GameBoardTestUtils.convertCharacterToGameBoard(board5x5);

//         gameBoard.testTileClicked(0, 0);
//         gameBoard.testTileClicked(0, 4);
//         GameBoardTestUtils.clickOnAllEmptyTiles(gameBoard, 1);

//         assertEquals(1, gameBoard.tilesLeft,
//                 "All but one tile should be clicked");
//         assertEquals(true, gameBoard.getTile(0, 4).isRevealed(),
//                 "The bomb should be revealed");
//         assertEquals(false, gameBoard.gameIsWon(),
//                 "When a bomb is clicked the game should be lost");

//         GameBoardTestUtils.clickOnAllEmptyTiles(gameBoard);

//         assertEquals(0, gameBoard.tilesLeft,
//                 "All but one tile should be clicked");
//         assertEquals(true, gameBoard.getTile(0, 4).isRevealed(),
//                 "The bomb should be revealed");
//         assertEquals(false, gameBoard.gameIsWon(),
//                 "When a bomb is clicked the game should be lost");

//     }
// }
