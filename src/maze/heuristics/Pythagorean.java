package maze.heuristics;

import core.Pos;
import maze.core.MazeExplorer;

import java.util.function.ToIntFunction;

import static java.lang.Math.sqrt;

public class Pythagorean implements ToIntFunction<MazeExplorer> {
    @Override
    public int applyAsInt(MazeExplorer value) {
        Pos target = value.getM().getEnd();
        int x_diff = target.getX() - value.getLocation().getX();
        int y_diff = target.getY() - value.getLocation().getY();
        return (int) sqrt(x_diff*x_diff + y_diff*y_diff);
    }
}
