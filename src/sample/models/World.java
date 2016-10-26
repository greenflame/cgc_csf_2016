package sample.models;

import sample.models.result.Obstacle;
import sample.models.result.Spell;
import sample.models.result.Tower;
import sample.models.result.Troop;

import java.util.ArrayList;
import java.util.List;

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
        troops.forEach(t -> {
            t.process(time);
        });

        towers.forEach(t -> {
            t.process(time);
        });

        spells.forEach(s -> {
            s.process(time);
        });

        this.time += time;
    }
}
