package sample.models.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 24/10/16.
 */
public class GameWorld {

    private List<GameObject> gameObjects;

    public GameWorld() {
        gameObjects = new ArrayList<>();
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void process(double interval) {
        gameObjects.forEach(gameObject -> {
            gameObject.getComponents().forEach(component -> {
                component.process(interval);
            });
        });
    }

    public List<GameObject> findByName(String name) {
        return gameObjects.stream()
                .filter(gameObject -> gameObject.getName().equals(name))
                .collect(Collectors.toList());
    }

    public GameObject firstByName(String name) {
        return findByName(name).get(0);
    }

    public List<GameObject> findByType(Class type) {
        return gameObjects.stream()
                .filter(gameObject -> gameObject.hasComponent(type))
                .collect(Collectors.toList());
    }

    public GameObject firstByType(Class type) {
        return findByType(type).get(0);
    }

    @Override
    public String toString() {
        return String.format("objects: %s", Arrays.toString(gameObjects.toArray()));
    }
}
