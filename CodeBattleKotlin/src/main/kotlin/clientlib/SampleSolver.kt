package clientlib

import clientlib.Elements.*
import java.util.Scanner
import kotlin.math.sqrt

private val SIZE_OF_BOARD = sqrt(board.length.toDouble()).toInt()

class SampleSolver : Solver() {

    /**
     * This should be your bot's code.
     */
    override fun move(): String {
        val action: String
        val scanner = Scanner(System.`in`)
        action = scanner.nextLine()
        return when (action) {
            "w" -> up()
            "s" -> down()
            "a" -> left()
            "d" -> right()
            "q" -> act()
            else -> ""
        }
    }

    fun getPlayerTank(): List<Point> {
        return getCoordinatesPlayerTank(arrayOf(TANK_UP, TANK_RIGHT, TANK_DOWN, TANK_LEFT))
    }

    fun getOtherPlayersTanks(): List<Point> {
        return getCoordinatesElements(arrayOf(OTHER_TANK_UP, OTHER_TANK_RIGHT, OTHER_TANK_DOWN, OTHER_TANK_LEFT))

    }

    fun getBotsTanks(): List<Point> {
        return getCoordinatesElements(arrayOf(AI_TANK_UP, AI_TANK_RIGHT, AI_TANK_DOWN, AI_TANK_LEFT))
    }

    fun getBullets(): List<Point> {
        return getCoordinatesElements(arrayOf(BULLET, BULLET_UP, BULLET_RIGHT, BULLET_DOWN, BULLET_LEFT))
    }

    fun getWormholes(): List<Point> {
        return getCoordinatesPoint(board, WORM_HOLE)
    }

    fun getConstructions(): List<Point> {
        return getCoordinatesElements(arrayOf(CONSTRUCTION, CONSTRUCTION_DESTROYED_DOWN, CONSTRUCTION_DESTROYED_UP,
                CONSTRUCTION_DESTROYED_LEFT, CONSTRUCTION_DESTROYED_RIGHT, CONSTRUCTION_DESTROYED_DOWN_TWICE,
                CONSTRUCTION_DESTROYED_UP_TWICE, CONSTRUCTION_DESTROYED_LEFT_TWICE, CONSTRUCTION_DESTROYED_RIGHT_TWICE,
                CONSTRUCTION_DESTROYED_LEFT_RIGHT, CONSTRUCTION_DESTROYED_UP_DOWN, CONSTRUCTION_DESTROYED_UP_LEFT,
                CONSTRUCTION_DESTROYED_RIGHT_UP, CONSTRUCTION_DESTROYED_DOWN_LEFT, CONSTRUCTION_DESTROYED_DOWN_RIGHT))
    }

    fun getDestroyedConstructions(): List<Point> {
        return getCoordinatesPoint(board, CONSTRUCTION_DESTROYED)
    }

    fun getBogs(): List<Point> {
        return getCoordinatesPoint(board, BOG)
    }

    fun getSands(): List<Point> {
        return getCoordinatesPoint(board, SAND)
    }

    fun getMoats(): List<Point> {
        return getCoordinatesElements(arrayOf(MOAT_HORIZONTAL, MOAT_VERTICAL))
    }

    fun getHedgehogs(): List<Point> {
        return getCoordinatesPoint(board, HEDGEHOG)
    }

    fun getWalls(): List<Point> {
        return getCoordinatesPoint(board, BATTLE_WALL)
    }

    fun getBarriers(): List<Point> {
        return getCoordinatesElements(arrayOf(BATTLE_WALL, CONSTRUCTION, CONSTRUCTION_DESTROYED_DOWN,
                CONSTRUCTION_DESTROYED_UP, CONSTRUCTION_DESTROYED_LEFT, CONSTRUCTION_DESTROYED_RIGHT,
                CONSTRUCTION_DESTROYED_DOWN_TWICE, CONSTRUCTION_DESTROYED_UP_TWICE, CONSTRUCTION_DESTROYED_LEFT_TWICE,
                CONSTRUCTION_DESTROYED_RIGHT_TWICE, CONSTRUCTION_DESTROYED_LEFT_RIGHT, CONSTRUCTION_DESTROYED_UP_DOWN,
                CONSTRUCTION_DESTROYED_UP_LEFT, CONSTRUCTION_DESTROYED_RIGHT_UP, CONSTRUCTION_DESTROYED_DOWN_LEFT,
                CONSTRUCTION_DESTROYED_DOWN_RIGHT, OTHER_TANK_UP, OTHER_TANK_RIGHT, OTHER_TANK_DOWN, OTHER_TANK_LEFT,
                AI_TANK_UP, AI_TANK_RIGHT, AI_TANK_DOWN, AI_TANK_LEFT, HEDGEHOG))
    }

    fun getAmmoBonuses(): List<Point> {
        return getCoordinatesPoint(board, BONUS_AMMO)
    }

    fun getMedKitBonuses(): List<Point> {
        return getCoordinatesPoint(board, MEDKIT)
    }

    fun isAnyOfAt(x: Int, y: Int, elements: Array<Elements>): Boolean {
        for (element in elements) {
            if (getCoordinatesPoint(board, element).contains(Point(x, y))) {
                return true
            }
        }
        return false
    }

    fun isAt(x: Int, y: Int, element: Elements): Boolean {
        return getCoordinatesPoint(board, element).contains(Point(x, y))
    }

    fun isNear(x: Int, y: Int, element: Elements): Boolean {
        for (point in returnsArrayPointsAround(x, y)) {
            return (getCoordinatesPoint(board, element).contains(point))
        }
        return false
    }

    fun isBarrierAt(x: Int, y: Int): Boolean {
        return getBarriers().contains(Point(x, y))
    }

    fun countNear(x: Int, y: Int, element: Elements): Int {
        var count = 0
        for (point in returnsArrayPointsAround(x, y)) {
            if (getCoordinatesPoint(board, element).contains(point)) {
                count++
            }
        }
        return count
    }

    private fun getCoordinatesPoint(board: String, element: Elements): List<Point> {
        val listElements: MutableList<Point> = mutableListOf()
        var coordinateX: Int
        var coordinateY: Int
        for ((index, char) in board.withIndex()) {
            if (char == element.element) {
                coordinateX = (index + 1) % SIZE_OF_BOARD
                if (coordinateX == 0) {
                    coordinateX = SIZE_OF_BOARD
                }
                coordinateY = index / SIZE_OF_BOARD + 1
                listElements.add(Point(coordinateX, coordinateY))
            }
        }
        return listElements
    }

    private fun getCoordinatesPlayerTank(elements: Array<Elements>): List<Point> {
        for (element in elements) {
            if (board.contains(element.element)) {
                return getCoordinatesPoint(board, element)
            }
        }
        return listOf()
    }

    private fun getCoordinatesElements(elements: Array<Elements>): List<Point> {
        val listElements: MutableList<Point> = mutableListOf()
        for (element in elements) {
            listElements.addAll(getCoordinatesPoint(board, element))
        }
        return listElements
    }

    private fun returnsArrayPointsAround(x: Int, y: Int): Array<Point> {
        return arrayOf(Point(x, y - 1), Point(x + 1, y - 1), Point(x + 1, y), Point(x + 1, y + 1),
                Point(x, y + 1), Point(x - 1, y + 1), Point(x - 1, y), Point(x - 1, y - 1))
    }
}
