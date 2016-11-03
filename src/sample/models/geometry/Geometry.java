package sample.models.geometry;

import sample.models.Square;
import sample.models.World;
import sample.models.geometry.primitives.Point2d;
import sample.models.result.Obstacle;
import sample.models.result.Tower;

/**
 * Created by Alexander on 30/10/16.
 */
public class Geometry {
    public static boolean isLineBetweenPoints(Point2d l1, Point2d l2, Point2d p1, Point2d p2) {
        double signedArea1 = l2.sub(l1).cross(p1.sub(l1));
        double signedArea2 = l2.sub(l1).cross(p2.sub(l1));
        return signedArea1 * signedArea2 < 0;
    }

    public static boolean areSegmentsCross(Point2d s1p1, Point2d s1p2, Point2d s2p1, Point2d s2p2) {
        return isLineBetweenPoints(s1p1, s1p2, s2p1, s2p2) &&
                isLineBetweenPoints(s2p1, s2p2, s1p1, s1p2);
    }

    public static double distanceToLine(Point2d l1, Point2d l2, Point2d p) {
        return Math.abs(l2.sub(l1).normalized().cross(p.sub(l1)));
    }

    public static boolean isPointInSquare(Point2d point, Point2d squareCenter, double squareSide) {
        double x = point.x;
        double y = point.y;
        double sx = squareCenter.x;
        double sy = squareCenter.y;
        double sr = squareSide / 2;

        return x > sx - sr &&
                x < sx + sr &&
                y > sy - sr &&
                y < sy + sr;
    }

    public static boolean segmentCrossSquare(Square square, Point2d p1, Point2d p2) {
        double x = square.getPosition().x;
        double y = square.getPosition().y;
        double r = square.getHalfSide();

        Point2d lu = new Point2d(x - r, y - r);
        Point2d ld = new Point2d(x - r, y + r);
        Point2d rd = new Point2d(x + r, y + r);
        Point2d ru = new Point2d(x + r, y - r);

        boolean segmentCrossSquare = areSegmentsCross(p1, p2, lu, ru) ||
                areSegmentsCross(p1, p2, ld, rd) ||
                areSegmentsCross(p1, p2, lu, ld) ||
                areSegmentsCross(p1, p2, ru, rd);

        return !segmentCrossSquare;
    }

    public static boolean isLineOfSight(World world, Point2d p1, Point2d p2) {
        for (Obstacle obstacle : world.getObstacles()) {
            if (segmentCrossSquare(obstacle, p1, p2)) {
                return false;
            }
        }

        for (Tower tower : world.getTowers()) {
            if (segmentCrossSquare(tower, p1, p2)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(areSegmentsCross(
                new Point2d(1, 1),
                new Point2d(2, 2),
                new Point2d(1, 2),
                new Point2d(2, 1)
        ));

        System.out.println(!areSegmentsCross(
                new Point2d(1, 1),
                new Point2d(2, 2),
                new Point2d(2, 2),
                new Point2d(3, 1)
        ));

        System.out.println(!areSegmentsCross(
                new Point2d(1, 1),
                new Point2d(2, 2),
                new Point2d(-1, -1),
                new Point2d(-2, -2)
        ));

        System.out.println(distanceToLine(
                new Point2d(1, 1),
                new Point2d(4, 2),
                new Point2d(4, 1)
        ));
    }
}
