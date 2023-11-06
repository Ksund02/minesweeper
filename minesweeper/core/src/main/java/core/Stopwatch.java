package core;

import java.time.LocalDate;

public class Stopwatch {
  private String date;
  private long timeWhenStarted;
  private int endTime;
  private boolean finished;
  private boolean started;

  public Stopwatch() {
    restart();
  }

  /**
   * Starts the stopwatch.
   */
  public void start() {
    this.started = true;
    this.timeWhenStarted = System.currentTimeMillis();
  }

  /**
   * Stops the stopwatch.
   */
  public void stop() {
    if (!finished && started) {
      this.endTime = (int) ((System.currentTimeMillis() - this.timeWhenStarted) / 1000);
      finished = true;
    }
  }

  /**
   * Main method for the Stopwatch class.
   * 
   * @return the time used in seconds
   */
  public int getTime() {
    if (finished && started) {
      return endTime;
    }

    int timeUsed = ((int) (System.currentTimeMillis() - this.timeWhenStarted) / 1000);
    boolean usedTooLongTime = timeUsed > 999;

    if (started && usedTooLongTime) {
      finished = true;
      return 999;
    }

    if (started) {
      return timeUsed;
    }
    return 0;
  }

  public String getDate() {
    return this.date;
  }

  public boolean isStarted() {
    return this.started;
  }

  /**
   * Restarts the stopwatch.
   * Sets the date to the current date, and sets the time to 0.
   */
  public void restart() {
    this.date = "" + LocalDate.now();
    this.timeWhenStarted = 0;
    this.endTime = 0;
    this.finished = false;
    this.started = false;
  }
}
