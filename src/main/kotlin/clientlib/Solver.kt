package clientlib

abstract class Solver {

    /**
     * The method of parsing the playing field. Called after server response.
     *
     * @param boardString playing field
     */
    fun parseField(boardString: String): Solver {

        val size: Int
        val field: Array<Array<Elements?>>

        var board = boardString.replace("\n".toRegex(), "")
        size = Math.sqrt(board.length.toDouble()).toInt()
        field = Array(size) { arrayOfNulls<Elements>(size) }
        board = boardString.replace("\n".toRegex(), "")

        val temp = board.toCharArray()
        for (y in 0 until size) {
            val dy = y * size
            for (x in 0 until size) {
                field[x][y] = valueOf(temp[dy + x])
            }
        }
        return this
    }

    private fun valueOf(ch: Char): Elements {
        for (el in Elements.values()) {
            if (el.element == ch) {
                return el
            }
        }
        throw IllegalArgumentException("No such element for $ch")
    }

    protected fun act(): String {
        return "ACT"
    }

    protected fun up(): String {
        return "UP"
    }

    protected fun down(): String {
        return "DOWN"
    }

    protected fun left(): String {
        return "LEFT"
    }

    protected fun right(): String {
        return "RIGHT"
    }

    abstract fun move(): String
}