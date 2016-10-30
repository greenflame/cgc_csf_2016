package sample.models.result;

import sample.models.Square;
import sample.models.geometry.Point;
import sample.models.Body;
import sample.models.World;

/**
 * Created by Alexander on 24/10/16.
 */
public class Obstacle extends Square {
    public Obstacle(Point position, double rotation, World world, double side) {
        super(position, rotation, world, side);
    }
}
