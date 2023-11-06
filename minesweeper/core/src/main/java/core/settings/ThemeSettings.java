package core.settings;

public enum ThemeSettings {
    LIGHT("white", "white;", "/"),
    DARK("gray", "black;", "/dark_");

    private final String backgroundCollorScene;
    private final String backgroundCollorGameGrid;
    private final String tilePrefix;

    private ThemeSettings(String backgroundCollorScene, String backgroundCollorGameGrid, String tilePrefix) {
        this.backgroundCollorScene = "-fx-background-color: " + backgroundCollorScene;
        this.backgroundCollorGameGrid = "-fx-background-color: " + backgroundCollorGameGrid;
        this.tilePrefix = tilePrefix;
    }

    public String getBackgroundStyle() {
        return backgroundCollorScene;
    }

    public String getBackgroundStyleGameGrid() {
        return backgroundCollorGameGrid;
    }

    public String getTilePrefix() {
        return tilePrefix;
    }
}
