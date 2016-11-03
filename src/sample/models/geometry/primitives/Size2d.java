package sample.models.geometry.primitives;

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

    public Size2d floor() {
        return new Size2d(Math.floor(width), Math.floor(height));
    }

    public Point2i toInt() {
        return new Point2i((int)width, (int)height);
    }
}
