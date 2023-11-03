package core;

public class Tile implements TileReadable {

    private boolean isBomb, isFlagged, isRevealed;
    private int numBombsAround;
    private final int x, y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isBomb() {
        return isBomb;
    }

    @Override
    public boolean isFlagged() {
        return isFlagged;
    }

    @Override
    public boolean isRevealed() {
        return isRevealed;
    }

    @Override
    public boolean hasAdjacentBomb() {
        return this.getNumBombsAround() > 0;
    }

    public int getNumBombsAround() {
        return numBombsAround;
    }

    public void toggleFlag() {
        if (this.isRevealed())
            throw new IllegalStateException("Can not flag a revealed tile");

        isFlagged = !isFlagged;
    }

    public void reveal() {
        if (this.isFlagged())
            throw new IllegalStateException("Can not reveal a flagged tile");

        isRevealed = true;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    protected void makeBomb() {
        isBomb = true;
    }

    protected void incrementNumBombsAround() {
        this.numBombsAround++;
    }

    public String getRevealedImagePath() {
        if (isFlagged)
            return "flag.png";
        else if (!isRevealed)
            return "square.jpg";
        else if (isBomb)
            return "bomb.png";
        else
            return "number" + getNumBombsAround() + ".jpg";
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