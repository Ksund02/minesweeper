package ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import core.GameBoard;
import core.GameEngine;
import core.Tile;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import storage.HighscoreFileManager;
import storage.UserScore;

public class GamePageController {

    @FXML
    private Label timeLabel, gameStatusLabel, flagsLeftLabel, leaderBoardNameLabel, feedbackLabel;
    @FXML
    private GridPane gameGrid;
    @FXML
    private TextField nameField;
    @FXML
    private Button sendToLeaderBoardButton;
    @FXML
    private VBox anchor;

    private boolean isLightMode = true;
    private GameEngine gameEngine;
    private Timeline timeline;
    // private static final int GRID_WIDTH = 10, GRID_HEIGHT = 10, NUM_BOMBS = 10;
    // private static final int SCENE_MIN_WIDTH = 500, SCENE_MIN_HEIGHT = 500;
    private int[] currentSquare;

    @FXML
    public void initialize() throws IOException {
        this.gameEngine = new GameEngine();
        newGameGrid();
        Platform.runLater(() -> setStageMinSize());

        spaceBarClickSetup();
        this.timeline = createTimeline();
        this.currentSquare = null;
    }

    @FXML
    public void resetGame() {
        gameEngine.resetGame();

        clearGameGrid();
        timeline.stop();
        updateTimeLabel();
        flagsLeftLabel.setText(String.valueOf(gameEngine.getFlagsLeft()));

        sendToLeaderBoardButton.setDisable(true);
        sendToLeaderBoardButton.setVisible(false);
        nameField.setVisible(false);
        nameField.setDisable(true);
        leaderBoardNameLabel.setVisible(false);
        feedbackLabel.setVisible(false);
        gameGrid.setDisable(false);
    }

    @FXML
    public void switchToHighScoreList(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/HighscoreList.fxml"));
        Parent root = fxmlLoader.load();
        HighscoreListController controller = fxmlLoader.getController();
        if (!isLightMode) {
            controller.setDarkMode();
        }
        Node eventSource = (Node) event.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void switchToSettings(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/Settings.fxml"));
        Parent root = fxmlLoader.load();
        SettingsController controller = fxmlLoader.getController();
        if (isLightMode) {
            controller.setLightMode();
        }
        else {
            controller.setDarkMode();
        }
        Node eventSource = (Node) event.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // TODO: Name this saveToFIle ?
    @FXML
    public void submitHigescore() {
        HighscoreFileManager.writeToHighscore(
                new UserScore(nameField.getText(), gameEngine.getTime(), gameEngine.getDate()),
                HighscoreFileManager.getFile());

        feedbackLabel.setVisible(true);
        sendToLeaderBoardButton.setDisable(true);
        sendToLeaderBoardButton.setVisible(false);
        nameField.setVisible(false);
        nameField.setDisable(true);
        leaderBoardNameLabel.setVisible(false);
    }

    private void setNewImage(Tile tile) {
        // if dark mode add the /dark in the path
        String mode = (isLightMode) ? "/" : "/dark_";
        String path = mode + tile.getRevealedImagePath();
        ImageView imageView = (ImageView) getNodeFromGridPane(gameGrid, tile.getX(), tile.getY());
        InputStream inputStream = Tile.class.getResourceAsStream(path);

        // Path = /number0.jpg returns null.
        if (inputStream == null) {
            imageView.setImage(null);
        } else {
            imageView.setImage(new Image(inputStream));
        }
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        return gridPane.getChildren().stream()
                .filter(node -> GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
                .findFirst()
                .orElse(null);
    }

    private void newGameGrid() {
        gameGrid.getChildren().clear();
        Image squareImage = new Image(getClass().getResourceAsStream("/images/square.jpg"));
        int height = GameEngine.settings.getGridHeight();
        int width = GameEngine.settings.getGridWidth();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ImageView newSquare = newSquare(squareImage, x, y);
                gameGrid.add(newSquare, x, y);
            }
        }
    }

    private ImageView newSquare(Image image, int x, int y) {
        ImageView imageView = new ImageView(image);

        // Set dimensions to square
        imageView.setFitWidth(GameEngine.settings.getSquareSize());
        imageView.setFitHeight(GameEngine.settings.getSquareSize());

        final int row = x;
        final int col = y;
        imageView.setOnMouseClicked(e -> {
            squareClicked(e, row, col);
            if (gameEngine.gameIsStarted() && gameEngine.stopWatchIsStarted()) {
                timeline.play();
            }
        });
        // CurrentSquare gets updated when mouse hovers over a square
        imageView.setOnMouseEntered(e -> {
            currentSquare = new int[] { row, col };
        });
        imageView.setOnMouseExited(e -> {
            currentSquare = null;
        });
        return imageView;
    }

    private void squareClicked(MouseEvent e, int row, int col) {
        if (e.getButton().equals(MouseButton.PRIMARY)) {
            gameEngine.handleLeftClick(row, col);

        } else if (e.getButton().equals(MouseButton.SECONDARY)) {
            gameEngine.handleRightClick(row, col);
            flagsLeftLabel.setText("" + gameEngine.getFlagsLeft());
        }

        updateGameView();
    }

    private void setStageMinSize() {
        Stage stage = (Stage) gameGrid.getScene().getWindow();
        stage.setMinWidth(GameEngine.settings.getSceneMinWidth());
        stage.setMinHeight(GameEngine.settings.getSceneMinHeight());
    }

    /**
     * This method is used to initialize the spacebar click.
     * The gridpane gets permanent focus, and when the spacebar is clicked, the
     * method spaceBarClicked() is called.
     */
    private void spaceBarClickSetup() {
        gameGrid.setFocusTraversable(true);
        gameGrid.requestFocus();
        gameGrid.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                spaceBarClicked();
            }
        });
    }

    /**
     * This method handles the logic when the spacebar is clicked.
     * All non-flagged neighboring tiles are clicked,
     * unless the number of flags around the tile is not equal to the number of
     * bombs around the tile.
     */
    private void spaceBarClicked() {
        if (currentSquare == null) {
            return;
        }
        gameEngine.handleSpaceBarClick(currentSquare[0], currentSquare[1]);
        updateGameView();
    }

    private void updateGameView() {
        updateTiles();

        if (gameEngine.isGameWon())
            updateGameWon();

        if (gameEngine.isGameLost())
            updateGameLost();
    }

    private void updateGameWon() {
        timeline.stop();
        gameStatusLabel.setText("You win!");

        sendToLeaderBoardButton.setDisable(false);
        sendToLeaderBoardButton.setVisible(true);
        nameField.setVisible(true);
        nameField.setDisable(false);
        leaderBoardNameLabel.setVisible(true);
        gameGrid.setDisable(true);
    }

    private void updateGameLost() {
        timeline.stop();
        gameStatusLabel.setText("Game over!");
        gameGrid.setDisable(true);
    }

    private void updateTiles() {
        List<Tile> updatedTiles = gameEngine.getLatestUpdatedTiles();
        for (Tile tile : updatedTiles) {
            setNewImage(tile);
        }
    }

    private void clearGameGrid() {
        Image squareImage = new Image(getClass().getResourceAsStream("/images/square.jpg"));
        for (Node node : gameGrid.getChildren()) {
            ImageView iv = (ImageView) node;
            iv.setImage(squareImage);
        }
        gameGrid.setDisable(false);
        gameStatusLabel.setText("");
    }

    private Timeline createTimeline() {
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> updateTimeLabel());
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    public void updateTimeLabel() {
        timeLabel.setText("" + gameEngine.getTime());
    }

    public GameBoard getGameBoard() {
        return gameEngine.getGameBoard();
    }

    public void setIsLightMode(boolean b) {
        isLightMode = b;
        // if theme is dark mode
        if (!isLightMode) {
            anchor.setStyle("-fx-background-color: gray");
        }
    }


}
