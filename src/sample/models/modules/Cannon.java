package sample.models.modules;

import sample.models.Dynamic;

/**
 * Created by Alexander on 26/10/16.
 */
public class Cannon implements Dynamic {
    private int damage;
    private double range;
    private StopWatch recharger;

    public Cannon(int damage, double range, double reloadTime) {
        this.damage = damage;
        this.range = range;
        this.recharger = new StopWatch(reloadTime);
    }

    public int getDamage() {
        return damage;
    }

    public double getRange() {
        return range;
    }

    public StopWatch getRecharger() {
        return recharger;
    }

    public void attack(LifeCrystal lifeCrystal) {
        if (recharger.isFinished()) {
            lifeCrystal.damage(damage);
            recharger.reload();
        }
    }

    @Override
    public void process(double time) {
        recharger.process(time);
    }
}
