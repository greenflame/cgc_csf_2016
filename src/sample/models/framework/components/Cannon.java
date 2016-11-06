package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;

/**
 * Created by Alexander on 06/11/16.
 */
public class Cannon extends Component {

    private int damage;

    private double range;

    private double reloadTime;

    private double reloadRemain;

    public Cannon(GameObject gameObject, int damage, double range, double reloadTime) {
        super(gameObject);
        this.damage = damage;
        this.range = range;
        this.reloadTime = reloadTime;

        reloadRemain = reloadTime;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(double reloadTime) {
        this.reloadTime = reloadTime;
    }

    public double getReloadRemain() {
        return reloadRemain;
    }

    public boolean isReloaded() {
        return reloadRemain == 0;
    }

    public boolean isInRange(GameObject aim) {
        NavigationSystem navigationSystem = (NavigationSystem) getGameObject().firstOfType(NavigationSystem.class);
        return navigationSystem.distanceTo(aim) < range;
    }

    public void shut(GameObject gameObject) {
        LifeCrystal lifeCrystal = (LifeCrystal) gameObject.firstOfType(LifeCrystal.class);

        if (isInRange(gameObject) && isReloaded()) {
            lifeCrystal.damage(damage);
            reloadRemain = reloadTime;
        }
    }

    @Override
    public void process(double interval) {
        // Reload
        reloadRemain = Math.max(0, reloadRemain - interval);
    }
}
