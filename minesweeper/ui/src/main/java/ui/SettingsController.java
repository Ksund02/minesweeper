package ui;

import java.io.IOException;

import core.settings.GameDifficulty;
import core.settings.SettingsManager;
import core.settings.ThemeSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    private Button easyButton, mediumButton, hardButton, lightModeButton, darkModeButton;
    @FXML
    private Label themeLabel, difficultyLevelLabel;
    @FXML
    private VBox vBox;
    @FXML
    private Line line;

    public final static int STAGE_WIDTH = 600;
    public final static int STAGE_HEIGHT = 500;

    @FXML
    public void initialize() {
        switch (SettingsManager.gameDifficulty) {
            case EASY:
                setEasy();
                break;
            case MEDIUM:
                setMedium();
                break;
            case HARD:
                setHard();
                break;
            default:
        }

        switch (SettingsManager.themeSettings) {
            case LIGHT:
                setLightMode();
                break;
            case DARK:
                setDarkMode();
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
        SettingsManager.gameDifficulty = GameDifficulty.EASY;
    }

    @FXML
    public void setMedium() {
        difficultyLevelLabel.setText("Medium");
        difficultyLevelLabel.setTextFill(Paint.valueOf("orange"));
        easyButton.setDisable(false);
        mediumButton.setDisable(true);
        hardButton.setDisable(false);
        SettingsManager.gameDifficulty = GameDifficulty.MEDIUM;
    }

    @FXML
    public void setHard() {
        difficultyLevelLabel.setText("Hard");
        difficultyLevelLabel.setTextFill(Paint.valueOf("red"));
        easyButton.setDisable(false);
        mediumButton.setDisable(false);
        hardButton.setDisable(true);
        SettingsManager.gameDifficulty = GameDifficulty.HARD;
    }

    @FXML
    public void setLightMode() {
        SettingsManager.themeSettings = ThemeSettings.LIGHT;
        themeLabel.setText("Light mode");
        themeLabel.setStyle("-fx-font-weight: normal");
        vBox.setStyle(SettingsManager.themeSettings.getBackgroundStyle());
        lightModeButton.setDisable(true);
        darkModeButton.setDisable(false);
    }

    @FXML
    public void setDarkMode() {
        SettingsManager.themeSettings = ThemeSettings.DARK;
        themeLabel.setText("Dark mode");
        themeLabel.setStyle("-fx-font-weight: bold");
        vBox.setStyle(SettingsManager.themeSettings.getBackgroundStyle());
        lightModeButton.setDisable(false);
        darkModeButton.setDisable(true);
    }

    @FXML
    public void switchToGame(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/GamePage.fxml"));
        Parent root = fxmlLoader.load();
        Node eventSource = (Node) event.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setScene(new Scene(root, SettingsManager.getStageMinWidth(), SettingsManager.getStageMinHeight()));
        stage.show();
    }

}
