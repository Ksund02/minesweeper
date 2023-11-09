package springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import storage.HighscoreFileManager;
import storage.UserScore;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.io.FileInputStream;
import java.util.List;

/**
 * This class is very similar to the HighscoreRestControllerTest class.
 * The difference is that this class tests the HighscoreService class, which is
 * in contrast to the controller test, where a mock version of HighscoreService
 * is used.
 * 
 * So you can look at this class as an extension of the
 * HighscoreRestControllerTest-class.
 * It is possible that the ControllerTest-class passes all the tests, but the
 * ServiceTest-class fails. This is because the ServiceTest-class tests the
 * actual implementation of the service-methods, while the ControllerTest-class
 * tests
 * just tests that the HTTP requests are handled correctly.
 */
@SpringBootTest(classes = SpringApp.class)
@AutoConfigureMockMvc
public class HighscoreServiceTest {
  @Autowired
  private MockMvc mockMvc; // This guy is used to simulate HTTP interactions. It's a mock, so it's not a
                           // real server.

  @Autowired
  private ObjectMapper objectMapper; // Instantiating a new field variable of type ObjectMapper with the @Autowired
                                     // annotation.

  @Test
  public void testGetAllHighscores() throws Exception {

    List<UserScore> highscores = objectMapper.readValue(new FileInputStream("./../appdata/highscore.json"),
        new TypeReference<List<UserScore>>() {
        }); // Read in the userScores from the highscore.json file.

    mockMvc.perform(get("/highscores")) // Perform a GET request to the /highscores endpoint.
        .andExpect(status().isOk()) // Expect the status code to be 200 (OK).
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Expect the content type to be JSON.
        // Check that the response in JSON format is equal to the highscores list
        // written as a JSON string.
        .andExpect(content().json(objectMapper.writeValueAsString(highscores)));
  }

  @Test
  public void addHighscore() throws Exception {
    UserScore oskar = new UserScore("oskar", 15, "2023-10-15", "EASY");

    mockMvc.perform(post("/highscores") // Perform a POST request to the /highscores endpoint.
        .contentType(MediaType.APPLICATION_JSON) // Set the content type to JSON.
        .content(objectMapper.writeValueAsString(oskar))) // Set the content of the request to the oskar
                                                          // UserScore object written as a JSON string.
        .andExpect(status().isOk()); // Expect the status code to be 200 (OK).

    List<UserScore> highscores = objectMapper.readValue(new FileInputStream("./../appdata/highscore.json"),
        new TypeReference<List<UserScore>>() {
        }); // Read in the userScores from the highscore.json file.

    // Check that the oskar UserScore object is in the highscores list.
    assertTrue(highscores.stream()
        .anyMatch(userScore -> userScore.getName().equals(oskar.getName()) && userScore.getScore() == oskar.getScore()
            && userScore.getDate().equals(oskar.getDate()) && userScore.getDifficulty().equals(oskar.getDifficulty())));

    // Delete oskar from the highscore file, he is only there for tsting purposes.
    HighscoreFileManager.deleteFromHighscore(oskar.getName(), oskar.getScore(), oskar.getDate(), oskar.getDifficulty(),
        HighscoreFileManager.getFile());
  }

  @Test
  public void clearAllHighscores() throws Exception {

    // Start by saving the original highscores.
    List<UserScore> highscores = objectMapper.readValue(new FileInputStream("./../appdata/highscore.json"),
        new TypeReference<List<UserScore>>() {
        });

    mockMvc.perform(delete("/highscores")) // Perform a DELETE request to the /highscores endpoint.
        .andExpect(status().isOk()); // Expect the status code to be 200 (OK).

    List<UserScore> shouldBeEmpty = objectMapper.readValue(new FileInputStream("./../appdata/highscore.json"),
        new TypeReference<List<UserScore>>() {
        }); // Read in the userScores from the highscore.json file.

    // Check that the highscores list is empty.
    assertTrue(shouldBeEmpty.isEmpty());

    // Write the original highscores back to the highscore file.
    highscores.stream()
        .forEach(highscore -> HighscoreFileManager.writeToHighscore(highscore, HighscoreFileManager.getFile()));
  }
}
