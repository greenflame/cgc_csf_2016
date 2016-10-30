package sample.models.geometry;

import sample.models.Circle;
import sample.models.Square;

/**
 * Created by Alexander on 29/10/16.
 */
public class RouteSolver {
    public static double distance(Circle from, Circle to) {
        Point cp = PhysicsSolver.collisionPoint(from, to);
        return from.getPosition().distanceTo(cp);
    }

    public static double distance(Circle from, Square to) {
        Point cp = PhysicsSolver.collisionPoint(from, to);
        return  from.getPosition().distanceTo(cp);
    }

    public static Point direction(Circle from, Circle to) {
        Point cp = PhysicsSolver.collisionPoint(from, to);
        return cp.sub(from.getPosition()).normalized();
    }

    public static Point direction(Circle from, Square to) {
        Point cp = PhysicsSolver.collisionPoint(from, to);
        return cp.sub(from.getPosition()).normalized();
    }
}
