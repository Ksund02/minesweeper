package minesweeper.app.readeAndWriteFile;

public class UserScore {
    private String name;
    private int score;
    private String date;

    public UserScore(String name, int score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public String getUserLine() {
        return name + "," + score + "," + date;
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
