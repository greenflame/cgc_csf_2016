package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;

/**
 * Created by Alexander on 03/11/16.
 */
public class Badge extends Component {

    private String owner;

    public Badge(GameObject gameObject, String owner) {
        super(gameObject);
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
