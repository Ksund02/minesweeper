package springBoot;

import java.util.List;
import storage.UserScore;
import storage.HighscoreFileManager;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HighscoreController {

    @GetMapping("/highscores")
    public List<UserScore> getAllHighscores() {
        return HighscoreFileManager.readFromHighscore(HighscoreFileManager.getFile());
    }

    @PostMapping("/highscores")
    public void addHighscore(@RequestBody UserScore userScore) {
        HighscoreFileManager.writeToHighscore(userScore, HighscoreFileManager.getFile());
    }

    @DeleteMapping("/highscores")
    public void clearAllHighscores() {
        HighscoreFileManager.clearHighscore(HighscoreFileManager.getFile());
    }
}

