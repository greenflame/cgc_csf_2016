package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;
import sample.models.framework.geometry.Shape;

/**
 * Created by Alexander on 04/11/16.
 */
public class PhysicBody extends Component {

    private Shape shape;

    private boolean isMovable;

    public PhysicBody(GameObject gameObject, Shape shape, boolean isMovable) {
        super(gameObject);
        this.shape = shape;
        this.isMovable = isMovable;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setMovable(boolean movable) {
        isMovable = movable;
    }
}
