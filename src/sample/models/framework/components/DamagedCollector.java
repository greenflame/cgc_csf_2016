package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 06/11/16.
 */
public class DamagedCollector extends Component {

    public DamagedCollector(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void process(double interval) {
        List<GameObject> cleared = getGameObject().getGameWorld().getGameObjects().stream()
                .filter(gameObject -> !gameObject.hasComponent(LifeCrystal.class) ||
                        !((LifeCrystal) gameObject.firstOfType(LifeCrystal.class)).isDamaged())
                .collect(Collectors.toList());

        getGameObject().getGameWorld().setGameObjects(cleared);
    }
}
