package storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HighscoreFileManager {

    public static final File highscoreFile = new File("./../appdata/highscore.json");
    public final File file;

    public HighscoreFileManager() {
        file = highscoreFile;
    }

    public HighscoreFileManager(String filePath) {
        this.file = new File(filePath);
    }

    public static File getFile() {
        return highscoreFile;
    }

    /**
     * Writes a UserScore to the highscore file.
     * 
     * @param userScore The score which the player has achieved.
     */
    public static void writeToHighscore(UserScore userScore, File file) {

        List<UserScore> userScores = null;
        // Read in the old user scores
        try {
            userScores = readFromHighscore(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userScores == null) {
            userScores = new ArrayList<>();
        }

        // Add the new user score
        userScores.add(userScore);

        // Sort the userScores by score, lower score is better.
        userScores.sort((a, b) -> a.getScore() - b.getScore());

        // Write the scores to json-file
        writeToFile(userScores, file);
    }

    /**
     * Writes a list of UserScores to the highscore file.
     * 
     * @param userScores The scores which are to be written to the highscore file.
     */
    private static void writeToFile(List<UserScore> userScores, File file) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, userScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads from the highscore file and returns a list of UserScore objects
     * 
     * @return List<UserScore>: A list containing all saved UserScores.
     */
    public static List<UserScore> readFromHighscore(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(is, new TypeReference<List<UserScore>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read highscore-file", e);
        }
    }

    /**
     * Removes all data from the highscore file
     */
    public static void clearHighscore(File file) {
        writeToFile(new ArrayList<UserScore>(), file);

    }

    // Simple testing
    public static void main(String[] args) {
        UserScore alice = new UserScore("Alice", 200, "2023-01-01");
        UserScore bob = new UserScore("Bob", 100, "2023-01-01");
        UserScore charlie = new UserScore("Charlie", 300, "2023-01-01");
        UserScore dave = new UserScore("Dave", 150, "2023-01-01");
        UserScore eve = new UserScore("Eve", 250, "2023-01-01");
        List<UserScore> userScores = new ArrayList<>();
        userScores.add(alice);
        userScores.add(bob);
        userScores.add(charlie);
        userScores.add(dave);
        userScores.add(eve);
        writeToHighscore(alice, highscoreFile);
        writeToHighscore(bob, highscoreFile);
        writeToHighscore(charlie, highscoreFile);
        writeToHighscore(dave, highscoreFile);
        writeToHighscore(eve, highscoreFile);

        // clearHighscore(HighscoreFileManager.highscoreFile);
        // System.out.println(readFromHighscore());

    }
}
