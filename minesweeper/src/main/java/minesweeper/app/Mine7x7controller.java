package minesweeper.app;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Mine7x7controller {

    @FXML private Label timeLabel;
    @FXML private GridPane gameGrid;
    @FXML private GameBoard gameBoard;

    // TODO: Implementer logikk
    @FXML
    public void resetGame() {
        System.out.println("reset game er trykket inn");
        clearGameGrid();
    }

    @FXML
    public void initialize() throws IOException {
        clearGameGrid();
        //gameBoard = new GameBoard(7, 7, 10);
    }   

    // TODO: Move this out of controller, add mouseClick event on imageView
    // Clear the grid to start
    private void clearGameGrid() {
        Image squarImage = new Image("file:src/main/resources/minesweeper/images/square.jpg");

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                ImageView imageView = new ImageView(squarImage);
                // Set dimensions, if necessary:
                imageView.setFitWidth(30); 
                imageView.setFitHeight(30); 
                gameGrid.add(imageView, col, row);
                final int rower = row;
                final int coler = col;
                //imageView.setOnMouseClicked(e -> gameBoard.tileClicked(imageView, rower, coler));
                
                
            }
        }
    }
    
}
