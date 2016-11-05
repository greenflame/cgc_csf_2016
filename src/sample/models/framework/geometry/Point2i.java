package sample.models.framework.geometry;

/**
 * Created by Alexander on 03/11/16.
 */
public class Point2i {

    public final int x;

    public final int y;

    public Point2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point2i add(Point2i p) {
        return new Point2i(x + p.x, y + p.y);
    }

    public Point2i sub(Point2i p) {
        return new Point2i(x - p.x, y - p.y);
    }

    public Point2d toDouble() {
        return new Point2d(x, y);
    }
}
