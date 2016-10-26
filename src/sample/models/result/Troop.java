package sample.models.result;

import sample.models.*;

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
                 int count,int hitPoints, int damage, PlayerType owner) {
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
        return null;
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

        this.moveTo(getWorld().getTroops().get(0), time);
        System.out.println(getPosition().toString());

//        // Find closest enemy troop
//        Troop aim = getWorld().getTroops().stream()
//                .filter(u -> u.getOwner() != owner)
//                .reduce(null, (a, b) -> {
//                    if (a == null) {
//                        return b;
//                    } else {
//                        if (this.distanceTo(b) < this.distanceTo(a)) {
//                            return b;
//                        } else {
//                            return a;
//                        }
//                    }
//                }); // todo ?
//
////        double computedRange = this.cannon.getRange() != 0 ? this.cannon.getRange()
////                : this.getRadius() * 1.1f + aim.getRadius();
//
//        double computedRange = 2;
//
//        if (aim != null) {
//            if (this.distanceTo(aim) > computedRange) {
//                this.moveTo(aim, time);
//            } else {
//                // Attack
//            }
//        }
    }
}
