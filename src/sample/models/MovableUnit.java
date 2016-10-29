package sample.models;

import sample.models.geometry.Point;

/**
 * Created by Alexander on 26/10/16.
 */
public class MovableUnit extends Unit {
    private double speed;

    public MovableUnit(Point position, double radius, World world, double speed) {
        super(position, radius, world);
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void moveTo(Unit obj, double time) {
        Point dir = obj.getPosition().sub(getPosition()).normilized();
        double length = Math.min(speed * time, this.distanceTo(obj));
        Point newPos = getPosition().add(dir.mul(length));
        setPosition(newPos);
    }
}
