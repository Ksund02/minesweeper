package core.settings;

public enum GameDifficulty {
    TEST(5, 5, 5, 300, 300, 30),
    EASY(7, 7, 10, 500, 500, 30),
    MEDIUM(12, 10, 20, 600, 600, 30),
    HARD(14, 12, 40, 600, 600, 30);

    private final int gridWidth, gridHeight, numBombs, stageMinWidth, stageMinHeight, squareSize;

    private GameDifficulty(int gridWidth, int gridHeight, int numBombs, int stageMinWidth, int stageMinHeight,
            int squareSize) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.numBombs = numBombs;
        this.stageMinWidth = stageMinWidth;
        this.stageMinHeight = stageMinHeight;
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

    public int getStageMinWidth() {
        return stageMinWidth;
    }

    public int getStageMinHeight() {
        return stageMinHeight;
    }

    public int getSquareSize() {
        return squareSize;
    }

}
