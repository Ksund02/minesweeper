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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    private Label theme, difficultyLevel;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Line line;

    @FXML
    public void initialize() {
        // Must set difficultyLevelLabel like it is now in the start
        // Must set themeLabel and theme like it is now in the start
    }

    @FXML
    public void setEasy() {
        difficultyLevel.setText("Easy");
        difficultyLevel.setTextFill(Paint.valueOf("green"));
        // Must also set difficultyLevel in static variable
        GameEngine.setGameDifficulty(GameDifficulty.EASY);
    }

    @FXML
    public void setMedium() {
        difficultyLevel.setText("Medium");
        difficultyLevel.setTextFill(Paint.valueOf("orange"));
        // Must also set difficultyLevel in static variable
        GameEngine.setGameDifficulty(GameDifficulty.MEDIUM);
    }

    @FXML
    public void setHard() {
        difficultyLevel.setText("Hard");
        difficultyLevel.setTextFill(Paint.valueOf("red"));
        // Must also set difficultyLevel in static variable
        GameEngine.setGameDifficulty(GameDifficulty.HARD);
    }

    @FXML
    public void setLightMode() {
        theme.setText("Light mode");
        theme.setStyle("-fx-font-weight: normal");
        anchor.setStyle("-fx-background-color: white");
        // Must also set theme in static variable

    }

    @FXML
    public void setDarkMode() {
        theme.setText("Dark mode");
        theme.setStyle("-fx-font-weight: bold");
        anchor.setStyle("-fx-background-color: gray");
        // Must also set theme in static variable

    }


    @FXML
    public void switchToGame(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/GamePage.fxml"));
        Parent root = fxmlLoader.load();
        GamePageController controller = fxmlLoader.getController();
        controller.setIsLightMode(theme.getText() == "Light mode");
        Node eventSource = (Node) event.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
