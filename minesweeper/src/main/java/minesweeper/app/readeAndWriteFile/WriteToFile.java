package minesweeper.app.readeAndWriteFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO: Move this class to a common folder for read/write from file
public class WriteToFile {

    private static final String highscorePath = "minesweeper/src/main/resources/minesweeper/highscore.csv";

    // TODO: Show error as pop-up, not in terminal
    public static void writeToHighscore(String name, int seconds, String date) {

        String outputLine = ("\n" + name + "," + seconds + "," + date);

        ensureFileExists();
        writeToFile(highscorePath, outputLine);
    }

    
    
    private static void ensureFileExists() {
        Path path = Paths.get(highscorePath);
        
        if (Files.exists(path)) 
            return;

        try {
            Files.createDirectories(path.getParent());  
            Files.createFile(path); 
        } catch(IOException e) {
            System.out.println("Couldt make file");
        }

        String firstRow = ("name,seconds,date");
        writeToFile(highscorePath, firstRow);
        
    }

    private static void writeToFile(String path, String outputLine) {
        try (PrintStream outputStream = new PrintStream(new FileOutputStream(path, true))) {
            outputStream.print(outputLine);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    // Simple testing
    public static void main(String[] args) {
        WriteToFile.writeToHighscore("Bob", 12, "1999-01-01");
    }

    
}
