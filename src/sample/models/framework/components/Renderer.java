package sample.models.framework.components;

import javafx.scene.canvas.GraphicsContext;
import sample.models.framework.Component;
import sample.models.framework.GameObject;

/**
 * Created by Alexander on 03/11/16.
 */
public abstract class Renderer extends Component {

    public Renderer(GameObject gameObject) {
        super(gameObject);
    }

    abstract public void render(GraphicsContext graphicsContext, double scale);
}
