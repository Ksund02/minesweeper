module minesweeperstorage {
    // Jackson 
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires transitive minesweepercore;

    // SpringBoot
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.beans;
    requires spring.core;
    requires spring.context;
    requires jakarta.servlet;
    
    // Jackson needs access to the readAndWriteFile package
    exports storage;
    opens storage to com.fasterxml.jackson.databind;

    // SpringBoot needs access to the springBoot package, so called 'deep reflection'.
    opens springBoot;
}
