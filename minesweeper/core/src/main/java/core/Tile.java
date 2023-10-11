package core;

public class Tile {

    private boolean isBomb, isFlagged, isRevealed;
    private int numBombsAround;
    private int xCoordinat, yCoordinat;

    public Tile(int xCoordinat, int yCoordinat) {
        this.xCoordinat = xCoordinat;
        this.yCoordinat = yCoordinat;
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

    public boolean hasAdjacentBomb() {
        return this.getNumBombsAround() > 0;
    }

    private int getNumBombsAround() {
        return numBombsAround;
    }

    public void toggleFlag() {
        isFlagged = !isFlagged;
    }

    public void reveal() {
        isRevealed = true;
    }

    public int getXCoordinate() {
        return xCoordinat;
    }

    public int getYCoordinate() {
        return yCoordinat;
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
        if (isFlagged)
            return "F";

        if (isBomb)
            return "X";

        if (!isRevealed())
            return "?";

        if (getNumBombsAround() == 0)
            return "";

        return String.valueOf(getNumBombsAround());
    }
}