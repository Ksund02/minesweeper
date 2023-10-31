package storage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import springBoot.HighscoreService;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@SpringBootTest
@AutoConfigureMockMvc
public class HighscoreRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private HighscoreService highscoreService;

    @Test
    public void testGetAllHighscores() throws Exception {
        mockMvc.perform(get("/highscores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
}
