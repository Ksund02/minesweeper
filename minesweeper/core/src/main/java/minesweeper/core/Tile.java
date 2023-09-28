package minesweeper.core;

public class Tile {

    private boolean isBomb;
    private boolean isFlagged;
    private boolean isRevealed;
    private int numBombsAround;

    public Tile() {
        this.isBomb = false;
        this.isFlagged = false;
        this.isRevealed = false;
        this.numBombsAround = 0;
    }

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
        return this.numBombsAround;
    }

    public void toggleFlag() {
        isFlagged = !isFlagged;
    }

    public void reveal() {
        isRevealed = true;
    }

    protected void placeBomb() {
        isBomb = true;
    }

    protected void incrementNumBombsAround() {
        this.numBombsAround++;
    }

    public String getRevealedImagePath() {
        String relativePath = "file:src\\main\\resources\\minesweeper\\images\\";
        if (isBomb) {
            return relativePath + "bomb.png";
        }
        return relativePath + "number" + getNumBombsAround() + ".jpg";
    }

    @Override
    public String toString() {
        String info = "";
        if (isFlagged) {
            info = "F";
        } else if (isBomb) {
            info = "X";
        } else if (!isRevealed()) {
            info = "?";
        } else {
            info = String.valueOf(numBombsAround);
        }
        return info;
    }
}