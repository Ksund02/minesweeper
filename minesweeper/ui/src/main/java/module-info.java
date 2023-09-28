module minesweeper.ui {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires storage;

    opens minesweeper.app to javafx.graphics, javafx.fxml;

    // La til dette:
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires minesweeper.core;
    requires fxutil;

}
