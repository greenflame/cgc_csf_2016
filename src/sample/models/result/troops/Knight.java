package sample.models.result.troops;

import sample.models.PlayerType;
import sample.models.Point;
import sample.models.World;
import sample.models.result.Troop;

/**
 * Created by Alexander on 26/10/16.
 */
public class Knight extends Troop {
    public Knight(Point position, World world, PlayerType owner) {
        super(position,
                0.5,  // Radius,
                world,
                1.3,  // Speed,
                3,  // Cost,
                1.1f,  // HitSpeed,
                1,  // DeployTime,
                0,  // Range,
                1,  // Count,
                660,  // HitPoints,
                75,  // Damage,
                owner);
    }
}
