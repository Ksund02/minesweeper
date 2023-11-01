package storage;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;
import springBoot.HighscoreService;
import springBoot.SpringApp;

/**
 * This class is used to test the HighscoreRestController.
 * It sets up a mock-version of highscoreService, and then uses this mock to
 * test
 * the different methods in HighscoreRestController.
 */
@SpringBootTest(classes = SpringApp.class)
@AutoConfigureMockMvc
public class HighscoreRestControllerTest {

    @Autowired
    private MockMvc mockMvc; // This guy is used to simulate HTTP interactions. It's a mock, so it's not a
                             // real server.

    @MockBean
    private HighscoreService highscoreService;

    @Autowired
    private ObjectMapper objectMapper; // Instantiating a new field variable of type ObjectMapper with the @Autowired
                                       // annotation.

    @Test
    public void testGetAllHighscores() throws Exception {

        UserScore oskar = new UserScore("oskar", 15, "2023-10-15", "EASY");
        UserScore david = new UserScore("david", 20, "2023-10-08", "EASY");
        List<UserScore> highscores = Arrays.asList(oskar, david);

        when(highscoreService.getAllHighscores()).thenReturn(highscores);

        mockMvc.perform(get("/highscores")) // Perform a GET request to the /highscores endpoint.
                .andExpect(status().isOk()) // Expect the status code to be 200 (OK).
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Expect the content type to be JSON.
                // Check that the response in JSON format is equal to the highscores list
                // written as a JSON string.
                .andExpect(content().json(objectMapper.writeValueAsString(highscores)));
    }

    @Test
    public void testAddHighscore() throws Exception {
        UserScore oskar = new UserScore("oskar", 15, "2023-10-15", "EASY");

        mockMvc.perform(post("/highscores") // Perform a POST request to the /highscores endpoint.
                .contentType(MediaType.APPLICATION_JSON) // Specify that the content type is JSON.
                .content(objectMapper.writeValueAsString(oskar))) // Write the UserScore object as a JSON string.
                .andExpect(status().isOk()); // Expect the status code to be 200 (OK).

        // Capture the UserScore object which is passed to the addHighscore method.
        ArgumentCaptor<UserScore> captor = ArgumentCaptor.forClass(UserScore.class);
        // Verify that the addHighscore method is called exactly once, and capture the
        // UserScore object which was passed to the method.
        verify(highscoreService, times(1)).addHighscore(captor.capture());

        UserScore capturedUser = captor.getValue(); // Getting the object which was sent in the original request.

        // Checking that that addHighscore was called with the correct UserScore object.
        assertEquals("oskar", capturedUser.getName());
        assertEquals(15, capturedUser.getScore());
        assertEquals("2023-10-15", capturedUser.getDate());
        assertEquals("EASY", capturedUser.getDifficulty());
    }

    @Test
    public void testClearHighscore() throws Exception {

        mockMvc.perform(delete("/highscores")) // Send a DELETE request to /highscores.
                .andExpect(status().isOk()); // We expect everything to be in order.

        // Check that the clearAllHighscores method was called exactly once.
        verify(highscoreService, times(1)).clearAllHighscores();
    }
}