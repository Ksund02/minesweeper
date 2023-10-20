package core.settings;

public enum GameDifficulty {
    TEST(5, 5, 5, 300, 400, 30),
    EASY(7, 7, 10, 300, 400, 30),
    MEDIUM(12, 10, 20, 600, 500, 30),
    HARD(14, 12, 40, 700, 600, 30);

    private final int gridWidth, gridHeight, numBombs, sceneMinWidth, sceneMinHeight, squareSize;

    private GameDifficulty(int gridWidth, int gridHeight, int numBombs, int sceneMinWidth, int sceneMinHeight,
            int squareSize) {
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
