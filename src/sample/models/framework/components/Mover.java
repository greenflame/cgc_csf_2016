package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;
import sample.models.framework.structures.Point2d;

/**
 * Created by Alexander on 04/11/16.
 */
public class Mover extends Component {

    private double speed;

    private Point2d destination;

    public Mover(GameObject gameObject, double speed, Point2d destination) {
        super(gameObject);
        this.speed = speed;
        this.destination = destination;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Point2d getDestination() {
        return destination;
    }

    public void setDestination(Point2d destination) {
        this.destination = destination;
    }

    @Override
    public void process(double interval) {

    }
}
