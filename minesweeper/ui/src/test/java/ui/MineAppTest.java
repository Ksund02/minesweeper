package ui;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import core.settings.SettingsManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

// New syntax for using JUnit 5, enables the use of the @Start annotation.
@ExtendWith(ApplicationExtension.class) 
public class MineAppTest {

  private Stage stage;

  @Start
  private void start(Stage stage) throws IOException {
    this.stage = stage;
    new MineApp().start(stage);
  }

  @Test
  public void testIconIsPresent() {
    assertFalse(stage.getIcons().isEmpty());
  }

  @Test
  public void testStageSize() {
    WaitForAsyncUtils.waitForFxEvents(); // Wait for all javafx operations to finish before testing stage size.
    WaitForAsyncUtils.sleep(300, java.util.concurrent.TimeUnit.MILLISECONDS);
    assertEquals(SettingsManager.getGameDifficulty().getStageMinWidth(), stage.getWidth(), "Stage width should match settings");
    assertEquals(SettingsManager.getGameDifficulty().getStageMinHeight(), stage.getHeight(), "Stage height should match settings");
  }
}
