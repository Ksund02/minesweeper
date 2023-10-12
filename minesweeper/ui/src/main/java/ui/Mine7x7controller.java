package ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import core.GameBoard;
import core.GameEngine;
import core.Tile;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import storage.HighscoreFileManager;
import storage.UserScore;

public class Mine7x7controller {

    @FXML
    private Label timeLabel, gameStatusLabel, flagsLeftLabel, lbNameLabel, feedbackLabel;
    @FXML
    private GridPane gameGrid;
    @FXML
    private TextField nameField;
    @FXML
    private Button sendLB;

    private GameEngine gameEngine;
    private Timeline timeline;
    private static final int GRID_WIDTH = 7, GRID_HEIGHT = 7, NUM_BOMBS = 10;

    @FXML
    public void initialize() throws IOException {
        this.gameEngine = new GameEngine(GRID_WIDTH, GRID_WIDTH, NUM_BOMBS);
        newGameGrid(GRID_WIDTH, GRID_HEIGHT);
        this.timeline = createTimeline();
    }

    @FXML
    public void resetGame() {
        gameEngine.resetGame(GRID_WIDTH, GRID_HEIGHT, NUM_BOMBS);

        clearGameGrid();
        timeline.stop();
        updateTimeLabel();
        flagsLeftLabel.setText(String.valueOf(gameEngine.getFlagsLeft()));

        sendLB.setDisable(true);
        sendLB.setVisible(false);
        nameField.setVisible(false);
        nameField.setDisable(true);
        lbNameLabel.setVisible(false);
        feedbackLabel.setVisible(false);
    }
    
    @FXML
    public void switchToHighScoreList(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/HighscoreList.fxml"));
        Parent root = fxmlLoader.load();
        Node eventSource = (Node) event.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // TODO: Name this saveToFIle ?
    @FXML
    public void sendToLeaderBoard() {
        HighscoreFileManager.writeToHighscore(
                new UserScore(nameField.getText(), gameEngine.getTime(), gameEngine.getDate()),
                HighscoreFileManager.getFile());
        feedbackLabel.setVisible(true);
        sendLB.setDisable(true);
        sendLB.setVisible(false);
        nameField.setVisible(false);
        nameField.setDisable(true);
        lbNameLabel.setVisible(false);
    }


    private void setNewImage(Tile tile) {
        String path = tile.getRevealedImagePath();
        ImageView imageView = (ImageView) getNodeFromGridPane(gameGrid, tile.getXCoordinate(), tile.getYCoordinate());
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

    // Clear the grid to start
    // TODO: make @FXXML gamegrid w*h optinal
    private void newGameGrid(int width, int height) {
        Image squareImage = new Image(getClass().getResourceAsStream("/images/square.jpg"));
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newSquare(squareImage, x, y);
            }
        }
    }

    private void newSquare(Image image, int x, int y) {
        ImageView imageView = new ImageView(image);

        // Set dimensions to square
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        gameGrid.add(imageView, x, y);
        final int row = x;
        final int col = y;
        imageView.setOnMouseClicked(e -> {
            squareClicked(e, row, col);
            if (gameEngine.gameIsStarted() && gameEngine.stopWatchIsStarted()) {
                timeline.play();
            }
        });
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

    private void updateGameView() {
        if (gameEngine.isGameWon())
            updateGameWon();

        if (gameEngine.isGameLost())
            updateGameLost();

        updateTiles();
    }

    private void updateGameWon() {
        timeline.stop();
        gameGrid.setDisable(true);
        gameStatusLabel.setText("You win!");

        sendLB.setDisable(false);
        sendLB.setVisible(true);
        nameField.setVisible(true);
        nameField.setDisable(false);
        lbNameLabel.setVisible(true);
    }

    private void updateGameLost() {
        timeline.stop();
        gameStatusLabel.setText("Game over!");
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

}
