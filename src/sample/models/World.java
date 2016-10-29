package sample.models;

import sample.models.geometry.Point;
import sample.models.result.Obstacle;
import sample.models.result.Spell;
import sample.models.result.Tower;
import sample.models.result.Troop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 24/10/16.
 */
public class World implements Dynamic {
    private List<Troop> troops;
    private List<Tower> towers;
    private List<Obstacle> obstacles;
    private List<Spell> spells;

    private double width;
    private double height;
    private double time;

    public World() {
        troops = new ArrayList<>();
        towers = new ArrayList<>();
        obstacles = new ArrayList<>();
        spells = new ArrayList<>();

        width = 22;
        height = 22;
    }

    public List<Troop> getTroops() {
        return troops;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getTime() {
        return time;
    }

    @Override
    public void process(double time) {
        processUnits(time);
        clearDamaged();
        while (processCollisions()) {}
        this.time += time;
    }

    private void processUnits(double time) {
        troops.forEach(t -> {
            t.process(time);
        });

        towers.forEach(t -> {
            t.process(time);
        });

        spells.forEach(s -> {
            s.process(time);
        });
    }

    private void clearDamaged() {
        troops = troops.stream().filter(t -> !t.getLifeCrystal().isDamaged()).collect(Collectors.toList());
    }

    private boolean processCollisions() {
        Map<Troop, Point> resolvers = new HashMap<>();

        List<Troop> deployedTroops =
                troops.stream().filter(t -> t.getDeployer().isFinished()).collect(Collectors.toList());

        deployedTroops.forEach(subj -> {
            Point resolver = deployedTroops.stream()
                    .filter(obj ->  obj != subj)
                    .map(subj::collisionResolverFor)
                    .reduce(new Point(), Point::add);

            resolver = obstacles.stream()
                    .map(subj::collisionResolverFor)
                    .reduce(resolver, Point::add);

            if (!resolver.isZero()) {
                resolvers.put(subj, resolver);
            }
        });

        resolvers.forEach((troop, resolver)-> troop.setPosition(troop.getPosition().add(resolver)));
        return resolvers.values().stream().count() != 0;
    }
}
