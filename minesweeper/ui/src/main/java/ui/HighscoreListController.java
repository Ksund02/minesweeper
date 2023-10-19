package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import storage.HighscoreFileManager;
import storage.UserScore;

public class HighscoreListController {
    private final int HIGHESCORE_LENGTH = 10;

    @FXML
    private Label name1, name2, name3, name4, name5, name6, name7, name8, name9, name10;
    @FXML
    private Label score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;
    @FXML
    private Label date1, date2, date3, date4, date5, date6, date7, date8, date9, date10;

    @FXML
    public void initialize() {
        List<UserScore> userScores = HighscoreFileManager.readFromHighscore(HighscoreFileManager.getFile());
        List<Label> names = new ArrayList<>(
                Arrays.asList(name1, name2, name3, name4, name5, name6, name7, name8, name9, name10));
        List<Label> scores = new ArrayList<>(
                Arrays.asList(score1, score2, score3, score4, score5, score6, score7, score8, score9, score10));
        List<Label> dates = new ArrayList<>(
                Arrays.asList(date1, date2, date3, date4, date5, date6, date7, date8, date9, date10));

        for (int i = 0; i < Math.min(HIGHESCORE_LENGTH, userScores.size()); i++) {
            names.get(i).setText(userScores.get(i).getName());
            scores.get(i).setText("" + userScores.get(i).getScore());
            dates.get(i).setText(userScores.get(i).getDate());
        }
    }

    @FXML
    public void switchToGame(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/GamePage.fxml"));
        Parent root = fxmlLoader.load();
        Node eventSource = (Node) event.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
