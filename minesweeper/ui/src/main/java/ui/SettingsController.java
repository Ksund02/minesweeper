package ui;

import java.io.IOException;

import core.GameDifficulty;
import core.GameEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    private Label theme, difficultyLevel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Line line;

    @FXML
    public void initialize() {
        switch (GameEngine.settings) {
            case EASY:
                difficultyLevelLabel.setText("Easy");
                difficultyLevelLabel.setTextFill(Paint.valueOf("green"));
                easyButton.setDisable(true);
                break;
            case MEDIUM:
                difficultyLevelLabel.setText("Medium");
                difficultyLevelLabel.setTextFill(Paint.valueOf("orange"));
                mediumButton.setDisable(true);
                break;
            case HARD:
                difficultyLevelLabel.setText("Hard");
                difficultyLevelLabel.setTextFill(Paint.valueOf("red"));
                hardButton.setDisable(true);
                break;
            default:
        }
    }

    @FXML
    public void setEasy() {
        difficultyLevelLabel.setText("Easy");
        difficultyLevelLabel.setTextFill(Paint.valueOf("green"));
        easyButton.setDisable(true);
        mediumButton.setDisable(false);
        hardButton.setDisable(false);
        GameEngine.setGameDifficulty(GameDifficulty.EASY);
    }

    @FXML
    public void setMedium() {
        difficultyLevelLabel.setText("Medium");
        difficultyLevelLabel.setTextFill(Paint.valueOf("orange"));
        easyButton.setDisable(false);
        mediumButton.setDisable(true);
        hardButton.setDisable(false);
        GameEngine.setGameDifficulty(GameDifficulty.MEDIUM);
    }

    @FXML
    public void setHard() {
        difficultyLevelLabel.setText("Hard");
        difficultyLevelLabel.setTextFill(Paint.valueOf("red"));
        easyButton.setDisable(false);
        mediumButton.setDisable(false);
        hardButton.setDisable(true);
        GameEngine.setGameDifficulty(GameDifficulty.HARD);
    }

    @FXML
    public void setLightMode() {
        themeLabel.setText("Light mode");
        themeLabel.setStyle("-fx-font-weight: normal");
        anchorPane.setStyle("-fx-background-color: white");
        // Must also set theme in static variable

    }

    @FXML
    public void setDarkMode() {
        themeLabel.setText("Dark mode");
        themeLabel.setStyle("-fx-font-weight: bold");
        anchorPane.setStyle("-fx-background-color: gray");
        // Must also set theme in static variable

    }

    @FXML
    public void switchToGame(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/GamePage.fxml"));
        Parent root = fxmlLoader.load();
        GamePageController controller = fxmlLoader.getController();
        if (themeLabel.getText() == "Dark mode") {
            controller.setDarkMode();
        }
        Node eventSource = (Node) event.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
