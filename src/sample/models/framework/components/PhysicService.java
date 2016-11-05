package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;
import sample.models.framework.geometry.Point2d;
import sample.models.framework.geometry.Shape;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Alexander on 04/11/16.
 */
public class PhysicService extends Component {

    public PhysicService(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void process(double interval) {
        int iteration = 0;
        int maxIterations = 20;
        while (iterate() && iteration < maxIterations) {
            iteration++;
        }
    }

    public boolean iterate() {
        Map<GameObject, Point2d> forces = new HashMap<>();

        getGameObject().getGameWorld().getGameObjects().stream()
                .filter(subj -> subj.hasComponent(PhysicBody.class))    // Physic body
                .filter(subj -> ((PhysicBody) subj.firstComponentOfType(PhysicBody.class)).isMovable()) // Movable
                .filter(subj -> !subj.hasComponent(Deployer.class) || ((Deployer) subj.firstComponentOfType(Deployer.class)).isDeployed())  // Deployed
                .forEach(gameObject -> {
                    Point2d force = computeForceFor(gameObject);

                    if (!force.isZero()) {
                        forces.put(gameObject, force);
                    }
                });

        forces.forEach((gameObject, force) -> {
            Transform transform = (Transform) gameObject.firstComponentOfType(Transform.class);
            transform.setPosition(transform.getPosition().add(force));
        });

        return forces.values().stream().count() != 0;
    }

    public Point2d computeForceFor(GameObject subj) {
        PhysicBody subjPb = (PhysicBody) subj.firstComponentOfType(PhysicBody.class);
        Transform subjTf = (Transform) subj.firstComponentOfType(Transform.class);

        Shape subjTransformedShape = subjPb.getShape().moved(subjTf.getPosition());

        Point2d resultForce = subj.getGameWorld().getGameObjects().stream()
                .filter(obj -> obj.hasComponent(PhysicBody.class))  // Physic body
                .filter(obj -> !obj.hasComponent(Deployer.class) || ((Deployer) obj.firstComponentOfType(Deployer.class)).isDeployed())  // Deployed
                .filter(obj -> obj != subj) // Not this
                .map(obj -> {
                    PhysicBody objPb = (PhysicBody) obj.firstComponentOfType(PhysicBody.class);
                    Transform objTf = (Transform) obj.firstComponentOfType(Transform.class);

                    Shape objTransformedShape = objPb.getShape().moved(objTf.getPosition());

                    return objTransformedShape.collisionForceFor(subjTransformedShape);
                })
                .reduce(new Point2d(0, 0), Point2d::add);

        return resultForce;
    }
}
