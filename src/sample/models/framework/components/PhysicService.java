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
                        ((Deployer) subj.firstOfType(Deployer.class)).isDeployed())  // Deployed
                .collect(Collectors.toList());

        Collections.shuffle(physicBodies, new Random(System.nanoTime()));

        for (int i = 0; i < physicBodies.size() - 1; i++) {
            GameObject o1 = physicBodies.get(i);

            PhysicBody pb1 = (PhysicBody) o1.firstOfType(PhysicBody.class);
            Transform tr1 = (Transform) o1.firstOfType(Transform.class);
            Shape trShape1 = pb1.getShape().moved(tr1.getPosition());

            for (int j = i + 1; j < physicBodies.size(); j++) {
                GameObject o2 = physicBodies.get(j);

                PhysicBody pb2 = (PhysicBody) o2.firstOfType(PhysicBody.class);
                Transform tr2 = (Transform) o2.firstOfType(Transform.class);
                Shape trShape2 = pb2.getShape().moved(tr2.getPosition());

                Point2d force12 = trShape1.collisionForceFor(trShape2);

                double k1 = pb2.getMass() / (pb1.getMass() + pb2.getMass());
                double k2 = pb1.getMass() / (pb1.getMass() + pb2.getMass());

                tr1.setPosition(tr1.getPosition().add(force12.mul(-k1)));
                tr2.setPosition(tr2.getPosition().add(force12.mul(k2)));
            }
        }
    }
}
