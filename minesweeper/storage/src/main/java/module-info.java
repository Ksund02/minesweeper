module minesweeper.storage {
    // Jackson 
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;

    // Jackson needs access to the readAndWriteFile package
    exports minesweeper.storage;
    opens minesweeper.storage to com.fasterxml.jackson.databind;
}
