package sample.models;

/**
 * Created by Alexander on 24/10/16.
 */
public abstract class Unit {
    private Point position;
    private double radius;
    private World world;

    public Unit(Point position, double radius, World world) {
        this.position = position;
        this.radius = radius;
        this.world = world;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getRadius() {
        return radius;
    }

    public World getWorld() {
        return world;
    }

    public double distanceTo(Unit obj) {
        return position.distanceTo(obj.position);
    }
}
