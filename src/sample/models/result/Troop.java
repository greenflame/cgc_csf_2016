package sample.models.result;

import sample.models.*;
import sample.models.geometry.Point;

import java.util.Vector;

/**
 * Created by Alexander on 24/10/16.
 */
public abstract class Troop extends MovableUnit implements Owned, Dynamic {
    private int cost;
    private int count;
    private PlayerType owner;
    private Cannon cannon;
    private LifeCrystal lifeCrystal;
    private StopWatch deployer;

    public Troop(Point position, double radius, World world, double speed,
                 int cost, float hitSpeed, float deployTime, float range,
                 int count, int hitPoints, int damage, PlayerType owner) {
        super(position, radius, world, speed);

        this.cost = cost;
        this.count = count;
        this.owner = owner;

        cannon = new Cannon(damage, range, hitSpeed);
        lifeCrystal = new LifeCrystal(hitPoints);
        deployer = new StopWatch(deployTime);
    }

    public int getCost() {
        return cost;
    }

    public int getCount() {
        return count;
    }

    @Override
    public PlayerType getOwner() {
        return owner;
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

                        if (this.distanceTo(b) < this.distanceTo(a)) {
                            return b;
                        } else {
                            return a;
                        }
                    });

            if (aim != null) {
                double computedRange = this.cannon.getRange() != 0 ? this.cannon.getRange()
                        : this.getRadius() * 1.5f + aim.getRadius();

                if (this.distanceTo(aim) > computedRange) {
                    this.moveTo(aim, time);
                } else {
                    this.cannon.attack(aim.lifeCrystal);
                }
            }
        }
    }

    private Point collisionPoint(Troop troop) {
        Point troopRelative = this.getPosition().sub(troop.getPosition())
                .normilized().mul(this.getRadius() + troop.getRadius());
        return troop.getPosition().add(troopRelative);
    }

    public Point collisionResolverFor(Troop troop) {
        Point cp = collisionPoint(troop);
        boolean isCollided = this.distanceTo(troop) < cp.distanceTo(troop.getPosition());

        if (isCollided) {
            return cp.sub(this.getPosition());
        } else {
            return new Point();
        }
    }

    private Point collisionPoint(Obstacle obstacle) {
        double x = obstacle.getPosition().getX();
        double y = obstacle.getPosition().getY();
        double r = obstacle.getRadius();

        Point lu = new Point(x - r, y - r);
        Point ru = new Point(x + r, y - r);
        Point ld = new Point(x - r, y + r);
        Point rd = new Point(x + r, y + r);

        Vector<Point> corners = new Vector<>();
        corners.add(lu);
        corners.add(ld);
        corners.add(ru);
        corners.add(rd);

        double mx = getPosition().getX();
        double my = getPosition().getY();
        double mr = getRadius();

        // TODO: 28/10/16 angles
        // y mid
        if (my > y - r && my < y + r) {
            // x left
            if (mx < x - r) {
                return new Point(x - r - mr, my);
            }
            // x right
            if (mx > x + r) {
                return new Point(x + r + mr, my);
            }
        }

        // x mid
        if (mx > x - r && mx < x + r) {
            // y up
            if (my < y - r) {
                return new Point(mx, y - r - mr);
            }
            // y down
            if (my > y + r) {
                return new Point(mx, y + r + mr);
            }
        }

        for (Point corner : corners) {
            if (this.getPosition().distanceTo(corner) < this.getRadius()) {
                Point relative = this.getPosition().sub(corner).normilized().mul(this.getRadius());
                return corner.add(relative);
            }
        }

        return getPosition();
    }

    public boolean isCollided(Obstacle obstacle) {
        double x = obstacle.getPosition().getX();
        double y = obstacle.getPosition().getY();
        double r = obstacle.getRadius();

        double mx = getPosition().getX();
        double my = getPosition().getY();
        double mr = getRadius();

        return mx > x - r - mr &&
                mx < x + r + mr &&
                my > y - r - mr &&
                my < y + r + mr;
    }

    public Point collisionResolverFor(Obstacle obstacle) {
        Point cp = collisionPoint(obstacle);

        if (isCollided(obstacle)) {
            return cp.sub(this.getPosition());
        } else {
            return new Point();
        }
    }
}
