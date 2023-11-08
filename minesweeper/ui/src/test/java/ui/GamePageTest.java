package ui;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import core.GameEngine;
import core.Tile;
import core.TileReadable;
import core.settings.SettingsManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.HighscoreFileManager;
import storage.UserScore;

public class GamePageTest extends ApplicationTest {

  private GamePageController gamePageController;
  private Parent root;
  private GridPane gameGrid;
  private FxRobot robot;

  @Override
  public void start(Stage stage) throws IOException {

    // Setting up a mock of HTTP requests.
    RestRequest mockz = Mockito.mock(RestRequest.class);
    Mockito.doAnswer(inv -> {
      UserScore score = inv.getArgument(0, UserScore.class);
      HighscoreFileManager.writeToHighscore(score, HighscoreFileManager.getFile());
      return null;
    }).when(mockz).writeToHighscore(Mockito.any(UserScore.class));

    GamePageController ctrl = new GamePageController();
    ctrl.setRestRequest(mockz);
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/GamePage.fxml"));
    fxmlLoader.setControllerFactory(cls -> ctrl);
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
    for (String label : labels) {
      clickOn(LabeledMatchers.hasText(label));
    }
  }

  @Test
  public void testResetGame() {
    click("Reset game");
    assertFalse(gamePageController.getStarted());
  }

  /**
   * A test where we click on all the tiles that are not bombs.
   * The game should be won after this.
   */
  @Test
  public void testWin() {
    assertInitialState();

    GameEngine engine = gamePageController.getGameEngine();
    clickOn(gameGrid.getChildren().get(0));

    for (Node node : gameGrid.getChildren()) {
      // Coordinate of the node we click on / (Tile)
      int x = GridPane.getColumnIndex(node);
      int y = GridPane.getRowIndex(node);

      TileReadable tile = engine.getTile(x, y);
      if (tile.isRevealed()) {
        continue;
      }

      if (tile.isBomb()) {
        rightClickOn(node);

      } else {
        clickOn(node);
      }
    }
    assertGameWon();
  }

  private void assertInitialState() {
    assertEquals(false, robot.lookup("#leaderBoardNameLabel").queryLabeled().isVisible());
    assertEquals(false, robot.lookup("#nameField").query().isVisible());
    assertEquals(false, robot.lookup("#sendToLeaderBoardButton").query().isVisible());
    assertEquals(false, !robot.lookup("#nameField").query().isDisabled());
    assertEquals(false, !robot.lookup("#sendToLeaderBoardButton").query().isDisabled());
  }

  private void assertGameWon() {
    assertEquals(true, robot.lookup("#leaderBoardNameLabel").queryLabeled().isVisible());
    assertEquals(true, robot.lookup("#nameField").query().isVisible());
    assertEquals(true, robot.lookup("#sendToLeaderBoardButton").query().isVisible());
    assertEquals(true, !robot.lookup("#nameField").query().isDisabled());
    assertEquals(true, !robot.lookup("#sendToLeaderBoardButton").query().isDisabled());

    robot.lookup("#nameField").queryTextInputControl().setText("TesterAAAAA");
    click("OK");

    assertEquals(true, HighscoreFileManager.readFromHighscore(new File("./../appdata/highscore.json")).stream()
        .anyMatch(score -> score.getName().equals("TesterAAAAA")));
    HighscoreFileManager.deleteFromHighscore("TesterAAAAA", gamePageController.getTime(),
        gamePageController.getDate(), SettingsManager.getGameDifficultyAsString(), HighscoreFileManager.getFile());
  }

  @Test
  @DisplayName("Ensure clicking on a bomb make you lose")
  public void testLose() {
    clickOn((Node) gameGrid.getChildren().get(0));
    for (Node node : gameGrid.getChildren()) {
      // Coordinate of the node we click on
      int x = GridPane.getColumnIndex(node);
      int y = GridPane.getRowIndex(node);

      TileReadable tile = gamePageController.getTile(x, y);

      if (tile.isBomb()) {
        clickOn(node);
        break;
      }
    }
    assertEquals("Game over!", robot.lookup("#gameStatusLabel").queryLabeled().getText(), "Game should be over");
  }

  @Test
  @DisplayName("Ensure that the choording works")
  public void testChoording() {
    clickOn((Node) gameGrid.getChildren().get(0));
    push(KeyCode.SPACE);

    TileReadable tileToClick = null;
    for (Node node : gameGrid.getChildren()) {
      int rowIndex = GridPane.getRowIndex(node);
      int columnIndex = GridPane.getColumnIndex(node);

      TileReadable tile = gamePageController.getTile(columnIndex, rowIndex);
      if (!tile.isBomb() && tile.hasAdjacentBomb() && tile.isRevealed()) {
        tileToClick = tile;
        break;
      }
    }

    List<Tile> neighborTiles = gamePageController.getNeighborTiles(tileToClick.getX(), tileToClick.getY());
    for (Tile tile : neighborTiles) {
      if (tile.isBomb()) {
        rightClickOn(getNodeFromGridPane(tile.getX(), tile.getY()));
      }
    }
    Node tileToClickNode = getNodeFromGridPane(tileToClick.getX(), tileToClick.getY());
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

  /**
   * This method tests if the leaderboard is shown when the leaderboard button is
   * clicked. The standard output is redirected to a ByteArrayOutputStream, such
   * that we don't get the error messages caused by the fact that the HTTP-
   * requests are not working, since we are not running the server.
   */
  @Test
  public void testSwitchToLeaderboard() {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    assertEquals(false, robot.lookup("#score1").tryQuery().isPresent());
    click("Leaderboard");
    assertEquals(true, robot.lookup("#score1").tryQuery().isPresent());
    System.setOut(originalOut);
  }

  @Test
  public void testThatOtherButtonsDoNotAffectGame() {
    // Mousewheel click
    clickOn((Node) gameGrid.getChildren().get(0), MouseButton.MIDDLE);
    assertFalse(gamePageController.getStarted());

    // Clicking U on keyboard.
    clickOn((Node) gameGrid.getChildren().get(0));
    push(KeyCode.U);

    TileReadable tileToClick = null;
    for (Node node : gameGrid.getChildren()) {
      int rowIndex = GridPane.getRowIndex(node);
      int columnIndex = GridPane.getColumnIndex(node);

      TileReadable tile = gamePageController.getTile(columnIndex, rowIndex);
      if (!tile.isBomb() && tile.hasAdjacentBomb() && tile.isRevealed()) {
        tileToClick = tile;
        break;
      }
    }

    List<Tile> neighborTiles = gamePageController.getNeighborTiles(tileToClick.getX(), tileToClick.getY());
    for (Tile tile : neighborTiles) {
      if (tile.isBomb()) {
        rightClickOn(getNodeFromGridPane(tile.getX(), tile.getY()));
      }
    }

    Node tileToClickNode = getNodeFromGridPane(tileToClick.getX(), tileToClick.getY());
    List<Tile> revealedNeighbors = neighborTiles.stream().filter(Tile::isRevealed).toList();
    clickOn(tileToClickNode);
    push(KeyCode.U);

    assertFalse(neighborTiles.stream().anyMatch(t -> t.isRevealed() && !revealedNeighbors.contains(t)),
        "Tiles should not be revealed when clicking U instead of spacebar");
  }

  // For testing
  public Node getNodeFromGridPane(int col, int row) {
    return gameGrid.getChildren().stream()
        .filter(node -> GridPane.getColumnIndex(node) == col &&
            GridPane.getRowIndex(node) == row)
        .findFirst()
        .orElse(null);
  }
}
