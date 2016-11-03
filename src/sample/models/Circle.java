package sample.models;

import sample.models.geometry.primitives.Point2d;

/**
 * Created by Alexander on 29/10/16.
 */
public class Circle extends Body {
    private double radius;

    public Circle(Point2d position, double rotation, World world, double radius) {
        super(position, rotation, world);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
