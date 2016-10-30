package sample.models.modules;

import sample.models.Dynamic;

/**
 * Created by Alexander on 26/10/16.
 */
public class StopWatch implements Dynamic {
    private double interval;
    private double timeRemain;

    public StopWatch(double interval) {
        this.interval = interval;
        this.timeRemain = interval;
    }

    public double getInterval() {
        return interval;
    }

    public double getTimeRemain() {
        return timeRemain;
    }

    public boolean isFinished() {
        return timeRemain == 0;
    }

    public void reload() {
        timeRemain = interval;
    }

    @Override
    public void process(double time) {
        timeRemain = Math.max(0, timeRemain - time);
    }
}
