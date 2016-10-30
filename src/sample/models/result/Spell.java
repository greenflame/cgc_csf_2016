package sample.models.result;

import sample.models.*;
import sample.models.geometry.Point;

/**
 * Created by Alexander on 24/10/16.
 */
public abstract class Spell extends Circle implements Dynamic, Owned {
    public Spell(Point position, double rotation, World world, double radius) {
        super(position, rotation, world, radius);
    }
}
