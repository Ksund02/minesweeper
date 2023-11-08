package storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserScoreTest {

  @Test
  public void testJson() {
    UserScore albert = new UserScore("Albert", 100, "2020-10-04", "Easy");
    String expectedJson = "{\"name\":\"Albert\",\"score\":100,\"date\":\"2020-10-04\",\"difficulty\":\"Easy\"}";
    assertEquals(expectedJson, albert.toJson(), "JSON string is not correct");
  }
  
}