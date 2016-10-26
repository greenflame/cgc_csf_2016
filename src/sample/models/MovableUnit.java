package sample.models;

import javafx.scene.shape.MoveTo;

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
        setPosition(obj.getPosition().sub(getPosition()).normilized().mul(speed * time));
    }
}
