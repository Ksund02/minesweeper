package springBoot;

import java.util.List;
import org.springframework.stereotype.Service;
import storage.HighscoreFileManager;
import storage.UserScore;

@Service
public class HighscoreService {

    public List<UserScore> getAllHighscores() {
        return HighscoreFileManager.readFromHighscore(HighscoreFileManager.getFile());
    }

    public void addHighscore(UserScore userScore) {
        HighscoreFileManager.writeToHighscore(userScore, HighscoreFileManager.getFile());
    }

    public void clearAllHighscores() {
        HighscoreFileManager.clearHighscore(HighscoreFileManager.getFile());
    }
}