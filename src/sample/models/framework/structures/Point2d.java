package sample.models.framework.structures;

/**
 * Created by Alexander on 25/10/16.
 */
public class Point2d {

    public final double x;

    public final double y;

    public Point2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2d add(Point2d p) {
        return new Point2d(x + p.x, y + p.y);
    }

    public Point2d sub(Point2d p) {
        return new Point2d(x - p.x, y - p.y);
    }

    public Point2d mul(double k) {
        return new Point2d(x * k, y * k);
    }

    public Point2d div(double k) {
        return new Point2d(x / k, y / k);
    }

    public double dot(Point2d p) {
        return x * p.x + y * p.y;
    }

    public double cross(Point2d p) {
        return this.x * p.y - this.y * p.x;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Point2d normalized() {
        return this.div(this.length());
    }

    public double distanceTo(Point2d p) {
        return this.sub(p).length();
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    public Point2d floor() {
        return new Point2d(Math.floor(x), Math.floor(y));
    }

    public Point2i toInt() {
        return new Point2i((int)x, (int)y);
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof Point2d) {
            return x == ((Point2d) other).x && y == ((Point2d) other).y;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }
}
