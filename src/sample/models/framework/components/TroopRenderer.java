package sample.models.framework.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import sample.models.framework.GameObject;

/**
 * Created by Alexander on 03/11/16.
 */
public class TroopRenderer extends Renderer {

    public TroopRenderer(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void render(GraphicsContext gc, double scale) {
        Transform transform = (Transform) getGameObject().firstComponentOfType(Transform.class);
        Deployer deployer = (Deployer) getGameObject().firstComponentOfType(Deployer.class);
        LifeCrystal lifeCrystal = (LifeCrystal) getGameObject().firstComponentOfType(LifeCrystal.class);
        Badge badge = (Badge) getGameObject().firstComponentOfType(Badge.class);

        gc.setFill(colorForPlayer(badge.getOwner()));

        double k = deployer.isDeployed()
                ? 1f * lifeCrystal.getHealthRest() / lifeCrystal.getTotalHealth()
                : (1 - deployer.getTimeRemain() / deployer.getInterval());

        gc.fillArc((transform.getPosition().x - 0.5) * scale,
                (transform.getPosition().y - 0.5) * scale,
                scale,
                scale,
                0,
                360 * k,
                ArcType.ROUND);
    }

    private Color colorForPlayer(String player) {
        switch (player) {
            case "first":
                return Color.rgb(60, 130, 100);
            case "second":
                return Color.rgb(130, 120, 60);
        }
        return Color.GRAY;
    }
}
