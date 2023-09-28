package ui;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.Mine7x7controller;


public class MineAppTest extends ApplicationTest {

    /*
     * Mockito is mentioned in the lecture notes, but is not currently used.
     * 
     * 
     */


    private Mine7x7controller controller;
    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/Mine7x7.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }
    

    /**
     * Clicks on the labels in the fxml-file which has the same name as the string parameters.
     * @param labels A list of strings which are the names of the labels you want to click on.
     */
    private void click(String... labels) {
        // The button class is a sublass of the Labeled class.
        // So when searching for matching labels, we will also find buttons.
        for (String label : labels) {
            clickOn(LabeledMatchers.hasText(label));
        }
    }


    @Test
    public void testResetGame() {
        
        // Redirecting the printing from the terminal to a buffer
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        click("Reset game");
        
        // We must trim the text, since the terminal will have a newline at the end.
        String terminal_text = buffer.toString().trim();
        assertEquals("reset game er trykket inn", terminal_text);
    }

    // Parameterized test, the test will be run multiple times, but each time with slightly different parameters.
    // We should probably add those once we have more interesting things to test.   
}

