package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;
import sample.models.framework.geometry.Point2d;

import java.util.Optional;

/**
 * Created by Alexander on 04/11/16.
 */
public class TroopNavigator extends Component {

    public TroopNavigator(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void process(double interval) {
        Deployer deployer = (Deployer) getGameObject().firstComponentOfType(Deployer.class);
        Transform transform = (Transform) getGameObject().firstComponentOfType(Transform.class);
        Mover mover = (Mover) getGameObject().firstComponentOfType(Mover.class);
        Badge badge = (Badge) getGameObject().firstComponentOfType(Badge.class);

        Optional<GameObject> closestEnemy = getGameObject().getGameWorld().getGameObjects().stream()
                .filter(gameObject -> gameObject.hasComponent(TroopNavigator.class))    // Troop
                .filter(gameObject -> ((Deployer) gameObject.firstComponentOfType(Deployer.class)).isDeployed())    // Deployed
                .filter(gameObject -> {
                    Badge otherBadge = (Badge) gameObject.firstComponentOfType(Badge.class);
                    return !badge.getOwner().equals(otherBadge.getOwner());
                })    // Enemy
                .min((o1, o2) -> {
                    Transform t1 = (Transform) o1.firstComponentOfType(Transform.class);
                    Transform t2 = (Transform) o2.firstComponentOfType(Transform.class);

                    double d1 = transform.getPosition().distanceTo(t1.getPosition());
                    double d2 = transform.getPosition().distanceTo(t2.getPosition());

                    if (d1 < d2) {
                        return -1;
                    } else if (d1 > d2) {
                        return 1;
                    } else {
                        return 0;
                    }
                }); // Closest

        if (deployer.isDeployed() && closestEnemy.isPresent()) {
            Point2d destination = ((Transform) closestEnemy.get().firstComponentOfType(Transform.class)).getPosition();
            mover.setDestination(destination);
        } else {
            mover.setDestination(transform.getPosition());
        }
    }
}
