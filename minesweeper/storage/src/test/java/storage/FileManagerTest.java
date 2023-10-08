package storage;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.PrintStream;
import java.io.OutputStream;
import java.util.List;

public class FileManagerTest {

    private static final File testFile = new File("src/test/resources/testHighscore.json");

    @BeforeEach
    public void setup() {
        HighscoreFileManager.clearHighscore(testFile);
    }

    @Test
    public void checkFile() {
        File expectedFile = new File("./../appdata/highscore.json");
        File actualFile = HighscoreFileManager.getFile();
        assertEquals(expectedFile, actualFile, "File is not correct");
    }

    @Test
    public void clearHighscore() throws Exception {
        UserScore bert = new UserScore("Bert", 100, "2020-10-04");
        UserScore bernard = new UserScore("Bernard", 30, "2021-09-08");
        UserScore alfred = new UserScore("Alfred", 50, "2021-09-15");
        HighscoreFileManager.writeToHighscore(bert, testFile);
        HighscoreFileManager.writeToHighscore(bernard, testFile);
        HighscoreFileManager.writeToHighscore(alfred, testFile);

        HighscoreFileManager.clearHighscore(testFile);
        ObjectMapper jsonReader = new ObjectMapper();
        List<UserScore> userScores = jsonReader.readValue(testFile, new TypeReference<List<UserScore>>() {
        });

        assertEquals(0, userScores.size(), "File is not empty after clearing: " + userScores.toString());
    }

    @Test
    public void writeToEmptyHighscore() throws Exception {
        UserScore pedor = new UserScore("Pedor", 50, "2023-10-04");
        HighscoreFileManager.writeToHighscore(pedor, testFile);
        ObjectMapper jsonReader = new ObjectMapper();
        List<UserScore> userScores = jsonReader.readValue(testFile, new TypeReference<List<UserScore>>() {
        });

        assertEquals(1, userScores.size(), userScores.toString());
        assertEquals("Pedor", userScores.get(0).getName(), "Name is not correct");
        assertEquals(50, userScores.get(0).getScore(), "Score is not correct");
        assertEquals("2023-10-04", userScores.get(0).getDate(), "Date is not correct");
    }

    @Test
    public void testHighscoreSorting() throws Exception {
        UserScore alfa = new UserScore("Alfa", 100, "1908-01-04");
        UserScore beta = new UserScore("Beta", 30, "1945-05-08");
        UserScore gamma = new UserScore("Gamma", 50, "1940-04-09");
        UserScore zeta = new UserScore("Zeta", 480, "2021-09-15");
        HighscoreFileManager.writeToHighscore(alfa, testFile);
        HighscoreFileManager.writeToHighscore(beta, testFile);
        HighscoreFileManager.writeToHighscore(gamma, testFile);
        HighscoreFileManager.writeToHighscore(zeta, testFile);

        ObjectMapper jsonReader = new ObjectMapper();
        List<UserScore> userScores = jsonReader.readValue(testFile, new TypeReference<List<UserScore>>() {
        });

        assertEquals(4, userScores.size(), "Size is not correct: " + userScores.toString());

        assertEquals("Beta", userScores.get(0).getName(), "Name is not correct");
        assertEquals(30, userScores.get(0).getScore(), "Score is not correct");
        assertEquals("1945-05-08", userScores.get(0).getDate(), "Date is not correct");

        assertEquals("Gamma", userScores.get(1).getName(), "Name is not correct");
        assertEquals(50, userScores.get(1).getScore(), "Score is not correct");
        assertEquals("1940-04-09", userScores.get(1).getDate(), "Date is not correct");

        assertEquals("Alfa", userScores.get(2).getName(), "Name is not correct");
        assertEquals(100, userScores.get(2).getScore(), "Score is not correct");
        assertEquals("1908-01-04", userScores.get(2).getDate(), "Date is not correct");

        assertEquals("Zeta", userScores.get(3).getName(), "Name is not correct");
        assertEquals(480, userScores.get(3).getScore(), "Score is not correct");
        assertEquals("2021-09-15", userScores.get(3).getDate(), "Date is not correct");
    }

    /**
     * If we have come to this test, then we know that writing to highscore works,
     * therefore we can use HighscoreFileManager.writeToFile here..
     * 
     * @throws Exception If the file cannot be read.
     */
    @Test
    public void readFromHighscore() throws Exception {
        UserScore bert = new UserScore("Bert", 100, "2020-10-04");
        UserScore bernard = new UserScore("Bernard", 200, "2021-09-08");
        UserScore alfred = new UserScore("Alfred", 300, "2021-09-15");
        HighscoreFileManager.writeToHighscore(bert, testFile);
        HighscoreFileManager.writeToHighscore(bernard, testFile);
        HighscoreFileManager.writeToHighscore(alfred, testFile);

        List<UserScore> userScores = HighscoreFileManager.readFromHighscore(testFile);

        assertEquals(3, userScores.size(), userScores.toString());

        assertEquals("Bert", userScores.get(0).getName(), "Name is not correct");
        assertEquals(100, userScores.get(0).getScore(), "Score is not correct");
        assertEquals("2020-10-04", userScores.get(0).getDate(), "Date is not correct");

        assertEquals("Bernard", userScores.get(1).getName(), "Name is not correct");
        assertEquals(200, userScores.get(1).getScore(), "Score is not correct");
        assertEquals("2021-09-08", userScores.get(1).getDate(), "Date is not correct");

        assertEquals("Alfred", userScores.get(2).getName(), "Name is not correct");
        assertEquals(300, userScores.get(2).getScore(), "Score is not correct");
        assertEquals("2021-09-15", userScores.get(2).getDate(), "Date is not correct");
    }

    @Test
    public void readFromNonExistingFile() {

        // Setting up a new error stream, such that printstacktrace does not go off when
        // running mvn test.
        PrintStream originalErrorStream = System.err;
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) { // Don't write anything.
            }
        }));

        List<UserScore> userScores = HighscoreFileManager
                .readFromHighscore(new File("src/test/resources/nonExistingHighscore.json"));
        assertEquals(0, userScores.size(),
                "File is somehow not empty, even though you have read from a file which does not exist.");

        // Resetting the error stream.
        System.setErr(originalErrorStream);
    }
}
