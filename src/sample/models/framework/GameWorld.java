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

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void process(double interval) {
        gameObjects.stream()
                .filter(gameObject -> !gameObject.getName().equals("Global"))
                .forEach(gameObject -> processObject(gameObject, interval));

        if (existsByName("Global")) {
            processObject(firstByName("Global"), interval);
        }
    }

    private void processObject(GameObject object, double interval) {
        object.getComponents().forEach(component -> {
            component.process(interval);
        });
    }

    public List<GameObject> findByName(String name) {
        return gameObjects.stream()
                .filter(gameObject -> gameObject.getName().equals(name))
                .collect(Collectors.toList());
    }

    public boolean existsByName(String name) {
        return findByName(name).size() > 0;
    }

    public GameObject firstByName(String name) {
        return findByName(name).get(0);
    }

    public List<GameObject> findByType(Class type) {
        return gameObjects.stream()
                .filter(gameObject -> gameObject.hasComponent(type))
                .collect(Collectors.toList());
    }

    public boolean existsByType(Class type) {
        return findByType(type).size() > 0;
    }

    public GameObject firstByType(Class type) {
        return findByType(type).get(0);
    }

    @Override
    public String toString() {
        return String.format("objects: %s", Arrays.toString(gameObjects.toArray()));
    }
}
