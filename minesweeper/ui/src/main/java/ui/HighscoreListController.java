package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.util.List;

import core.settings.SettingsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import storage.UserScore;

public class HighscoreListController {

    private final static int HIGHESCORE_LENGTH = 10;
    public final static int STAGE_WIDTH = 500, STAGE_HEIGHT = 600;
    private final RestRequest restRequest = new RestRequest("http://localhost:8080");
    private String[] difficulties = { "EASY", "MEDIUM", "HARD" };
    private List<UserScore> userScores, scoresToShow;
    private List<Label> names, scores, dates;

    @FXML
    private Label name1, name2, name3, name4, name5, name6, name7, name8, name9, name10;
    @FXML
    private Label score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;
    @FXML
    private Label date1, date2, date3, date4, date5, date6, date7, date8, date9, date10;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label difficultyLabel;
    @FXML
    private ChoiceBox<String> difficultyChoiceBox;

    @FXML
    public void initialize() {
        userScores = restRequest.readFromHighscore();

        names = new ArrayList<>(
                Arrays.asList(name1, name2, name3, name4, name5, name6, name7, name8, name9, name10));
        scores = new ArrayList<>(
                Arrays.asList(score1, score2, score3, score4, score5, score6, score7, score8, score9, score10));
        dates = new ArrayList<>(
                Arrays.asList(date1, date2, date3, date4, date5, date6, date7, date8, date9, date10));

        anchorPane.setStyle(SettingsManager.getThemeSettings().getBackgroundStyle());
        difficultyChoiceBox.getItems().addAll(difficulties);
        difficultyChoiceBox.setValue(SettingsManager.getGameDifficultyAsString());
        difficultyChoiceBox.setOnAction(event -> switchLeaderboardDifficulty(event));
        this.switchLeaderboardDifficulty(null);
    }

    @FXML
    public void switchToGame(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/GamePage.fxml"));
        Parent root = fxmlLoader.load();
        Node eventSource = (Node) event.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setWidth(SettingsManager.getGameDifficulty().getStageMinWidth() + 1);
        stage.setHeight(SettingsManager.getGameDifficulty().getStageMinHeight() + 1);
        stage.show();
    }

    public void switchLeaderboardDifficulty(ActionEvent event) {
        String difficulty = difficultyChoiceBox.getValue();
        difficultyLabel.setText(difficulty);
        switch (difficulty) {
            case "EASY":
                difficultyLabel.setStyle("-fx-text-fill: green;");
                break;
            case "MEDIUM":
                difficultyLabel.setStyle("-fx-text-fill: orange;");
                break;
            case "HARD":
                difficultyLabel.setStyle("-fx-text-fill: red;");
                break;
            default:
                throw new IllegalStateException("Invalid game difficulty: " + difficulty + "!");
        }

        scoresToShow = userScores.stream()
                .filter(score -> score.getDifficulty().equals(difficulty))
                .toList();

        for (int i = 0; i < HIGHESCORE_LENGTH; i++) {
            if (i < scoresToShow.size()) {
                names.get(i).setText(scoresToShow.get(i).getName());
                scores.get(i).setText("" + scoresToShow.get(i).getScore());
                dates.get(i).setText(scoresToShow.get(i).getDate());
            } else {
                names.get(i).setText("-");
                scores.get(i).setText("-");
                dates.get(i).setText("-");
            }
        }
    }

}
