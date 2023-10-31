package storage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import springBoot.HighscoreService;
import springBoot.SpringApp;

@SpringBootTest(classes = SpringApp.class)
@AutoConfigureMockMvc
public class HighscoreRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HighscoreService highscoreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllHighscores() throws Exception {
        UserScore oskar = new UserScore("oskar", 15, "2023-10-15", "EASY");
        UserScore david = new UserScore("david", 20, "2023-10-08", "EASY");
        List<UserScore> highscores = Arrays.asList(oskar, david);

        when(highscoreService.getAllHighscores()).thenReturn(highscores);

        mockMvc.perform(get("/highscores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(highscores)));
    }   
}