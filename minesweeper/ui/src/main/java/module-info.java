module minesweeperui {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires minesweepercore;
    requires minesweeperstorage;
    opens ui to javafx.graphics, javafx.fxml;
    requires java.net.http;
    requires javafx.base;
}
