package core;

import java.time.LocalDate;

public class Stopwatch {
    private String date;
    private long nowTime;
    private int endTime;
    private boolean finished;
    private boolean started;

    public Stopwatch() {
        this.date = ""+LocalDate.now();
        this.nowTime = 0;
        this.endTime = 0;
        this.finished = false;
        this.started = false;
    }

    public void start() {
        this.started = true;
        this.nowTime = System.currentTimeMillis();
    }

    public void stop() {
        if (!finished && started) {
            this.endTime = (int) ((System.currentTimeMillis()-this.nowTime)/1000);
            finished = true;
        }
    }

    public int getTime() {
        if (finished && started) {
            return endTime;
        }
        else if (started) {
            if ((int) ((System.currentTimeMillis()-this.nowTime)/1000)>999) {
                finished = true;
                return 999;
            }
            return (int) ((System.currentTimeMillis()-this.nowTime)/1000);
        }
        return 0;
    }

    public String getDate() {
        return this.date;
    }

    public boolean started() {
        return this.started;
    }

    public void restart() {
        this.date = ""+LocalDate.now();
        this.nowTime = 0;
        this.endTime = 0;
        this.finished = false;
        this.started = false;
    }

}
