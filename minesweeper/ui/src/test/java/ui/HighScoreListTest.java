package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

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
        List<UserScore> userScores = null;
        userScores = HighscoreFileManager.readFromHighscore(HighscoreFileManager.getFile());
        userScores.sort((a, b) -> a.getScore() - b.getScore());
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
