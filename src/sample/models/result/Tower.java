package sample.models.result;

import sample.models.Dynamic;
import sample.models.Owned;
import sample.models.Point;
import sample.models.World;

/**
 * Created by Alexander on 24/10/16.
 */
public abstract class Tower extends Obstacle implements Dynamic, Owned {
    public Tower(Point position, double radius, World world) {
        super(position, radius, world);
    }
}
