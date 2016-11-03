package sample.models.geometry;

import sample.models.Circle;
import sample.models.Square;
import sample.models.World;
import sample.models.geometry.primitives.Point2d;
import sample.models.geometry.primitives.Point2i;
import sample.models.geometry.primitives.Size2i;
import sample.models.result.Obstacle;
import sample.models.result.Tower;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Alexander on 29/10/16.
 */
public class RouteSolver {
    public static double distance(Circle from, Circle to) {
        Point2d cp = PhysicsSolver.collisionPoint(from, to);
        return from.getPosition().distanceTo(cp);
    }

    public static double distance(Circle from, Square to) {
        Point2d cp = PhysicsSolver.collisionPoint(from, to);
        return  from.getPosition().distanceTo(cp);
    }

    public static Point2d direction(Circle from, Circle to) {
        Point2d cp = PhysicsSolver.collisionPoint(from, to);
        Point2d dir = cp.sub(from.getPosition()).normalized();

//        if (!Geometry.isLineOfSight(from.getWorld(), from.getPosition(), cp)) {
//            dir = dir.mul(-1);
//        }

        int[][] map = obstacleMap(from.getWorld());

        return dir;
    }

    public static Point2d direction(Circle from, Square to) {
        Point2d cp = PhysicsSolver.collisionPoint(from, to);
        return cp.sub(from.getPosition()).normalized();
    }

    // Waves core

    public static int[][] obstacleMap(World world) {
        int[][] res = new int[(int)world.getHeight()][(int)world.getWidth()];

        for (double i = 0.5; i < world.getHeight(); i += 1) {
            for (double j = 0.5; j < world.getWidth(); j+= 1) {
                Point2d cur = new Point2d(j, i);
                boolean isObstacle = false;

                for (Obstacle obstacle : world.getObstacles()) {
                    if (Geometry.isPointInSquare(cur, obstacle.getPosition(), obstacle.getSide())) {
                        isObstacle = true;
                    }
                }

                for (Tower tower : world.getTowers()) {
                    if (Geometry.isPointInSquare(cur, tower.getPosition(), tower.getSide())) {
                        isObstacle = true;
                    }
                }

                if (isObstacle) {
                    res[(int) Math.floor(i)][(int) Math.floor(j)] = -1;
                } else {
                    res[(int) Math.floor(i)][(int) Math.floor(j)] = Integer.MAX_VALUE;
                }
            }
        }

        return res;
    }

    public static void wave(int map[][], Size2i size, Point2i center) {
        Queue<Point2i> queue = new ArrayDeque<>();
        queue.add(center);

        while (!queue.isEmpty()) {

        }
    }
}
