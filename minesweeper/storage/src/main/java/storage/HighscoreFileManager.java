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

    private static final File highscoreFile = new File("./../appdata/highscore.json");

    /**
     * Private constructor to prevent instantiation, and to make jacoco not complain.
     */
    private HighscoreFileManager() {}

    /**
     * @return The highscore file.
     */
    public static File getFile() {
        return highscoreFile;
    }

    /**
     * Writes a UserScore to the highscore file.
     * Ensures that the highscore file is sorted by score, lower scores first.
     * @param userScore The score which the player has achieved.
     */
    public static void writeToHighscore(UserScore userScore, File file) {
        List<UserScore> userScores = readFromHighscore(file);
        userScores.add(userScore);
        sortUserScores(userScores);
        writeToFile(userScores, file);
    }

    /**
     * Sorts a list of UserScores by score, lower scores first.
     * 
     * @param userScores The list of UserScores which is to be sorted.
     */
    private static void sortUserScores(List<UserScore> userScores) {
        userScores.sort((a, b) -> a.getScore() - b.getScore());
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
            // It is actually very unlikely that something will go wrong here.
            // Since if you specify a file which does not exist, it will be created.
            e.printStackTrace();
        }
    }

    /**
     * Reads from the highscore file and returns a list of UserScore objects.
     * If the file cannot be read, an empty list will be returned.
     * 
     * @param file The file which is to be read.
     * @return List<UserScore>: A list containing all saved UserScores.
     */
    public static List<UserScore> readFromHighscore(File file) {
        try (InputStream is = new FileInputStream(file)) {
            ObjectMapper jsonReader = new ObjectMapper();
            return jsonReader.readValue(is, new TypeReference<List<UserScore>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<UserScore>();
        }
    }

    /**
     * Removes all data from the highscore file.
     */
    static void clearHighscore(File file) {
        writeToFile(new ArrayList<UserScore>(), file);
    }
}
