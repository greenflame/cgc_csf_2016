package sample.models.geometry;

/**
 * Created by Alexander on 30/10/16.
 */
public class Geometry {
    public static boolean isLineBetweenPoints(Point l1, Point l2, Point p1, Point p2) {
        double signedArea1 = l2.sub(l1).cross(p1.sub(l1));
        double signedArea2 = l2.sub(l1).cross(p2.sub(l1));
        return signedArea1 * signedArea2 < 0;
    }

    public static boolean areSegmentsCross(Point s1p1, Point s1p2, Point s2p1, Point s2p2) {
        return isLineBetweenPoints(s1p1, s1p2, s2p1, s2p2) &&
                isLineBetweenPoints(s2p1, s2p2, s1p1, s1p2);
    }

    public static double distanceToLine(Point l1, Point l2, Point p) {
        return Math.abs(l2.sub(l1).normalized().cross(p.sub(l1)));
    }

    public static void main(String[] args) {
        System.out.println(areSegmentsCross(
                new Point(1, 1),
                new Point(2, 2),
                new Point(1, 2),
                new Point(2, 1)
        ));

        System.out.println(!areSegmentsCross(
                new Point(1, 1),
                new Point(2, 2),
                new Point(2, 2),
                new Point(3, 1)
        ));

        System.out.println(!areSegmentsCross(
                new Point(1, 1),
                new Point(2, 2),
                new Point(-1, -1),
                new Point(-2, -2)
        ));

        System.out.println(distanceToLine(
                new Point(1, 1),
                new Point(4, 2),
                new Point(4, 1)
        ));
    }
}
