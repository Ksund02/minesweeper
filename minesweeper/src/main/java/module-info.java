module minesweeper {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Jackson 
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;

    opens minesweeper.app to javafx.graphics, javafx.fxml;
    
    // Jackson needs access to the readAndWriteFile package
    opens minesweeper.app.readeAndWriteFile to com.fasterxml.jackson.databind;
}
