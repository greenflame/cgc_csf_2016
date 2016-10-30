package sample.models.geometry;

/**
 * Created by Alexander on 25/10/16.
 */
public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point add(Point p) {
        return new Point(x + p.x, y + p.y);
    }

    public Point sub(Point p) {
        return new Point(x - p.x, y - p.y);
    }

    public Point mul(double k) {
        return new Point(x * k, y * k);
    }

    public Point div(double k) {
        return new Point(x / k, y / k);
    }

    public double dot(Point p) {
        return x * p.x + y * p.y;
    }

    public double cross(Point p) {
        return this.x * p.y - this.y * p.x;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Point normalized() {
        return this.div(this.length());
    }

    public double distanceTo(Point p) {
        return this.sub(p).length();
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
        return String.format("(%f, %f)", x, y);
    }
}
