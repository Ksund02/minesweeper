package minesweeper.app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    // TODO: Implementer logikk
    @FXML
    public void resetGame() {
        System.out.println("reset game er trykket inn");
        clearGameGrid();
    }

    @FXML
    public void initialize() throws IOException {
        clearGameGrid();
    }   

    // TODO: Move this out of controller, add mouseClick event on imageView
    // Clear the grid to start
    public void clearGameGrid() {
        Image squarImage = new Image("file:src/main/resources/minesweeper/images/square.jpg");

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                ImageView imageView = new ImageView(squarImage);
                // Set dimensions, if necessary:
                imageView.setFitWidth(30); 
                imageView.setFitHeight(30); 
                gameGrid.add(imageView, col, row);
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
