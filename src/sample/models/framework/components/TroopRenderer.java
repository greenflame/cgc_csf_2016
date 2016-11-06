package sample.models.framework.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import sample.models.framework.GameObject;
import sample.models.framework.geometry.Circle;

/**
 * Created by Alexander on 03/11/16.
 */
public class TroopRenderer extends Renderer {

    private Color color;

    public TroopRenderer(GameObject gameObject, Color color) {
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
        Transform transform = (Transform) getGameObject().firstOfType(Transform.class);
        Deployer deployer = (Deployer) getGameObject().firstOfType(Deployer.class);
        LifeCrystal lifeCrystal = (LifeCrystal) getGameObject().firstOfType(LifeCrystal.class);
        PhysicBody physicBody = (PhysicBody) getGameObject().firstOfType(PhysicBody.class);

        gc.setFill(color);

        double k = deployer.isDeployed()
                ? 1f * lifeCrystal.getHealthRest() / lifeCrystal.getTotalHealth()
                : (1 - deployer.getTimeRemain() / deployer.getInterval());

        double r = ((Circle) physicBody.getShape()).radius;

        gc.fillArc((transform.getPosition().x - r) * scale,
                (transform.getPosition().y - r) * scale,
                2 * r * scale,
                2 * r * scale,
                0,
                360 * k,
                ArcType.ROUND);
    }
}
