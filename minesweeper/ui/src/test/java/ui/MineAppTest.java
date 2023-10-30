package ui;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import core.Tile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.HighscoreFileManager;

public class MineAppTest extends ApplicationTest {

    /*
     * Mockito is mentioned in the lecture notes, but is not currently used.
     * 
     * 
     */

    private GamePageController gamePageController;
    private Parent root;
    private GridPane gameGrid;
    private FxRobot robot;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/GamePage.fxml"));
        root = fxmlLoader.load();
        gamePageController = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
        robot = new FxRobot();
        gameGrid = robot.lookup("#gameGrid").query();
    }

    /**
     * Clicks on the labels in the fxml-file which has the same name as the string
     * parameters.
     * 
     * @param labels A list of strings which are the names of the labels you want to
     *               click on.
     */
    private void click(String... labels) {
        // The button class is a sublass of the Labeled class.
        // So when searching for matching labels, we will also find buttons.
        for (String label : labels) {
            clickOn(LabeledMatchers.hasText(label));
        }
    }

    // TODO: Make a updated reset game test. It it not writing anything
    // to terminal anymore
    @Test
    public void testResetGame() {

        // Redirecting the printing from the terminal to a buffer
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        click("Reset game");
        assertFalse(gamePageController.getStarted());
        // We must trim the text, since the terminal will have a newline at the end.
        // String terminal_text = buffer.toString().trim();

        // Not printing to terminal
        // assertEquals("reset game er trykket inn", terminal_text);
    }

    // Parameterized test, the test will be run multiple times, but each time with
    // slightly different parameters.
    // We should probably add those once we have more interesting things to test.

    // test where we click on the tiles that are not bombs and win:
    @Test
    public void testWin() {
        assertEquals(false, robot.lookup("#leaderBoardNameLabel").queryLabeled().isVisible());
        assertEquals(false, robot.lookup("#nameField").query().isVisible());
        assertEquals(false, robot.lookup("#sendToLeaderBoardButton").query().isVisible());
        assertEquals(false, !robot.lookup("#nameField").query().isDisabled());
        assertEquals(false, !robot.lookup("#sendToLeaderBoardButton").query().isDisabled());

        clickOn(gameGrid.getChildren().get(0));
        HashSet<String> bombCoords = gamePageController.getBombCoords();

        for (Node n : gameGrid.getChildren()) {
            // Coordinate of the node we click on / (Tile)
            int rowIndex = GridPane.getRowIndex(n);
            int columnIndex = GridPane.getColumnIndex(n);
            String coordinate = columnIndex + "." + rowIndex;

            // Check if the coordinate is not in the bombCoords set
            if (!bombCoords.contains(coordinate)) {
                clickOn(n);
            } else {
                rightClickOn(n);
            }
        }

        assertEquals(true, robot.lookup("#leaderBoardNameLabel").queryLabeled().isVisible());
        assertEquals(true, robot.lookup("#nameField").query().isVisible());
        assertEquals(true, robot.lookup("#sendToLeaderBoardButton").query().isVisible());
        assertEquals(true, !robot.lookup("#nameField").query().isDisabled());
        assertEquals(true, !robot.lookup("#sendToLeaderBoardButton").query().isDisabled());

        robot.lookup("#nameField").queryTextInputControl().setText("testerAAAAAAA");
        click("OK");
        assertEquals(true, HighscoreFileManager.readFromHighscore(new File("./../appdata/highscore.json")).stream()
                .anyMatch(score -> score.getName().equals("testerAAAAAAA")));
        HighscoreFileManager.deleteFromHighscore("testerAAAAAAA", gamePageController.getTime(), gamePageController.getDate());

    }

    @Test
    public void testLose() {
        clickOn((Node) gameGrid.getChildren().get(0));
        HashSet<String> bombCoords = gamePageController.getBombCoords();
        for (Node n : gameGrid.getChildren()) {
            // Coordinate of the node we click on / (Tile)
            int rowIndex = GridPane.getRowIndex(n);
            int columnIndex = GridPane.getColumnIndex(n);
            String coordinate = columnIndex + "." + rowIndex;

            // Check if the coordinate is in the bombCoords set
            if (bombCoords.contains(coordinate)) {
                clickOn(n);
                break;
            }
        }
        assertEquals("Game over!", robot.lookup("#gameStatusLabel").queryLabeled().getText());
    }

    @Test
    @DisplayName("Ensure that the choording works")
    public void testChoording() {
        clickOn((Node) gameGrid.getChildren().get(0));
        push(KeyCode.SPACE);

        Tile tileToClick = null;
        for (Node n : gameGrid.getChildren()) {
            int rowIndex = GridPane.getRowIndex(n);
            int columnIndex = GridPane.getColumnIndex(n);

            Tile tile = gamePageController.getTile(columnIndex, rowIndex);
            if (!tile.isBomb() && tile.hasAdjacentBomb() && tile.isRevealed()) {
                tileToClick = tile;
                break;
            }
        }

        List<Tile> neighborTiles = gamePageController.getNeighborTiles(tileToClick.getX(), tileToClick.getY());
        for (Tile tile : neighborTiles) {
            if (tile.isBomb()) {
                rightClickOn(gamePageController.getNodeFromGridPane(gameGrid, tile.getX(), tile.getY()));
            }
        }
        Node tileToClickNode = gamePageController.getNodeFromGridPane(gameGrid, tileToClick.getX(), tileToClick.getY());
        clickOn(tileToClickNode);
        push(KeyCode.SPACE);

        for (Tile tile : neighborTiles) {
            assertEquals(true, tile.isRevealed() || tile.isFlagged(),
                    "Tiles should be flagged or revealed after choording");
        }
    }

    @Test
    public void testSwitchToSettings() {
        assertEquals(false, robot.lookup("#easyButton").tryQuery().isPresent());
        click("Settings");
        assertEquals(true, robot.lookup("#easyButton").tryQuery().isPresent());
    }

    @Test
    public void testSwitchToLeaderboard() {
        assertEquals(false, robot.lookup("#score1").tryQuery().isPresent());
        click("Leaderboard");
        assertEquals(true, robot.lookup("#score1").tryQuery().isPresent());
    }
}
