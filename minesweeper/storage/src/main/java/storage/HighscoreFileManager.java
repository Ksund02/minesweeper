package storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Move this class to a common folder for read/write from file
public class HighscoreFileManager {

    private static final File highscoreFile = new File("storage/src/main/resources/highscore.json");

    

    // TODO: Show error as pop-up, not in terminal

    /**
     * Writes a UserScore to the highscore file.
     * 
     * @param userScore The score which the player has achieved.
     */
    public static void writeToHighscore(UserScore userScore) {
        
        List<UserScore> userScores = null;
        // Read in the old user scores
        try {userScores = readFromHighscore();}
        catch (FileNotFoundException e) {e.printStackTrace();}

        if (userScores == null) {
            userScores = new ArrayList<>();
        }

        // Add the new user score
        userScores.add(userScore);

        // Sort the userScores by score, lower score is better.
        userScores.sort((a, b) -> a.getScore() - b.getScore());

        // Write the scores to json-file
        writeToFile(userScores);
    }

    /**
     * Writes a list of UserScores to the highscore file.
     * 
     * @param userScores The scores which are to be written to the highscore file.
     */
    private static void writeToFile(List<UserScore> userScores) {
        
        
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(highscoreFile, userScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads from the highscore file and returns a list of UserScore objects
     * 
     * @return List<UserScore>: A list containing all saved UserScores.
     */
    public static List<UserScore> readFromHighscore() throws FileNotFoundException {
        InputStream is = HighscoreFileManager.class.getClassLoader().getResourceAsStream("highscore.json");
        if (is == null) {
            throw new FileNotFoundException("Failed to read highscore-file");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(is, new TypeReference<List<UserScore>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read highscore-file", e);
        }
    }

    /**
     * Removes all data from the highscore file
     */
    private static void clearHighscore() {
        writeToFile(new ArrayList<UserScore>());
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
        writeToHighscore(alice);
        writeToHighscore(bob);
        writeToHighscore(charlie);
        writeToHighscore(dave);
        writeToHighscore(eve);

        // clearHighscore();
        // System.out.println(readFromHighscore());

    }
}
