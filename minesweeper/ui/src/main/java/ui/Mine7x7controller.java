package ui;

import java.io.IOException;
import java.io.InputStream;

import core.GameBoard;
import core.Stopwatch;
import core.Tile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import core.Stopwatch;
import javafx.scene.control.Label;


public class Mine7x7controller {

    @FXML
    private Label timeLabel, gameStatusLabel;
    @FXML
    private GridPane gameGrid;
    //@FXML
    private GameBoard gameBoard;
    private Stopwatch stopwatch;
    private Timeline timeline;

    @FXML
    public void resetGame() {
        System.out.println("reset game er trykket inn");
        gameBoard = new GameBoard(7, 7, 10);
        clearGameGrid();
        timeline.stop();
        stopwatch.restart();
        timeLabel.setText(""+0);
    }

    @FXML
    public void initialize() throws IOException {
        gameBoard = new GameBoard(7, 7, 10);
        newGameGrid();
        stopwatch = new Stopwatch();
        timeline = createTimeline();
    }

    private void updateGameBoard() {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                Tile tile = gameBoard.getTile(x, y);
                if (tile.isRevealed()) {
                    String path = tile.getRevealedImagePath();
                    ImageView imageView = (ImageView) getNodeFromGridPane(gameGrid, x, y);
                    InputStream is = Tile.class.getResourceAsStream(path);
                    // Path = /number0.jpg returns null.
                    if (is == null) {imageView.setImage(null);}
                    else {imageView.setImage(new Image(is));}
                }
                if (tile.isRevealed() && tile.isBomb()) {
                    gameOver();
                    return;
                } else if (gameBoard.gameIsWon()) {
                    gameWon();
                    return;
                }
            }
        }
    }

    private void gameOver() {
        timeline.stop();
        stopwatch.stop();
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                Tile tile = gameBoard.getTile(x, y);
                if (tile.isBomb()) {
                    String path = tile.getRevealedImagePath();
                    ImageView imageView = (ImageView) getNodeFromGridPane(gameGrid, x, y);
                    InputStream is = Tile.class.getResourceAsStream(path);
                    // Path = /number0.jpg returns null.
                    if (is == null) {imageView.setImage(null);}
                    else {imageView.setImage(new Image(is));}
                }
                gameGrid.setDisable(true);
                gameStatusLabel.setText("Game over!");
            }
        }
    }

    private void gameWon() {
        timeline.stop();
        stopwatch.stop();
        gameGrid.setDisable(true);
        gameStatusLabel.setText("You win!");
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    // TODO: Move this out of controller, add mouseClick event on imageView
    // Clear the grid to start
    private void newGameGrid() {
        Image squareImage = new Image(getClass().getResourceAsStream("/images/square.jpg"));
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                // Tile tile = gameBoard.getTile(row, col);
                ImageView imageView = new ImageView(squareImage);
                // Set dimensions, if necessary:
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                gameGrid.add(imageView, x, y);
                final int row = x;
                final int col = y;
                imageView.setOnMouseClicked(e -> {
                    gameBoard.tileClicked(row, col);
                    if (gameBoard.gameStarted() && !stopwatch.started()) {
                        stopwatch.start();
                        timeline.play();
                    }
                    updateGameBoard();
                });
            }
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

    public void switchToHighScoreList(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/HighscoreList.fxml"));
        Parent root = fxmlLoader.load();
        Node eventSource = (Node) event.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private Timeline createTimeline() {
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateTimeLabel();
            }
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    public void updateTimeLabel() {
        timeLabel.setText(""+stopwatch.getTime());
    }

}
