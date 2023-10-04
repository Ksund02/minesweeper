package storage;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class FileManagerTest {

    private static final File testFile = new File("src/test/resources/testHighscore.json");

    @BeforeEach
    public void setup() {
        HighscoreFileManager.clearHighscore(testFile);

    }

    @Test
    public void writeToHighscore() throws Exception {
        UserScore pedor = new UserScore("Pedor", 50, "2023-10-04");
        HighscoreFileManager.clearHighscore(testFile);
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
    public void klubb() {
    
    }



}
