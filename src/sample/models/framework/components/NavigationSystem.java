package sample.models.framework.components;

import sample.models.framework.Component;
import sample.models.framework.GameObject;
import sample.models.framework.geometry.*;

/**
 * Created by Alexander on 04/11/16.
 */
public class NavigationSystem extends Component {

    private double speed;

    public NavigationSystem(GameObject gameObject, double speed) {
        super(gameObject);
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double distanceTo(GameObject destination) {
        Transform myTransform = (Transform) getGameObject().firstOfType(Transform.class);
        Transform destTransform = (Transform) destination.firstOfType(Transform.class);

        return myTransform.getPosition().distanceTo(destTransform.getPosition());
    }

    public void moveTo(GameObject destination, double interval) {
        WorldSize worldSize = (WorldSize) getGameObject().getGameWorld().firstByName("Global").firstOfType(WorldSize.class);
        Transform transform = (Transform) getGameObject().firstOfType(Transform.class);

        Transform destTransform = (Transform) destination.firstOfType(Transform.class);
        Size2i mapSize = worldSize.getSize().floor().toInt();

        int[][] map = generateObstacleMap(mapSize);
        Routes.doWave(map, mapSize, destTransform.getPosition().floor().toInt());
        Point2d direction = Routes.direction(map, mapSize, transform.getPosition().floor().toInt()).toDouble().normalized();

        transform.setPosition(transform.getPosition().add(direction.mul(speed * interval)));
    }

    private int[][] generateObstacleMap(Size2i size) {
        int[][] map = new int[size.width][size.height];

        for (int i = 0; i < size.width; i++) {
            for (int j = 0; j < size.height; j++) {
                Point2i curI = new Point2i(i, j);
                Point2d curD = curI.toDouble().add(new Point2d(0.5, 0.5));

                boolean isObstacle = getGameObject().getGameWorld().getGameObjects().stream()
                        .filter(gameObject -> gameObject.hasComponent(ObstacleLabel.class))  // ObstacleLabel
                        .filter(gameObject -> {
                            PhysicBody physicBody = (PhysicBody) gameObject.firstOfType(PhysicBody.class);
                            Transform transform = (Transform) gameObject.firstOfType(Transform.class);

                            Shape trShape = physicBody.getShape().moved(transform.getPosition());

                            return trShape.containsPoint(curD);
                        }) // Contains point
                        .count() > 0;

                if (isObstacle) {
                    map[curI.x][curI.y] = -1;
                } else {
                    map[curI.x][curI.y] = Integer.MAX_VALUE - 1;
                }
            }
        }

        return map;
    }
}
