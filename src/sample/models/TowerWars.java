package sample.models;

import javafx.scene.paint.Color;
import sample.models.framework.GameObject;
import sample.models.framework.GameWorld;
import sample.models.framework.components.*;
import sample.models.framework.geometry.Circle;
import sample.models.framework.geometry.Point2d;
import sample.models.framework.geometry.Size2d;
import sample.models.framework.geometry.Square;

/**
 * Created by Alexander on 03/11/16.
 */
public class TowerWars extends GameWorld {

    public TowerWars() {
        GameObject global = new GameObject(this, "Global");

        Clock clock = new Clock(global);
        global.getComponents().add(clock);

        WorldSize worldSize = new WorldSize(global, new Size2d(22, 22));
        global.getComponents().add(worldSize);

        PhysicService physicService = new PhysicService(global, 20);
        global.getComponents().add(physicService);

        DamagedCollector damagedCollector = new DamagedCollector(global);
        global.getComponents().add(damagedCollector);

        getGameObjects().add(global);

        spawnTroop("fTroop", new Point2d(5, 5), 0, 500, "first", 1);
        spawnTroop("sTroop", new Point2d(15, 15), 0, 500, "second", 1);
//        spawnTroop("sTroop", new Point2d(10, 6.5), 0, 500, "second", 1);

//        spawnObstacle("o", new Point2d(3, 2), 1);

//        spawnObstacle("o", new Point2d(12, 12), 1);
//        spawnObstacle("o2", new Point2d(12.1, 12.3), 1);
    }

    public void spawnTroop(String name, Point2d pos, double deployTime, int health, String owner, double speed) {
        GameObject troop = new GameObject(this, name);

        Transform transform = new Transform(troop, pos, 0);
        troop.getComponents().add(transform);

        TroopRenderer troopRenderer = new TroopRenderer(troop, colorForPlayer(owner));
        troop.getComponents().add(troopRenderer);

        Deployer deployer = new Deployer(troop, deployTime);
        troop.getComponents().add(deployer);

        LifeCrystal lifeCrystal = new LifeCrystal(troop, health);
        troop.getComponents().add(lifeCrystal);

        Badge badge = new Badge(troop, owner);
        troop.getComponents().add(badge);

        TroopStrategy troopStrategy = new TroopStrategy(troop);
        troop.getComponents().add(troopStrategy);

        NavigationSystem navigationSystem = new NavigationSystem(troop, speed);
        troop.getComponents().add(navigationSystem);

        PhysicBody physicBody = new PhysicBody(troop, new Circle(new Point2d(0, 0), 0.5), 2);
        troop.getComponents().add(physicBody);

        Cannon cannon = new Cannon(troop, 50, 2, 0.4);
        troop.getComponents().add(cannon);

        getGameObjects().add(troop);
    }

    public void spawnObstacle(String name, Point2d pos, double radius) {
        GameObject obstacle = new GameObject(this, name);

        Transform transform = new Transform(obstacle, pos, 0);
        obstacle.getComponents().add(transform);

        ObstacleRenderer obstacleRenderer = new ObstacleRenderer(obstacle, Color.GRAY);
        obstacle.getComponents().add(obstacleRenderer);

        PhysicBody physicBody = new PhysicBody(obstacle, new Square(new Point2d(0, 0), radius), 3);
        obstacle.getComponents().add(physicBody);

        // Fun
//        TroopStrategy troopStrategy = new TroopStrategy(obstacle);
//        obstacle.getComponents().add(troopStrategy);

        NavigationSystem navigationSystem = new NavigationSystem(obstacle, 1);
        obstacle.getComponents().add(navigationSystem);

        Badge badge = new Badge(obstacle, "test");
        obstacle.getComponents().add(badge);

        Deployer deployer = new Deployer(obstacle, 0.1);
        obstacle.getComponents().add(deployer);

        ObstacleLabel obstacleLabel = new ObstacleLabel(obstacle);
        obstacle.getComponents().add(obstacleLabel);

        getGameObjects().add(obstacle);
    }

    private Color colorForPlayer(String player) {
        switch (player) {
            case "first":
                return Color.rgb(60, 130, 100);
            case "second":
                return Color.rgb(130, 120, 60);
        }
        return Color.GRAY;
    }

    public static void main(String[] args) {
        TowerWars towerWars = new TowerWars();
        towerWars.process(3000);

        double time = ((Clock) towerWars.firstByName("World").firstOfType(Clock.class)).getTime();
        System.out.println(time);
        System.out.println(towerWars);
    }
}
