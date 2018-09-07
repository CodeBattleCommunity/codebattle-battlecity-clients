package clientlib

const val URL = "127.0.0.1:8080"
const val USERNAME = "battlecity-super-ai@codenjoy.com"
const val PASSWORD = "123456"

fun main(args: Array<String>) {
    WebSocketRunner(SampleSolver()).run(URL, USERNAME, PASSWORD, SampleSolver())
}

