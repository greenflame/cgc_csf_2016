package sample.models.framework.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander on 04/11/16.
 */
public class Square extends Shape {

    public final double radius;

    public Square(Point2d position, double radius) {
        super(position);
        this.radius = radius;
    }

    @Override
    public boolean isIntersect(Shape s) {

        if (s instanceof Circle) {
            return Collisions.isIntersect(this, (Circle) s);
        }

        if (s instanceof Square) {
            return Collisions.isIntersect(this, (Square) s);
        }

        throw new NotImplementedException();
    }

    @Override
    public Point2d collisionPointFor(Shape s) {

        if (s instanceof Circle) {
            return Collisions.collisionPointFor(this, (Circle) s);
        }

        if (s instanceof Square) {
            return Collisions.collisionPointFor(this, (Square) s);
        }

        throw new NotImplementedException();
    }

    @Override
    public Shape moved(Point2d v) {
        return new Square(position.add(v), radius);
    }

    @Override
    public boolean containsPoint(Point2d p) {
        return p.x > position.x - radius &&
                p.x < position.x + radius &&
                p.y > position.y - radius &&
                p.y < position.y + radius;
    }

    public Point2d leftUp() {
        return position.add(new Point2d(-radius, -radius));
    }

    public Point2d rightUp() {
        return position.add(new Point2d(radius, -radius));
    }

    public Point2d leftDown() {
        return position.add(new Point2d(-radius, radius));
    }

    public Point2d rightDown() {
        return position.add(new Point2d(radius, radius));
    }

    public List<Point2d> corners() {
        List<Point2d> result = new LinkedList<>();

        result.add(leftUp());
        result.add(rightUp());
        result.add(leftDown());
        result.add(rightDown());

        return result;
    }
}
