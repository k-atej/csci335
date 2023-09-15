package maze.heuristics;

import core.Pos;
import maze.core.MazeExplorer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.ToIntFunction;

// another idea: nav to middle of the maze, nav to end of the maze

public class ManToTreasure implements ToIntFunction<MazeExplorer> {

    // not finding the nearest treasure effectively
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
            return value.getLocation().getManhattanDist(value.getM().getEnd());
        }

        int closest = value.getLocation().getManhattanDist(value.getM().getEnd());

        for (Pos treasure : unfound) {
            int dist = value.getLocation().getManhattanDist(treasure);
            closest = Math.min(closest, dist);
        }

        return closest;
    }
}
