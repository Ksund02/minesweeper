module minesweeperstorage {
    // Jackson 
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires transitive minesweepercore;

    // Jackson needs access to the readAndWriteFile package
    exports storage;
    opens storage to com.fasterxml.jackson.databind;
}
