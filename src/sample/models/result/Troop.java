package sample.models.result;

import sample.models.*;
import sample.models.geometry.primitives.Point2d;
import sample.models.geometry.RouteSolver;
import sample.models.modules.Cannon;
import sample.models.modules.LifeCrystal;
import sample.models.modules.StopWatch;

/**
 * Created by Alexander on 24/10/16.
 */
public abstract class Troop extends Circle implements Dynamic, Owned {
    private int cost;
    private int count;
    private double speed;
    private Cannon cannon;
    private LifeCrystal lifeCrystal;
    private StopWatch deployer;
    private PlayerType owner;

    public Troop(Point2d position, double rotation, World world, double radius, int cost, double hitSpeed, double speed,
                 double deployTime, double range, int count, int hitPoints, int damage, PlayerType owner) {
        super(position, rotation, world, radius);

        this.cost = cost;
        this.count = count;
        this.speed = speed;

        cannon = new Cannon(damage, range, hitSpeed);
        lifeCrystal = new LifeCrystal(hitPoints);
        deployer = new StopWatch(deployTime);

        this.owner = owner;
    }

    public int getCost() {
        return cost;
    }

    public int getCount() {
        return count;
    }

    public double getSpeed() {
        return speed;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public LifeCrystal getLifeCrystal() {
        return lifeCrystal;
    }

    public StopWatch getDeployer() {
        return deployer;
    }

    @Override
    public PlayerType getOwner() {
        return owner;
    }

    @Override
    public void process(double time) {
        cannon.process(time);
        deployer.process(time);

        if (deployer.isFinished()) {
            Troop aim = getWorld().getTroops().stream()
                    .filter(u -> u.deployer.isFinished() && !u.lifeCrystal.isDamaged() && u.getOwner() != owner)
                    .reduce(null, (a, b) -> {
                        if (a == null) {
                            return b;
                        }

                        if (b == null) {
                            return a;
                        }

                        if (RouteSolver.distance(this, b) < RouteSolver.distance(this, a)) {
                            return b;
                        } else {
                            return a;
                        }
                    });

            if (aim != null) {
                double computedRange = this.cannon.getRange() != 0 ? this.cannon.getRange()
                        : this.getRadius() * 1.3f + aim.getRadius();

                if (this.rawDistanceTo(aim) > computedRange) {
                    Point2d direction = RouteSolver.direction(this, aim);
                    this.setPosition(this.getPosition().add(direction.mul(speed * time)));
                } else {
                    this.cannon.attack(aim.lifeCrystal);
                }
            }
        }
    }
}
