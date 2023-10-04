package core;

public class Tile {

    private boolean isBomb, isFlagged, isRevealed;
    private int numBombsAround;

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int getNumBombsAround() {
        return numBombsAround;
    }

    public void toggleFlag() {
        isFlagged = !isFlagged;
    }

    public void reveal() {
        isRevealed = true;
    }

    protected void makeBomb() {
        isBomb = true;
    }

    protected void incrementNumBombsAround() {
        this.numBombsAround++;
    }

    public String getRevealedImagePath() {
        if (isFlagged) 
            return "/flag.png";
        else if (!isRevealed) 
            return "/square.jpg";
        else if (isBomb) 
            return "/bomb.png";
        else 
            return "/number" + getNumBombsAround() + ".jpg";        
    }

    @Override
    public String toString() {
        String info = "";
        if (isFlagged) 
            info = "F";
        else if (isBomb) 
            info = "X";
        else if (!isRevealed()) 
            info = "?";
        else 
            info = String.valueOf(numBombsAround);
        return info;
    }
}