package core;

public enum GameDifficulty {
    TEST(5, 5, 5, 300, 400, 30),
    EASY(9, 9, 10, 300, 400, 30),
    MEDIUM(10, 12, 20, 400, 500, 30),
    HARD(12, 14, 40, 500, 600, 30);

    private final int gridWidth, gridHeight, numBombs, sceneMinWidth, sceneMinHeight, squareSize;

    private GameDifficulty(int gridWidth, int gridHeight, int numBombs, int sceneMinWidth, int sceneMinHeight, int squareSize) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.numBombs = numBombs;
        this.sceneMinWidth = sceneMinWidth;
        this.sceneMinHeight = sceneMinHeight;
        this.squareSize = squareSize;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public int getSceneMinWidth() {
        return sceneMinWidth;
    }

    public int getSceneMinHeight() {
        return sceneMinHeight;
    }

    public int getSquareSize() {
        return squareSize;
    }

}
