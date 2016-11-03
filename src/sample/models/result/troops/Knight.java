package sample.models.result.troops;

import sample.models.PlayerType;
import sample.models.geometry.primitives.Point2d;
import sample.models.World;
import sample.models.result.Troop;

/**
 * Created by Alexander on 26/10/16.
 */
public class Knight extends Troop {
    public Knight(Point2d position, World world, PlayerType owner) {
        super(position,
                0,  // Rotation
                world,
                0.5,  // Radius,
                3,  // Cost,
                1.1f,  // HitSpeed,
                1.3f,  // Speed,
                1,  // DeployTime,
                0,  // Range,
                1,  // Count,
                660,  // HitPoints,
                75,  // Damage,
                owner);
    }
}
