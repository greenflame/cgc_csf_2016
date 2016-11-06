package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;

import java.util.Optional;

/**
 * Created by Alexander on 04/11/16.
 */
public class TroopStrategy extends Component {

    public TroopStrategy(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void process(double interval) {
        Deployer deployer = (Deployer) getGameObject().firstOfType(Deployer.class);
        Badge badge = (Badge) getGameObject().firstOfType(Badge.class);
        NavigationSystem navigationSystem = (NavigationSystem) getGameObject().firstOfType(NavigationSystem.class);
        Cannon cannon = (Cannon) getGameObject().firstOfType(Cannon.class);

        Optional<GameObject> aim = getGameObject().getGameWorld().getGameObjects().stream()
                .filter(gameObject -> gameObject.hasComponent(TroopStrategy.class))    // Troop
                .filter(gameObject -> !gameObject.hasComponent(Deployer.class) ||
                        ((Deployer) gameObject.firstOfType(Deployer.class)).isDeployed())    // Deployed
                .filter(gameObject -> {
                    Badge otherBadge = (Badge) gameObject.firstOfType(Badge.class);
                    return !badge.getOwner().equals(otherBadge.getOwner());
                })    // Enemy
                .min((o1, o2) -> {
                    double d1 = navigationSystem.distanceTo(o1);
                    double d2 = navigationSystem.distanceTo(o2);

                    if (d1 < d2) {
                        return -1;
                    } else if (d1 > d2) {
                        return 1;
                    } else {
                        return 0;
                    }
                }); // Closest

        if (deployer.isDeployed() && aim.isPresent()) {
            if (cannon.isInRange(aim.get())) {
                cannon.shut(aim.get());
            } else {
                navigationSystem.moveTo(aim.get(), interval);
            }
        }
    }
}
