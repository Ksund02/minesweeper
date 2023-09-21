package minesweeper.app;

import java.io.IOException;

import javafx.event.ActionEvent;
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

public class Mine7x7controller {

    @FXML private Label timeLabel;
    @FXML private GridPane gameGrid;
    @FXML private GameBoard gameBoard;

    @FXML
    public void resetGame() {
        System.out.println("reset game er trykket inn");
        clearGameGrid();
    }

    @FXML
    public void initialize() throws IOException {
        clearGameGrid();
        gameBoard = new GameBoard(7, 7, 10);
    }

    private void updateGameBoard() {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                Tile tile = gameBoard.getTile(x, y);
                if (tile.isRevealed()) {
                    String path = tile.getRevealedImagePath();
                    ImageView imageView = (ImageView) getNodeFromGridPane(gameGrid, x, y);
                    imageView.setImage(new Image(path));
                }
            }
        }
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
    public void clearGameGrid() {
        Image squarImage = new Image("file:src/main/resources/minesweeper/images/square.jpg");

        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                //Tile tile = gameBoard.getTile(row, col);
                ImageView imageView = new ImageView(squarImage);
                // Set dimensions, if necessary:
                imageView.setFitWidth(30); 
                imageView.setFitHeight(30); 
                gameGrid.add(imageView, x, y);
                final int row = x;
                final int col = y;
                imageView.setOnMouseClicked(e -> {
                    gameBoard.tileClicked(row, col);
                    updateGameBoard();
                });
            }
        }
    }
    public void switchToHighScoreList(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("HighscoreList.fxml"));
        Parent root = fxmlLoader.load();
        Node eventSource = (Node) event.getSource();
        Stage stage = (Stage) eventSource.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
}
