module minesweeper.core {
    opens minesweeper.app;

    // La til dette:
    requires transitive com.fasterxml.jackson.databind;
    exports minesweeper.core;
    exports minesweeper.json;
}
