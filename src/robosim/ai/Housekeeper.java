package robosim.ai;

import core.Duple;
import robosim.core.*;
import robosim.reinforcement.QTable;

import java.util.Optional;

public class Housekeeper implements Controller {
    //collect as much dirt as possible
    //avoid hitting objects
    int state = 5;
    int act = 4;

    double discount = 0.7;
    int rateConstant = 1;
    int startState = 0;
    int targetVisits = 5;
    QTable q = new QTable(state, act, startState, targetVisits, rateConstant, discount);

    @Override
    public void control(Simulator sim) {
        int state = detectState(sim);
        int act = nextAction(state);

        if (act == 0) {
            Action.FORWARD.applyTo(sim);
        }
        if (act == 1) {
            Action.BACKWARD.applyTo(sim);
        }
        if (act == 2) {
            Action.LEFT.applyTo(sim);
        }
        if (act == 3) {
            Action.RIGHT.applyTo(sim);
        }
    }

    public int nextAction(int state) {
        int reward = calcReward(q.getLastState(), q.getLastAction());
        return q.senseActLearn(state, reward);
    }

    public int detectState(Simulator sim) {
        int state;
        if (sim.wasHit()) {
            state = 0;
        }
        else if (detectDirt(sim, sim.bot) == 0) {
            state = 2;
        }
        else if (sim.findClosestProblem() < 10) {
            state = 1;
        }
        else if (detectDirt(sim, sim.bot) == 1) {
            state = 3;
        }
        else {
            state = 4;
        }
        return state;
    }

    public int detectDirt(Simulator sim, Robot bot) {
        for (Duple<SimObject, Polar>  obj : sim.allVisibleObjects()) {
            if (obj.getFirst().isVacuumable()) {
                if (obj.getFirst().distanceTo(bot) == 0){
                    //hitting dirt
                    return 0;
                }
                else if (obj.getFirst().distanceTo(bot) < 50 && obj.getSecond().getTheta() < Robot.ANGULAR_VELOCITY) {
                    //close to dirt
                    return 1;
                }
            }
        }
        //no dirt
        return 2;
    }

    //states

    //0 hitting obstacle
    //1 close to obstacle
    //2 hitting dirt
    //3 close to dirt
    //4 far
    public int[][] initReward() {
        int[][] r = new int[state][act];
        r[0][0] = 0;
        r[0][1] = 10;
        r[0][2] = 10;
        r[0][3] = 10;
        r[1][0] = 10;
        r[1][1] = 15;
        r[1][2] = 500;
        r[1][3] = 500;
        r[2][0] = 2500;
        r[2][1] = 2500;
        r[2][2] = 2500;
        r[2][3] = 2500;
        r[3][0] = 1500;
        r[3][1] = 1000;
        r[3][2] = 1000;
        r[3][3] = 1000;
        r[4][0] = 1500;
        r[4][1] = 100;
        r[4][2] = 500;
        r[4][3] = 500;
        return r;
    }

    public int calcReward(int state, int action) {
        int[][] r = initReward();
        return r[state][action];
    }

}
