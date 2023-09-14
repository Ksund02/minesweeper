package minesweeper.app;

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
    public static void writeToHighscoreFile(String name, int seconds, String date) {

        String outputLine = ("\n" + name + "," + seconds + "," + date);

        try {
            ensureFileExists();
            try (PrintStream outputStream = new PrintStream(new FileOutputStream(highscorePath, true))) {
                writeToFile(outputStream, outputLine);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error writing to highscore file.");

        } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error ensuring highscore file exists.");
        }
    }
    
    private static void ensureFileExists() throws IOException {
        Path path = Paths.get(highscorePath);
        
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());  
            Files.createFile(path); 
        }
    }

    private static void writeToFile(PrintStream outputStream, String outputLine) {
        outputStream.print(outputLine);
    }

    // Simple testing
    public static void main(String[] args) {
        WriteToFile.writeToHighscoreFile("Bob", 12, "1999-01-01");
    }

    
}
