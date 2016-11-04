package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;

/**
 * Created by Alexander on 03/11/16.
 */
public class Deployer extends Component {

    private double interval;

    private double timeRemain;

    public Deployer(GameObject gameObject, double interval) {
        super(gameObject);
        this.interval = interval;
        timeRemain = interval;
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

    @Override
    public void process(double interval) {
        timeRemain = Math.max(0, timeRemain - interval);
    }
}
