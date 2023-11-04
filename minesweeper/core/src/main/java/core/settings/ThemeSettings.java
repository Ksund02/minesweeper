package core.settings;

public enum ThemeSettings {
  LIGHT(false),
  DARK(true);

  private final boolean isDarkMode;

  private ThemeSettings(boolean isDarkMode) {
    this.isDarkMode = isDarkMode;
  }

  public String getBackgroundStyle() {
    return isDarkMode ? "-fx-background-color: gray" : "-fx-background-color: white";
  }

  public String getTilePrefix() {
    return isDarkMode ? "/dark_" : "/";
  }
}
