module minesweeper.storage {
    // Jackson 
    requires transitive com.fasterxml.jackson.core;
    requires transitive com.fasterxml.jackson.databind;
    requires transitive com.fasterxml.jackson.annotation;

    // Jackson needs access to the readAndWriteFile package
    opens minesweeper.storage to com.fasterxml.jackson.databind;
    exports minesweeper.storage;
}
