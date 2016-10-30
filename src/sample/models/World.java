package sample.models;

import sample.models.geometry.PhysicsSolver;
import sample.models.geometry.Point;
import sample.models.result.Obstacle;
import sample.models.result.Spell;
import sample.models.result.Tower;
import sample.models.result.Troop;
import sample.models.result.towers.DefenceTower;
import sample.models.result.towers.MainTower;

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

        // Obstacles
        this.getObstacles().add(new Obstacle(new Point(9.5, 7.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(10.5, 7.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(11.5, 7.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(12.5, 7.5), 0, this, 1));

        this.getObstacles().add(new Obstacle(new Point(9.5, 14.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(10.5, 14.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(11.5, 14.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(12.5, 14.5), 0, this, 1));

        this.getObstacles().add(new Obstacle(new Point(7.5, 9.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(7.5, 10.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(7.5, 11.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(7.5, 12.5), 0, this, 1));

        this.getObstacles().add(new Obstacle(new Point(14.5, 9.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(14.5, 10.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(14.5, 11.5), 0, this, 1));
        this.getObstacles().add(new Obstacle(new Point(14.5, 12.5), 0, this, 1));

        this.getObstacles().add(new Obstacle(new Point(11, 11), 0, this, 2));
        this.getObstacles().add(new Obstacle(new Point(7.5, 7.5), 0, this, 3));
        this.getObstacles().add(new Obstacle(new Point(7.5, 14.5), 0, this, 3));
        this.getObstacles().add(new Obstacle(new Point(14.5, 7.5), 0, this, 3));
        this.getObstacles().add(new Obstacle(new Point(14.5, 14.5), 0, this, 3));

        // Down player towers
        this.getTowers().add(new MainTower(new Point(3, 19), this, PlayerType.FIRST));    // Main
        this.getTowers().add(new DefenceTower(new Point(3, 11), this, PlayerType.FIRST));    // Up
        this.getTowers().add(new DefenceTower(new Point(11, 19), this, PlayerType.FIRST));    // Right
        this.getTowers().add(new DefenceTower(new Point(19, 19), this, PlayerType.FIRST));    // Right right

        this.getTowers().add(new MainTower(new Point(19, 3), this, PlayerType.SECOND));    // Main
        this.getTowers().add(new DefenceTower(new Point(19, 11), this, PlayerType.SECOND));    // Down
        this.getTowers().add(new DefenceTower(new Point(11, 3), this, PlayerType.SECOND));    // Left
        this.getTowers().add(new DefenceTower(new Point(3, 3), this, PlayerType.SECOND));    // Left left
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
        freeDamaged();

        int iteration = 0;
        int maxIterations = 20;

        while (processCollisions() && iteration < maxIterations) {
            iteration++;
        }

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

    private void freeDamaged() {
        troops = troops.stream().filter(t -> !t.getLifeCrystal().isDamaged()).collect(Collectors.toList());
    }

    private boolean processCollisions() {
        Map<Troop, Point> resolvers = new HashMap<>();

        List<Troop> deployedTroops =
                troops.stream().filter(t -> t.getDeployer().isFinished()).collect(Collectors.toList());

        // For each troop
        deployedTroops.forEach(subj -> {
            // Receive force from other
            Point resolver = deployedTroops.stream()
                    .filter(obj ->  obj != subj)
                    .map(obj -> PhysicsSolver.solveCollision(subj, obj))
                    .reduce(new Point(0, 0), Point::add);

            // From obstacles
            resolver = obstacles.stream()
                    .map(obj -> PhysicsSolver.solveCollision(subj, obj))
                    .reduce(resolver, Point::add);

            // From towers
            resolver = towers.stream()
                    .map(obj -> PhysicsSolver.solveCollision(subj, obj))
                    .reduce(resolver, Point::add);

            if (!resolver.isZero()) {
                resolvers.put(subj, resolver);
            }
        });

        resolvers.forEach((troop, resolver) -> troop.setPosition(troop.getPosition().add(resolver)));
        return resolvers.values().stream().count() != 0;
    }
}
