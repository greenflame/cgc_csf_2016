package sample.models;

import sample.models.framework.GameObject;
import sample.models.framework.GameWorld;
import sample.models.framework.components.*;
import sample.models.framework.structures.Point2d;
import sample.models.framework.structures.Size2d;

/**
 * Created by Alexander on 03/11/16.
 */
public class TowerWars extends GameWorld {

    public TowerWars() {
        GameObject common = new GameObject(this, "World");

        Clock clock = new Clock(common);
        common.getComponents().add(clock);

        WorldSize worldSize = new WorldSize(common, new Size2d(22, 22));
        common.getComponents().add(worldSize);

        getGameObjects().add(common);

        spawnTroop("fTroop", new Point2d(2, 3), 3, 500, "first");
        spawnTroop("sTroop", new Point2d(10, 7), 3, 500, "second");
    }

    public void spawnTroop(String name, Point2d pos, double deployTime, int health, String owner) {
        GameObject troop = new GameObject(this, name);

        Transform transform = new Transform(troop, pos, 0);
        troop.getComponents().add(transform);

        TroopRenderer troopRenderer = new TroopRenderer(troop);
        troop.getComponents().add(troopRenderer);

        Deployer deployer = new Deployer(troop, deployTime);
        troop.getComponents().add(deployer);

        LifeCrystal lifeCrystal = new LifeCrystal(troop, health);
        troop.getComponents().add(lifeCrystal);

        Badge badge = new Badge(troop, owner);
        troop.getComponents().add(badge);

        getGameObjects().add(troop);
    }

    public static void main(String[] args) {
        TowerWars towerWars = new TowerWars();
        towerWars.process(3000);

        double time = ((Clock) towerWars.firstByName("World").firstComponentOfType(Clock.class)).getTime();
        System.out.println(time);
        System.out.println(towerWars);
    }
}
