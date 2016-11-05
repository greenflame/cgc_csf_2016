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
            return position.distanceTo(s.position) < radius + ((Circle) s).radius;
        }

        throw new NotImplementedException();
    }

    @Override
    public Point2d collisionPointFor(Shape s) {

        if (s instanceof Circle) {
            Point2d relative = s.position.sub(position).normalized().mul(radius + ((Circle) s).radius);
            return position.add(relative);
        }

        throw new NotImplementedException();
    }

    @Override
    public Point2d collisionForceFor(Shape s) {

        if (s instanceof Circle) {
            if (isIntersect(s)) {
                return collisionPointFor(s).sub(s.position);
            } else {
                return new Point2d(0, 0);
            }
        }

        throw new NotImplementedException();
    }

    @Override
    public Shape moved(Point2d v) {
        return new Circle(position.add(v), radius);
    }
}
