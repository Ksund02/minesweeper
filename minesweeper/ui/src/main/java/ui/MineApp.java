package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import core.settings.SettingsManager;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * JavaFX App
 */
public class MineApp extends Application {

    private final String[] playlist = {
            "/music/introvert.mp3",
            "/music/houses_on_hills.mp3",
            "/music/close_my_eyes.mp3"
    };

    private MediaPlayer musicPlayer;

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

        // play_music(0);
        stage.setWidth(SettingsManager.getGameDifficulty().getStageMinWidth());
        stage.setHeight(SettingsManager.getGameDifficulty().getStageMinHeight());
        stage.show();
    }

    private void play_music(int index) {

        if (musicPlayer != null) {
            musicPlayer.dispose();
        }

        Media media = new Media(getClass().getResource(playlist[index]).toString());
        musicPlayer = new MediaPlayer(media);

        musicPlayer.setOnEndOfMedia(() -> {
            play_music((index + 1) % playlist.length);
        });
        musicPlayer.play();
    }

    public static void main(String[] args) {
        launch();
    }
}