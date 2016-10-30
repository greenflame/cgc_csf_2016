package sample.models.geometry;

import sample.models.Circle;
import sample.models.Square;

import java.util.Vector;

/**
 * Created by Alexander on 29/10/16.
 */
public class PhysicsSolver {
    public static Point collisionPoint(Circle subj, Circle obj) {
        Point objRelative = subj.getPosition().sub(obj.getPosition())
                .normalized().mul(subj.getRadius() + obj.getRadius());
        return obj.getPosition().add(objRelative);
    }

    public static Point solveCollision(Circle subj, Circle obj) {
        Point cp = collisionPoint(subj, obj);
        boolean isCollided = subj.rawDistanceTo(obj) < cp.distanceTo(obj.getPosition());

        if (isCollided) {
            return cp.sub(subj.getPosition());
        } else {
            return new Point(0, 0);
        }
    }

    public static Point collisionPoint(Circle subj, Square obj) {
        double sx = obj.getPosition().getX();
        double sy = obj.getPosition().getY();
        double sr = obj.getHalfSide();

        double cx = subj.getPosition().getX();
        double cy = subj.getPosition().getY();
        double cr = subj.getRadius();

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
            return new Point(sx - sr - cr, cy);
        }

        if (isInRightOpenAngle && isInRightOpenRectangle) {
            return new Point(sx + sr + cr, cy);
        }

        if (isInUpOpenAngle && isInUpOpenRectangle) {
            return new Point(cx, sy - sr - cr);
        }

        if (isInDownOpenAngle && isInDownOpenRectangle) {
            return new Point(cx, sy + sr + cr);
        }

        // Corners

        // Sides
        boolean ls = cx < sx - sr;
        boolean rs = cx > sx + sr;
        boolean us = cy < sy - sr;
        boolean ds = cy > sy + sr;

        Point corner = obj.getPosition();

        if (ls && us) {
            corner = new Point(sx - sr, sy - sr);
        }

        if (rs && us) {
            corner = new Point(sx + sr, sy - sr);
        }

        if (ls && ds) {
            corner = new Point(sx - sr, sy + sr);
        }

        if (rs && ds) {
            corner = new Point(sx + sr, sy + sr);
        }

        Point relative = subj.getPosition().sub(corner).normalized().mul(subj.getRadius());
        return corner.add(relative);
    }

    private static boolean isPointInSquare(Point point, Point squareCenter, double squareSide) {
        double x = point.getX();
        double y = point.getY();
        double sx = squareCenter.getX();
        double sy = squareCenter.getY();
        double sr = squareSide / 2;

        return x > sx - sr &&
                x < sx + sr &&
                y > sy - sr &&
                y < sy + sr;
    }

    private static boolean isCollided(Circle obj, Square subj) {
        Point cPos = obj.getPosition();
        double cRad = obj.getRadius();

        Point sPos = subj.getPosition();
        double sSide = subj.getSide();
        double sRad = subj.getHalfSide();

        boolean isCollideCross = isPointInSquare(cPos, sPos.add(new Point(0, cRad)), sSide) ||
                isPointInSquare(cPos, sPos.add(new Point(cRad, 0)), sSide) ||
                isPointInSquare(cPos, sPos.add(new Point(0, -cRad)), sSide) ||
                isPointInSquare(cPos, sPos.add(new Point(-cRad, 0)), sSide);

        Vector<Point> corners = new Vector<>();
        corners.add(sPos.add(new Point(-sRad, -sRad)));
        corners.add(sPos.add(new Point(sRad, -sRad)));
        corners.add(sPos.add(new Point(-sRad, sRad)));
        corners.add(sPos.add(new Point(sRad, sRad)));

        boolean isCollideCorners = corners.stream()
                .filter(corner -> corner.distanceTo(cPos) < cRad).count() > 0;

        return isCollideCross || isCollideCorners;
    }

    public static Point solveCollision(Circle obj, Square subj) {
        Point cp = collisionPoint(obj, subj);

        if (isCollided(obj, subj)) {
            return cp.sub(obj.getPosition());
        } else {
            return new Point(0, 0);
        }
    }
}
