package sample.models.framework.geometry;

/**
 * Created by Alexander on 04/11/16.
 */
public abstract class Shape {

    public final Point2d position;

    public Shape(Point2d position) {
        this.position = position;
    }

    abstract public boolean isIntersect(Shape s);

    abstract public Point2d collisionPointFor(Shape s);

    abstract public Point2d collisionForceFor(Shape s);

    abstract public Shape moved(Point2d v);
}
