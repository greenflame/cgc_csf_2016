package sample.models.framework.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        throw new NotImplementedException();
    }

    @Override
    public Point2d collisionPointFor(Shape s) {
        throw new NotImplementedException();
    }

    @Override
    public Point2d collisionForceFor(Shape s) {
        throw new NotImplementedException();
    }

    @Override
    public Shape moved(Point2d v) {
        return new Square(position.add(v), radius);
    }
}
