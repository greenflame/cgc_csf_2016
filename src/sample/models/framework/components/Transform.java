package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;
import sample.models.framework.geometry.Point2d;

/**
 * Created by Alexander on 03/11/16.
 */
public class Transform extends Component {

    private Point2d position;

    private float rotation;

    public Transform(GameObject gameObject, Point2d position, float rotation) {
        super(gameObject);
        this.position = position;
        this.rotation = rotation;
    }

    public Point2d getPosition() {
        return position;
    }

    public void setPosition(Point2d position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void process(double interval) {

    }
}
