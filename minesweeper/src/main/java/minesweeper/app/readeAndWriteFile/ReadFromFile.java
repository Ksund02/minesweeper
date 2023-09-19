package minesweeper.app.readeAndWriteFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import minesweeper.app.UserScore;

public class ReadFromFile {
    private static final String filePath = System.getProperty("user.dir")+"/src/main/resources/minesweeper/highscore.csv";

    public List<UserScore> readFromFile() {
        List<UserScore> userScores = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            reader.readLine();
            String line = reader.readLine();
            while (line != null) {
                String[] lineArray = line.split(",");
                String name = lineArray[0];
                int score = Integer.parseInt(lineArray[1]);
                String date = lineArray[2];
                userScores.add(new UserScore(name, score, date));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userScores;
    }
    
    public static void main(String[] args) {
        ReadFromFile fileReader = new ReadFromFile();
        List<UserScore> userScores = fileReader.readFromFile();
        for (UserScore userScore : userScores) {
            System.out.println(userScore);
        }
    
    }
}
