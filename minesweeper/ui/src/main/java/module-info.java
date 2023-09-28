module minesweeperui {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires minesweeperstorage;
    opens ui to javafx.graphics, javafx.fxml;

    // La til dette:
    //requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires javafx.base;
    //requires javafx.controls;
    //requires javafx.fxml;
    requires minesweepercore;
    //requires fxutil;

}
