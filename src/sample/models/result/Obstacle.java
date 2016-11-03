package sample.models.result;

import sample.models.Square;
import sample.models.geometry.primitives.Point2d;
import sample.models.World;

/**
 * Created by Alexander on 24/10/16.
 */
public class Obstacle extends Square {
    public Obstacle(Point2d position, double rotation, World world, double side) {
        super(position, rotation, world, side);
    }
}
