package sample.models.framework.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander on 04/11/16.
 */
public class Square extends Shape {

    public final double radius;

    public Square(Point2d position, double radius) {
        super(position);
        this.radius = radius;
    }

    @Override
    public boolean isIntersect(Shape s) {

        if (s instanceof Circle) {
            return isIntersect((Circle) s);
        }

        throw new NotImplementedException();
    }

    private boolean isIntersect(Circle c) {
        boolean isCollideCross = this.moved(new Point2d(0, c.radius)).containsPoint(c.position) ||
                this.moved(new Point2d(c.radius, 0)).containsPoint(c.position) ||
                this.moved(new Point2d(0, -c.radius)).containsPoint(c.position) ||
                this.moved(new Point2d(-c.radius, 0)).containsPoint(c.position);

        List<Point2d> corners = this.corners();

        boolean isCollideCorners = corners.stream()
                .filter(corner -> c.containsPoint(corner)).count() > 0;

        return isCollideCross || isCollideCorners;
    }

    @Override
    public Point2d collisionPointFor(Shape s) {

        if (s instanceof Circle) {
            return collisionPointFor((Circle) s);
        }

        throw new NotImplementedException();
    }

    private Point2d collisionPointFor(Circle c) {
        double sx = position.x;
        double sy = position.y;
        double sr = radius;

        double cx = c.position.x;
        double cy = c.position.y;
        double cr = c.radius;

        // Open rectangles
        boolean isInLeftOpenRectangle = cx < sx && cy > sy - sr && cy < sy + sr;
        boolean isInRightOpenRectangle = cx > sx && cy > sy - sr && cy < sy + sr;
        boolean isInUpOpenRectangle = cy < sy && cx > sx - sr && cx < sx + sr;
        boolean isInDownOpenRectangle = cy > sy && cx > sx - sr && cx < sx + sr;

        // Relative coordinates
        double rx = cx - sx;
        double ry = cy - sy;

        // Semiplanes
        boolean lu = rx < ry;
        boolean rd = rx > ry;
        boolean ld = rx < -ry;
        boolean ru = rx > -ry;

        // Open angles
        boolean isInLeftOpenAngle = lu && ld;
        boolean isInRightOpenAngle = ru && rd;
        boolean isInUpOpenAngle = ld && rd;
        boolean isInDownOpenAngle = lu && ru;

        if (isInLeftOpenAngle && isInLeftOpenRectangle) {
            return new Point2d(sx - sr - cr, cy);
        }

        if (isInRightOpenAngle && isInRightOpenRectangle) {
            return new Point2d(sx + sr + cr, cy);
        }

        if (isInUpOpenAngle && isInUpOpenRectangle) {
            return new Point2d(cx, sy - sr - cr);
        }

        if (isInDownOpenAngle && isInDownOpenRectangle) {
            return new Point2d(cx, sy + sr + cr);
        }

        // Corners

        // Sides
        boolean ls = cx < sx - sr;
        boolean rs = cx > sx + sr;
        boolean us = cy < sy - sr;
        boolean ds = cy > sy + sr;

        Point2d corner = this.position;

        if (ls && us) {
            corner = new Point2d(sx - sr, sy - sr);
        }

        if (rs && us) {
            corner = new Point2d(sx + sr, sy - sr);
        }

        if (ls && ds) {
            corner = new Point2d(sx - sr, sy + sr);
        }

        if (rs && ds) {
            corner = new Point2d(sx + sr, sy + sr);
        }

        Point2d relative = c.position.sub(corner).normalized().mul(c.radius);
        return corner.add(relative);
    }

    @Override
    public Shape moved(Point2d v) {
        return new Square(position.add(v), radius);
    }

    @Override
    public boolean containsPoint(Point2d p) {
        return p.x > position.x - radius &&
                p.x < position.x + radius &&
                p.y > position.y - radius &&
                p.y < position.y + radius;
    }

    public List<Point2d> corners() {
        List<Point2d> result = new LinkedList<>();

        result.add(position.add(new Point2d(radius, radius)));
        result.add(position.add(new Point2d(radius, -radius)));
        result.add(position.add(new Point2d(-radius, radius)));
        result.add(position.add(new Point2d(-radius, -radius)));

        return result;
    }
}
