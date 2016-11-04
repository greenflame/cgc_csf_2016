package sample.models.framework.structures;

/**
 * Created by Alexander on 03/11/16.
 */
public class Size2i {

    public final int width;

    public final int height;

    public Size2i(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Size2d toDouble() {
        return new Size2d(width, height);
    }

    public boolean isPointExists(Point2i p) {
        return  p.x >= 0 && p.x < width &&
                p.y >= 0 && p.y < height;
    }
}
