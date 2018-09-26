package clientlib

import spock.lang.Specification
import spock.lang.Unroll

import static clientlib.TestSolver.STEP.*


class SolverSpec extends Specification {

    @Unroll
    def 'Correct step for going #direction and #action is #expectedMove'() {
        when: 'Solver choose direction'
        def testSolver = solver

        then: 'It performs correct action'
        testSolver.move() == expectedMove

        where:
        solver                          | direction         | action        | expectedMove
        new TestSolver(UP)              | 'UP'              | 'NONE'        | 'UP'
        new TestSolver(DOWN)            | 'DOWN'            | 'NONE'        | 'DOWN'
        new TestSolver(LEFT)            | 'LEFT'            | 'NONE'        | 'LEFT'
        new TestSolver(RIGHT)           | 'RIGHT'           | 'NONE'        | 'RIGHT'
        new TestSolver(ACT)             | 'ACT'             | 'NONE'        | 'ACT'
        new TestSolver(ACT_AND_UP)      | 'ACT_AND_UP'      | 'BEFORE_TURN' | 'ACT,UP'
        new TestSolver(ACT_AND_DOWN)    | 'ACT_AND_DOWN'    | 'BEFORE_TURN' | 'ACT,DOWN'
        new TestSolver(ACT_AND_LEFT)    | 'ACT_AND_LEFT'    | 'BEFORE_TURN' | 'ACT,LEFT'
        new TestSolver(ACT_AND_RIGHT)   | 'ACT_AND_RIGHT'   | 'BEFORE_TURN' | 'ACT,RIGHT'
        new TestSolver(UP_AND_ACT)      | 'UP_AND_ACT'      | 'AFTER_TURN'  | 'UP,ACT'
        new TestSolver(DOWN_AND_ACT)    | 'DOWN_AND_ACT'    | 'AFTER_TURN'  | 'DOWN,ACT'
        new TestSolver(LEFT_AND_ACT)    | 'LEFT_AND_ACT'    | 'AFTER_TURN'  | 'LEFT,ACT'
        new TestSolver(RIGHT_AND_ACT)   | 'RIGHT_AND_ACT'   | 'AFTER_TURN'  | 'RIGHT,ACT'
    }
}
