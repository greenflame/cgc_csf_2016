package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;
import sample.models.framework.structures.Size2d;

/**
 * Created by Alexander on 03/11/16.
 */
public class WorldSize extends Component {

    private Size2d size;

    public WorldSize(GameObject gameObject, Size2d size) {
        super(gameObject);
        this.size = size;
    }

    public Size2d getSize() {
        return size;
    }

    public void setSize(Size2d size) {
        this.size = size;
    }
}
