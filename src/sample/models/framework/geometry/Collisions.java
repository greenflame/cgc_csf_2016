package sample.models.framework.geometry;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by Alexander on 05/11/16.
 */
public class Collisions {
    public static boolean isIntersect(Circle c1, Circle c2) {
        return c1.position.distanceTo(c2.position) < c1.radius + c2.radius;
    }

    public static boolean isIntersect(Circle c, Square s) {
        boolean isCollideCross = s.moved(new Point2d(0, c.radius)).containsPoint(c.position) ||
                s.moved(new Point2d(c.radius, 0)).containsPoint(c.position) ||
                s.moved(new Point2d(0, -c.radius)).containsPoint(c.position) ||
                s.moved(new Point2d(-c.radius, 0)).containsPoint(c.position);

        List<Point2d> corners = s.corners();

        boolean isCollideCorners = corners.stream()
                .filter(c::containsPoint).count() > 0;

        return isCollideCross || isCollideCorners;
    }

    public static boolean isIntersect(Square s, Circle c) {
        return isIntersect(c, s);
    }


    public static boolean isIntersect(Square s1, Square s2) {
        return new Square(s1.position, s1.radius + s2.radius).containsPoint(s2.position);
    }

    public static Point2d collisionPointFor(Circle subj, Circle obj) {
        Point2d relative = obj.position.sub(subj.position).normalized().mul(subj.radius + obj.radius);
        return subj.position.add(relative);
    }

    public static Point2d collisionPointFor(Square subj, Circle obj) {
        double sx = subj.position.x;
        double sy = subj.position.y;
        double sr = subj.radius;

        double cx = obj.position.x;
        double cy = obj.position.y;
        double cr = obj.radius;

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

        Point2d corner = subj.position;

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

        Point2d relative = obj.position.sub(corner).normalized().mul(obj.radius);
        return corner.add(relative);
    }

    public static Point2d collisionPointFor(Circle subj, Square obj) {
        Point2d cp = collisionPointFor(obj, subj);
        return obj.position.sub(cp).add(subj.position);
    }


    public static Point2d collisionPointFor(Square subj, Square obj) {
        double sx = subj.position.x;
        double sy = subj.position.y;
        double sr = subj.radius;

        double cx = obj.position.x;
        double cy = obj.position.y;
        double cr = obj.radius;

        double rr = sr + cr;

        // Open rectangles
        boolean isInLeftOpenRectangle = cx < sx && cy > sy - rr && cy < sy + rr;
        boolean isInRightOpenRectangle = cx > sx && cy > sy - rr && cy < sy + rr;
        boolean isInUpOpenRectangle = cy < sy && cx > sx - rr && cx < sx + rr;
        boolean isInDownOpenRectangle = cy > sy && cx > sx - rr && cx < sx + rr;

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

        if (ls && us) {
            return new Point2d(sx - rr, sy - rr);
        }

        if (rs && us) {
            return new Point2d(sx + rr, sy - rr);
        }

        if (ls && ds) {
            return new Point2d(sx - rr, sy + rr);
        }

        if (rs && ds) {
            return new Point2d(sx + rr, sy + rr);
        }

        return obj.position;
    }

    private static Point2d right(Square s, Point2d p) {
        Point2d cp = new Point2d(s.position.x + s.radius, p.y);
        return cp.sub(p);
    }

    private static Point2d left(Square s, Point2d p) {
        Point2d cp = new Point2d(s.position.x - s.radius, p.y);
        return cp.sub(p);
    }

    private static Point2d up(Square s, Point2d p) {
        Point2d cp = new Point2d(p.x, s.position.y - s.radius);
        return cp.sub(p);
    }

    private static Point2d down(Square s, Point2d p) {
        Point2d cp = new Point2d(p.x, s.position.y + s.radius);
        return cp.sub(p);
    }
}
