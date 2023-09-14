package minesweeper.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
    private static final String filePath = System.getProperty("user.dir")+"/g2302/minesweeper/src/main/resources/minesweeper/highscore.csv";

    public List<UserScore> readFromFile() {
        List<UserScore> userScores = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (!line.equals("")) {
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
        FileIO fileReader = new FileIO();
        fileReader.readFromFile();
        System.out.println((System.getProperty("user.dir")));
    }
}
