package sample.models.framework.geometry;

import com.sun.corba.se.impl.corba.ContextListImpl;
import com.sun.org.glassfish.external.probe.provider.PluginPoint;
import javafx.scene.paint.Color;
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
        return new Circle(position.add(v), radius);
    }

    @Override
    public boolean containsPoint(Point2d p) {
        return position.distanceTo(p) < radius;
    }
}
