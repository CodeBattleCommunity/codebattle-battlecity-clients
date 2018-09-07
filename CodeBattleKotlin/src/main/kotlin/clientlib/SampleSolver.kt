package clientlib

import java.util.Scanner

class SampleSolver : Solver() {

    /**
     * This should be your bot's code.
     *
     * @return command
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
}