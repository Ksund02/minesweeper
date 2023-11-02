package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import core.settings.SettingsManager;

/**
 * JavaFX App
 */
public class MineApp extends Application {
    /**
     * Initializing the game scene, setting the icon, and starts to play music.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/GamePage.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));

        Image icon = new Image(getClass().getResourceAsStream("/images/truls.jpg"));
        stage.getIcons().add(icon);

        stage.setWidth(SettingsManager.getGameDifficulty().getStageMinWidth()+1);
        stage.setHeight(SettingsManager.getGameDifficulty().getStageMinHeight()+1);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}