package sample.models.result.towers;

import sample.models.PlayerType;
import sample.models.World;
import sample.models.geometry.primitives.Point2d;
import sample.models.result.Tower;

/**
 * Created by Alexander on 29/10/16.
 */
public class MainTower extends Tower {
    public MainTower(Point2d position, World world, PlayerType owner) {
        super(position,
                0,  // Rotation
                world,
                4,  // Side
                1,    // HitSpeed
                6,  // Range
                2400, // HitPoints
                50, // Damage
                owner);
    }
}
