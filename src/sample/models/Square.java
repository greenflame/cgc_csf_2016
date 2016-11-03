package sample.models;

import sample.models.geometry.primitives.Point2d;

/**
 * Created by Alexander on 29/10/16.
 */
public class Square extends Body {
    private double side;

    public Square(Point2d position, double rotation, World world, double side) {
        super(position, rotation, world);
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    public double getHalfSide() {
        return side / 2;
    }
}
