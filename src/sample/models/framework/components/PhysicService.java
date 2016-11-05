package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;
import sample.models.framework.geometry.Point2d;
import sample.models.framework.geometry.Shape;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 04/11/16.
 */
public class PhysicService extends Component {

    private int iterations;

    public PhysicService(GameObject gameObject, int iterations) {
        super(gameObject);
        this.iterations = iterations;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    @Override
    public void process(double interval) {
        for (int i = 0; i < iterations; i++) {
            iterate();
        }
    }

    public void iterate() {

        List<GameObject> physicBodies = getGameObject().getGameWorld().getGameObjects().stream()
                .filter(subj -> subj.hasComponent(PhysicBody.class))    // Physic body
                .filter(subj -> !subj.hasComponent(Deployer.class) ||
                        ((Deployer) subj.firstComponentOfType(Deployer.class)).isDeployed())  // Deployed
                .collect(Collectors.toList());

        Collections.shuffle(physicBodies, new Random(System.nanoTime()));

        List<GameObject> movable =  physicBodies.stream()
                .filter(subj -> ((PhysicBody) subj.firstComponentOfType(PhysicBody.class)).isMovable()) // Movable
                .collect(Collectors.toList());

        List<GameObject> obstacle =  physicBodies.stream()
                .filter(subj -> !((PhysicBody) subj.firstComponentOfType(PhysicBody.class)).isMovable()) // Static
                .collect(Collectors.toList());

        movable.forEach(subj -> {
            PhysicBody pbSubj = (PhysicBody) subj.firstComponentOfType(PhysicBody.class);
            Transform trSubj = (Transform) subj.firstComponentOfType(Transform.class);
            Shape trShapeSubj = pbSubj.getShape().moved(trSubj.getPosition());

            movable.stream().filter(obj -> obj != subj).forEach(obj -> {
                PhysicBody pbObj = (PhysicBody) obj.firstComponentOfType(PhysicBody.class);
                Transform trObj = (Transform) obj.firstComponentOfType(Transform.class);
                Shape trShapeObj = pbObj.getShape().moved(trObj.getPosition());

                Point2d forceObjSubj = trShapeObj.collisionForceFor(trShapeSubj);

                trSubj.setPosition(trSubj.getPosition().add(forceObjSubj.mul(0.5)));
                trObj.setPosition(trObj.getPosition().add(forceObjSubj.mul(-0.5)));
            });

            obstacle.forEach(obj -> {
                PhysicBody pbObj = (PhysicBody) obj.firstComponentOfType(PhysicBody.class);
                Transform trObj = (Transform) obj.firstComponentOfType(Transform.class);
                Shape trShapeObj = pbObj.getShape().moved(trObj.getPosition());

                Point2d forceObjSubj = trShapeObj.collisionForceFor(trShapeSubj);

                trSubj.setPosition(trSubj.getPosition().add(forceObjSubj));
            });
        });
    }
}
