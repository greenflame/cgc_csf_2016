package sample.models.framework.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.models.framework.GameObject;
import sample.models.framework.geometry.Point2d;
import sample.models.framework.geometry.Square;

/**
 * Created by Alexander on 05/11/16.
 */
public class ObstacleRenderer extends Renderer {

    private Color color;

    public ObstacleRenderer(GameObject gameObject, Color color) {
        super(gameObject);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void render(GraphicsContext gc, double scale) {
        Transform transform = (Transform) getGameObject().firstComponentOfType(Transform.class);
        PhysicBody physicBody = (PhysicBody) getGameObject().firstComponentOfType(PhysicBody.class);

        gc.setFill(color);

        Point2d pos = transform.getPosition();
        double r = ((Square) physicBody.getShape()).radius;

        gc.fillRect((pos.x - r) * scale,
                (pos.y - r) * scale,
                r * 2 * scale,
                r * 2 * scale);
    }
}
