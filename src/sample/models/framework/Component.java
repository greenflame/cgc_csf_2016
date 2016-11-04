package sample.models.framework;

/**
 * Created by Alexander on 03/11/16.
 */
public abstract class Component {

    private GameObject gameObject;

    public Component(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void process(double interval) {

    }
}
