module minesweepercore {
    opens minecore.core;

    // La til dette:
    requires transitive com.fasterxml.jackson.databind;
    exports minecore.core;
    //exports minesweeper.json;
}
