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

    public Point2d collisionForceFor(Shape s) {
        if (isIntersect(s)) {
            return collisionPointFor(s).sub(s.position);
        } else {
            return new Point2d(0, 0);
        }
    }

    abstract public Shape moved(Point2d v);

    abstract public boolean containsPoint(Point2d p);
}
