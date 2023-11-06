package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import core.settings.SettingsManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import storage.UserScore;

public class HighScoreListTest extends ApplicationTest {

  /*
   * Mockito is mentioned in the lecture notes, but is not currently used.
   * 
   * 
   */
  private Parent root;
  private FxRobot robot;
  private RestRequest restRequest;

  @Override
  public void start(Stage stage) throws IOException {
    RestRequest mockzy = Mockito.mock(RestRequest.class);
    this.restRequest = mockzy;
    Mockito.when(mockzy.readFromHighscore()).thenReturn(List.of(
      new UserScore("Underdal", 400, "2021-04-23", "EASY"),
      new UserScore("David", 200, "2021-04-21", "EASY"),
      new UserScore("Christian", 100, "2021-04-20", "EASY"),
      new UserScore("Oskar", 300, "2021-04-22", "EASY"),
      new UserScore("MineLegend", 14, "2021-04-19", "EASY")));

    HighscoreListController ctrl = new HighscoreListController();
    ctrl.setRestRequest(mockzy);
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/HighscoreList.fxml"));
    fxmlLoader.setControllerFactory(cls -> ctrl);
    root = fxmlLoader.load();
    stage.setScene(new Scene(root));
    stage.show();
    robot = new FxRobot();
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

  @Test
  public void right_order() {
    List<UserScore> userScores = restRequest.readFromHighscore();
    userScores = userScores.stream()
        .filter(score -> score.getDifficulty().equals(SettingsManager.getGameDifficultyAsString()))
        .sorted((a, b) -> a.getScore() - b.getScore())
        .toList();

    for (int i = 1; i < Math.min(11, userScores.size()); i++) {
      Assertions.assertEquals("" + userScores.get(i - 1).getScore(),
          robot.lookup("#score" + i).queryLabeled().getText());
      Assertions.assertEquals(userScores.get(i - 1).getName(),
          robot.lookup("#name" + i).queryLabeled().getText());
      Assertions.assertEquals(userScores.get(i - 1).getDate(),
          robot.lookup("#date" + i).queryLabeled().getText());
    }
  }

  @Test
  public void testBackButton() {
    assertEquals(false, robot.lookup("#gameGrid").tryQuery().isPresent());
    click("Back");
    assertEquals(true, robot.lookup("#gameGrid").tryQuery().isPresent());
  }
}
