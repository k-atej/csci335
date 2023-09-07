package robosim.ai;

import robosim.core.*;
import robosim.reinforcement.QTable;

public class ForwardAvoidQ implements Controller {

    QTable q = new QTable(3, 4, 0, 10, 1, 1.0);

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
        else if (sim.findClosestProblem() < 10) {
            state = 1;
        }
        else {
            state = 2;
        }
        return state;
    }

    public int[][] initReward() {
        int[][] r = new int[3][4];
        r[0][0] = 0;
        r[0][1] = 10;
        r[0][2] = 10;
        r[0][3] = 10;
        r[1][0] = 10;
        r[1][1] = 15;
        r[1][2] = 500;
        r[1][3] = 500;
        r[2][0] = 1500;
        r[2][1] = 100;
        r[2][2] = 100;
        r[2][3] = 100;
        return r;
    }

    public int calcReward(int state, int action) {
        int[][] r = initReward();
        return r[state][action];
    }

}
