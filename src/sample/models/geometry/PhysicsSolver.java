package sample.models.geometry;

import sample.models.Circle;
import sample.models.Square;
import sample.models.geometry.primitives.Point2d;

import java.util.Vector;

import static sample.models.geometry.Geometry.isPointInSquare;

/**
 * Created by Alexander on 29/10/16.
 */
public class PhysicsSolver {
    public static Point2d collisionPoint(Circle subj, Circle obj) {
        Point2d objRelative = subj.getPosition().sub(obj.getPosition())
                .normalized().mul(subj.getRadius() + obj.getRadius());
        return obj.getPosition().add(objRelative);
    }

    public static Point2d solveCollision(Circle subj, Circle obj) {
        Point2d cp = collisionPoint(subj, obj);
        boolean isCollided = subj.rawDistanceTo(obj) < cp.distanceTo(obj.getPosition());

        if (isCollided) {
            return cp.sub(subj.getPosition());
        } else {
            return new Point2d(0, 0);
        }
    }

    public static Point2d collisionPoint(Circle subj, Square obj) {
        double sx = obj.getPosition().x;
        double sy = obj.getPosition().y;
        double sr = obj.getHalfSide();

        double cx = subj.getPosition().x;
        double cy = subj.getPosition().y;
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

        Point2d corner = obj.getPosition();

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

        Point2d relative = subj.getPosition().sub(corner).normalized().mul(subj.getRadius());
        return corner.add(relative);
    }

    private static boolean isCollided(Circle obj, Square subj) {
        Point2d cPos = obj.getPosition();
        double cRad = obj.getRadius();

        Point2d sPos = subj.getPosition();
        double sSide = subj.getSide();
        double sRad = subj.getHalfSide();

        boolean isCollideCross = isPointInSquare(cPos, sPos.add(new Point2d(0, cRad)), sSide) ||
                isPointInSquare(cPos, sPos.add(new Point2d(cRad, 0)), sSide) ||
                isPointInSquare(cPos, sPos.add(new Point2d(0, -cRad)), sSide) ||
                isPointInSquare(cPos, sPos.add(new Point2d(-cRad, 0)), sSide);

        Vector<Point2d> corners = new Vector<>();
        corners.add(sPos.add(new Point2d(-sRad, -sRad)));
        corners.add(sPos.add(new Point2d(sRad, -sRad)));
        corners.add(sPos.add(new Point2d(-sRad, sRad)));
        corners.add(sPos.add(new Point2d(sRad, sRad)));

        boolean isCollideCorners = corners.stream()
                .filter(corner -> corner.distanceTo(cPos) < cRad).count() > 0;

        return isCollideCross || isCollideCorners;
    }

    public static Point2d solveCollision(Circle obj, Square subj) {
        Point2d cp = collisionPoint(obj, subj);

        if (isCollided(obj, subj)) {
            return cp.sub(obj.getPosition());
        } else {
            return new Point2d(0, 0);
        }
    }
}
