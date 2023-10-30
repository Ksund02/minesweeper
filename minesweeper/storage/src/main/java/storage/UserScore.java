package storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserScore {

    @JsonProperty("name")
    private String name;

    @JsonProperty("score")
    private int score;

    @JsonProperty("date")
    private String date;

    @JsonProperty("difficulty")
    private String difficulty;

    @JsonCreator
    public UserScore(@JsonProperty("name") String name, @JsonProperty("score") int score,
            @JsonProperty("date") String date, @JsonProperty("difficulty") String difficulty) {
        this.name = name;
        this.score = score;
        this.date = date;
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "UserScore [name=" + name + ", score=" + score + ", date=" + date + ", difficulty=" + difficulty + "]";
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
