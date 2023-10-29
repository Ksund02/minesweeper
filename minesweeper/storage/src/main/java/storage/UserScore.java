package storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserScore {

    @JsonProperty("name")
    private String name;

    @JsonProperty("score")
    private int score;

    @JsonProperty("date")
    private String date;

    @JsonCreator
    public UserScore(@JsonProperty("name") String name, @JsonProperty("score") int score,
            @JsonProperty("date") String date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    /**
     * Converts the UserScore object to a JSON string
     * JSONS strings are of the form: {"name":"Roger","score":100,"date":"2021-04-20"}
     * @return JSON string representation of the UserScore object
     */
    public String toJson() {
        ObjectMapper jsonConverter = new ObjectMapper();
        try {
            return jsonConverter.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String toString() {
        return "UserScore [name=" + name + ", score=" + score + ", date=" + date + "]";
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
}
