package clientlib;

import static clientlib.Action.AFTER_TURN;
import static clientlib.Action.BEFORE_TURN
import static clientlib.TestSolver.STEP.*;

class TestSolver extends Solver {

    enum STEP {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        ACT,

        ACT_AND_UP,
        ACT_AND_DOWN,
        ACT_AND_LEFT,
        ACT_AND_RIGHT,

        UP_AND_ACT,
        DOWN_AND_ACT,
        LEFT_AND_ACT,
        RIGHT_AND_ACT
    }

    private STEP stepToCheck;

    public TestSolver(STEP stepToCheck) {
        this.stepToCheck = stepToCheck;
    }

    @Override
    public String move() {
        switch (stepToCheck) {
            case ACT: return act();
            case UP: return up();
            case DOWN: return down();
            case LEFT: return left();
            case RIGHT: return right();
            case ACT_AND_UP: return up(BEFORE_TURN);
            case ACT_AND_DOWN: return down(BEFORE_TURN);
            case ACT_AND_LEFT: return left(BEFORE_TURN);
            case ACT_AND_RIGHT: return right(BEFORE_TURN);
            case UP_AND_ACT: return up(AFTER_TURN);
            case DOWN_AND_ACT: return down(AFTER_TURN);
            case LEFT_AND_ACT: return left(AFTER_TURN);
            case RIGHT_AND_ACT: return right(AFTER_TURN);
        }
        throw new RuntimeException();
    }
}
