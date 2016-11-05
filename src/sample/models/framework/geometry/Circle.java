package sample.models.framework.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Alexander on 04/11/16.
 */
public class Circle extends Shape {

    public final double radius;

    public Circle(Point2d position, double radius) {
        super(position);
        this.radius = radius;
    }

    @Override
    public boolean isIntersect(Shape s) {

        if (s instanceof Circle) {
            return isIntersect((Circle) s);
        }

        throw new NotImplementedException();
    }

    private boolean isIntersect(Circle c) {
        return position.distanceTo(c.position) < radius + c.radius;
    }

    @Override
    public Point2d collisionPointFor(Shape s) {

        if (s instanceof Circle) {
            return collisionPointFor((Circle) s);
        }

        throw new NotImplementedException();
    }

    private Point2d collisionPointFor(Circle c) {
        Point2d relative = c.position.sub(position).normalized().mul(radius + c.radius);
        return position.add(relative);
    }

    @Override
    public Shape moved(Point2d v) {
        return new Circle(position.add(v), radius);
    }

    @Override
    public boolean containsPoint(Point2d p) {
        return position.distanceTo(p) < radius;
    }
}
