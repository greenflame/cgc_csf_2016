package sample.models.result;

import sample.models.*;
import sample.models.geometry.primitives.Point2d;
import sample.models.modules.Cannon;
import sample.models.modules.LifeCrystal;

/**
 * Created by Alexander on 24/10/16.
 */
public abstract class Tower extends Square implements Dynamic, Owned {
    private Cannon cannon;
    private LifeCrystal lifeCrystal;
    private PlayerType owner;

    public Tower(Point2d position, double rotation, World world, double side,
                 double hitSpeed, double range, int hitPoints, int damage, PlayerType owner) {
        super(position, rotation, world, side);

        this.cannon = new Cannon(damage, range, hitSpeed);
        this.lifeCrystal = new LifeCrystal(hitPoints);

        this.owner = owner;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public LifeCrystal getLifeCrystal() {
        return lifeCrystal;
    }

    @Override
    public PlayerType getOwner() {
        return owner;
    }

    @Override
    public void process(double time) {

    }
}
