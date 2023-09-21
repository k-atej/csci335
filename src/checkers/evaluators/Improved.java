package checkers.evaluators;

import checkers.core.Checkerboard;
import checkers.core.PlayerColor;

import java.util.function.ToIntFunction;

public class Improved implements ToIntFunction<Checkerboard>  {
    @Override
    public int applyAsInt(Checkerboard value) {
        PlayerColor currentP = value.getCurrentPlayer();
        PlayerColor opp;
        if (currentP == PlayerColor.RED) {
            opp = PlayerColor.BLACK;
        }
        else {
            opp = PlayerColor.RED;
        }
        return (value.numPiecesOf(currentP) + value.numKingsOf(currentP)) - (value.numPiecesOf(opp) + value.numKingsOf(opp));
    }
}