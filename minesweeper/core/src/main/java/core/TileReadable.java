package core;

public interface TileReadable {
  public boolean isBomb();

  public boolean isFlagged();

  public boolean isRevealed();

  public boolean hasAdjacentBomb();

  public int getX();

  public int getY();

  public String getRevealedImagePath();
}
