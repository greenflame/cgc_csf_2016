package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;

/**
 * Created by Alexander on 03/11/16.
 */
public class Clock extends Component {

    private double time;

    public Clock(GameObject gameObject) {
        super(gameObject);
        time = 0;
    }

    public double getTime() {
        return time;
    }

    @Override
    public void process(double interval) {
        time += interval;
    }
}
