package sample.models.result;

import sample.models.geometry.Point;
import sample.models.Unit;
import sample.models.World;

/**
 * Created by Alexander on 24/10/16.
 */
public class Obstacle extends Unit {
    public Obstacle(Point position, double radius, World world) {
        super(position, radius, world);
    }
}
