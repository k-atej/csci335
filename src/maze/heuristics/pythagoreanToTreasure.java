package maze.heuristics;

import core.Pos;
import maze.core.MazeExplorer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.ToIntFunction;

import static java.lang.Math.sqrt;

// monotonic: will never overestimate the distance to the treasure

public class pythagoreanToTreasure implements ToIntFunction<MazeExplorer> {

    @Override
    public int applyAsInt(MazeExplorer value) {
        Set<Pos> treasures = value.getAllTreasureFromMaze();
        Set<Pos> found = value.getAllTreasureFound();

        Set<Pos> unfound = new HashSet<>();

        for (Pos treasure : treasures) {
            if (!found.contains(treasure)) {
                unfound.add(treasure);
            }
        }


        if (unfound.isEmpty()) {
            return pythag(value, value.getM().getEnd());
        }

        int closest = pythag(value, value.getM().getEnd());

        for (Pos treasure : unfound) {
            int dist = pythag(value, treasure);
            closest = Math.min(closest, dist);
        }

        return closest;
    }

    private int pythag(MazeExplorer value, Pos target) {
        int x_diff = target.getX() - value.getLocation().getX();
        int y_diff = target.getY() - value.getLocation().getY();
        return (int) sqrt(x_diff*x_diff + y_diff*y_diff);
    }
}
