package ui;

import java.io.IOException;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import core.settings.SettingsManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import storage.HighscoreFileManager;
import storage.UserScore;

public class HighScoreListTest extends ApplicationTest {

    /*
     * Mockito is mentioned in the lecture notes, but is not currently used.
     * 
     * 
     */
    private Parent root;
    private FxRobot robot;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/HighscoreList.fxml"));
        root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
        robot = new FxRobot();
    }

    @Test
    public void right_order() {
        List<UserScore> userScores = HighscoreFileManager.readFromHighscore(HighscoreFileManager.getFile());
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
}
