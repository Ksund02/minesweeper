package core.settings;

public class SettingsManager {

    public static GameDifficulty gameDifficulty = GameDifficulty.EASY;
    public static ThemeSettings themeSettings = ThemeSettings.LIGHT;

    public static int getStageMinHeight() {
        return gameDifficulty.getStageMinHeight();
    }

    public static int getStageMinWidth() {
        return gameDifficulty.getStageMinWidth();
    }

    public static int getSquareSize() {
        return gameDifficulty.getSquareSize();
    }

}
