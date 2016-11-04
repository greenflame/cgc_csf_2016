package sample.models.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 24/10/16.
 */
public class GameObject {

    private GameWorld gameWorld;

    private String name;

    private List<Component> components;

    public GameObject(GameWorld gameWorld, String name) {
        this.gameWorld = gameWorld;
        this.name = name;
        components = new ArrayList<>();
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Component> getComponents() {
        return components;
    }

    public List<Component> getComponents(Class type) {
        return components.stream()
                .filter(type::isInstance)
                .collect(Collectors.toList());
    }

    public boolean hasComponent(Class type) {
        return getComponents(type).size() > 0;
    }

    public Component firstComponentOfType(Class type) {
        return getComponents(type).get(0);
    }

    @Override
    public String toString() {
        return String.format("name: %s, components: %s", name, Arrays.toString(components.toArray()));
    }
}
