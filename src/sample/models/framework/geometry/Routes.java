package sample.models.framework.geometry;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 06/11/16.
 */
public class Routes {

    private static List<Point2i> getNeighbours() {
        List<Point2i> neighbours = new LinkedList<>();

        neighbours.add(new Point2i(1, 0));
        neighbours.add(new Point2i(0, 1));
        neighbours.add(new Point2i(-1, 0));
        neighbours.add(new Point2i(0, -1));

        return neighbours;
    }

    public static void doWave(int[][] map, Size2i size, Point2i center) {
        Queue<Point2i> queue = new ArrayDeque<>();
        map[center.x][center.y] = 0;
        queue.add(center);

        List<Point2i> neighbours = getNeighbours();

        while (!queue.isEmpty()) {
            Point2i cp = queue.element();

            List<Point2i> available = neighbours.stream()
                    .map(cp::add)   // Neighbours
                    .filter(size::containsPoint)    // In borders
                    .filter(neighbour -> map[neighbour.x][neighbour.y] != -1)   // Not obstacles
                    .collect(Collectors.toList());

            Optional<Integer> minNeighborVal = available.stream()
                    .map(p -> map[p.x][p.y])
                    .min(Integer::compareTo);

            if (minNeighborVal.isPresent()) {
                map[cp.x][cp.y] = Math.min(map[cp.x][cp.y], minNeighborVal.get() + 1);

                available.stream()
                        .filter(p -> map[p.x][p.y] == Integer.MAX_VALUE - 1)    // Not visited
                        .filter(p -> !queue.contains(p))   // Not in queue
                        .forEach(queue::add);

                // ...
                queue.remove();
            }
        }
    }

    public static Point2i direction(int[][] map, Size2i size, Point2i point) {
        List<Point2i> neighbours = getNeighbours();

        Optional<Point2i> min = neighbours.stream()
                .map(point::add)   // Neighbours
                .filter(size::containsPoint)    // In borders
                .filter(neighbour -> map[neighbour.x][neighbour.y] != -1)   // Not obstacles
                .min((o1, o2) -> new Integer(map[o1.x][o1.y]).compareTo(map[o2.x][o2.y]));

        if (min.isPresent()) {
            return min.get().sub(point);
        } else {
            return new Point2i(0, 0);
        }
    }
}
