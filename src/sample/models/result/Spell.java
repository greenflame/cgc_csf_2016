package sample.models.result;

import sample.models.*;

/**
 * Created by Alexander on 24/10/16.
 */
public abstract class Spell extends Unit implements Dynamic, Owned {
    public Spell(Point position, double radius, World world) {
        super(position, radius, world);
    }
}
