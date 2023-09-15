package maze.heuristics;

import core.Pos;
import maze.core.MazeExplorer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.ToIntFunction;

import static java.lang.Math.sqrt;

public class pythagoreanAdd implements ToIntFunction<MazeExplorer> {

    // adds the euclidean distance to the end + the euclidean distance to the nearest treasure
    // will occasionally overestimate -- make a triangle between the explorer, goal, and treasure for example
    @Override
    public int applyAsInt(MazeExplorer value) {
        System.out.println(goalDist(value) + getNearestTreasure(value));
        return goalDist(value) + getNearestTreasure(value);
    }

    private int goalDist(MazeExplorer value) {
        // return pythagorean distance to
        Pos target = value.getM().getEnd();
        return pythagorean(value, target);
    }

    private int getNearestTreasure(MazeExplorer value) {
        Set<Pos> treasures = value.getAllTreasureFromMaze();
        Set<Pos> found = value.getAllTreasureFound();

        Set<Pos> unfound = new HashSet<>();

        for (Pos treasure : treasures) {
            if (!found.contains(treasure)) {
                unfound.add(treasure);
            }
        }


        if (unfound.isEmpty()) {
            return pythagorean(value, value.getM().getEnd());
        }

        int closest = value.getLocation().getManhattanDist(value.getM().getEnd());

        for (Pos treasure : unfound) {
            int dist = pythagorean(value, treasure);
            closest = Math.min(closest, dist);
        }

        return closest;
    }

    private int pythagorean(MazeExplorer value, Pos target) {
        int x_diff = target.getX() - value.getLocation().getX();
        int y_diff = target.getY() - value.getLocation().getY();
        return (int) sqrt(x_diff * x_diff + y_diff * y_diff);
    }
}
