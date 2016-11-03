package sample.models;

import sample.models.geometry.primitives.Point2d;

/**
 * Created by Alexander on 24/10/16.
 */
public abstract class Body {
    private Point2d position;
    private double rotation;
    private World world;

    public Body(Point2d position, double rotation, World world) {
        this.position = position;
        this.rotation = rotation;
        this.world = world;
    }

    public Point2d getPosition() {
        return position;
    }

    public void setPosition(Point2d position) {
        this.position = position;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public World getWorld() {
        return world;
    }

    public double rawDistanceTo(Body obj) {
        return position.distanceTo(obj.position);
    }
}
