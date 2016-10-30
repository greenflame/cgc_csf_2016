package sample.models.result.towers;

import sample.models.PlayerType;
import sample.models.World;
import sample.models.geometry.Point;
import sample.models.result.Tower;

/**
 * Created by Alexander on 30/10/16.
 */
public class DefenceTower extends Tower {
    public DefenceTower(Point position, World world, PlayerType owner) {
        super(position,
                0,  // Rotation
                world,
                2,  // Side
                0.8,    // HitSpeed
                5,  // Range
                1400, // HitPoints
                50, // Damage
                owner);
    }
}
