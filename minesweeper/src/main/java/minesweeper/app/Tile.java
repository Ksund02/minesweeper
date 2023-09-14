package minesweeper.app;


public class Tile {
    
    private boolean isBomb;
    private boolean isFlagged;
    private boolean isRevealed;
    private int numBombsAround;

    public Tile(boolean isBomb, int numBombsAround) { 
        this.isBomb = isBomb;
        this.numBombsAround = numBombsAround;
        this.isFlagged = false; 
        this.isRevealed = false; 
    }

    public boolean isBomb() {
        return isBomb;
    }
    
    public int getNumBombsAround() {
        return this.numBombsAround;
    }

    public boolean isFlagged() {
        return isFlagged;
    }
    

    public boolean isRevealed() {
        return isRevealed;
    }

    public void reveal() {
        isRevealed = true;
    }

    public void toggleFlag() {
        isFlagged = !isFlagged;
    }

    public void incrementNumBombsAround() {
        this.numBombsAround++;
    }
}

