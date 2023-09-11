module minesweeper {
    requires javafx.controls;
    requires javafx.fxml;

    opens minesweeper.app to javafx.graphics, javafx.fxml;
}
