package sample.models.framework.geometry;

/**
 * Created by Alexander on 03/11/16.
 */
public class Size2d {

    public final double width;

    public final double height;

    public Size2d(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public Size2d mul(double k) {
        return new Size2d(width * k, height * k);
    }

    public Size2d floor() {
        return new Size2d(Math.floor(width), Math.floor(height));
    }

    public Size2i toInt() {
        return new Size2i((int)width, (int)height);
    }

    public Point2d toPoint() {
        return new Point2d(width, height);
    }
}
