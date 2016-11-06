package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;
import sample.models.framework.geometry.Shape;

/**
 * Created by Alexander on 04/11/16.
 */
public class PhysicBody extends Component {

    private Shape shape;

    private double mass;

    public PhysicBody(GameObject gameObject, Shape shape, double mass) {
        super(gameObject);
        this.shape = shape;
        this.mass = mass;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
}
