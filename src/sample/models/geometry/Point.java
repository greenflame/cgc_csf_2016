package sample.models.geometry;

/**
 * Created by Alexander on 25/10/16.
 */
public class Point {
    private double x;
    private double y;

    public Point() {}

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point add(Point other) {
        return new Point(x + other.x, y + other.y);
    }

    public Point sub(Point other) {
        return new Point(x - other.x, y - other.y);
    }

    public Point mul(double k) {
        return new Point(x * k, y * k);
    }

    public Point div(double k) {
        return this.mul(1 / k);
    }

    public double dot(Point other) {
        return x * other.x + y * other.y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Point normilized() {
        return this.div(this.length());
    }

    public double distanceTo(Point other) {
        return this.sub(other).length();
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof Point) {
            return x == ((Point) other).x && y == ((Point) other).y;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("[%f, %f]", x, y);
    }
}
