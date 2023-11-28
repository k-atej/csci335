package maze.heuristics;

import core.Pos;
import maze.core.MazeExplorer;

import java.util.function.ToIntFunction;

import static java.lang.Math.sqrt;

public class average implements ToIntFunction<MazeExplorer> {
    // finds the manhattan and euclidean distances to the end, averages them
    // is this monotonic? I think it is, since man and pyth are individually monotonic
    @Override
    public int applyAsInt(MazeExplorer value) {
        int pyth = pythag(value);
        int man = value.getLocation().getManhattanDist(value.getM().getEnd());
        return (man + pyth) / 2;
    }

    private int pythag(MazeExplorer value) {
        Pos target = value.getM().getEnd();
        int x_diff = target.getX() - value.getLocation().getX();
        int y_diff = target.getY() - value.getLocation().getY();
        return (int) sqrt(x_diff*x_diff + y_diff*y_diff);
    }
}