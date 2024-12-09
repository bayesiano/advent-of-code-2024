import java.util.*


object Day8 {
    private const val debug = true
    private val day = this::class.simpleName!!.lowercase()

    @JvmStatic
    fun main(args: Array<String>) {
        println(ULong.MAX_VALUE)
        runProblemRaw("$day/example_1a.txt", "$day.Example 1a", solution = 2) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1b.txt", "$day.Example 1b", solution = 4) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1c.txt", "$day.Example 1c", solution = 4) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1d.txt", "$day.Example 1d", solution = 14) {
            solveProblem1(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 1", solution = 269) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_2.txt", "$day.Example 2", solution = 9) {
            solveProblem2(it)
        }
        runProblemRaw("$day/example_2b.txt", "$day.Example 2b", solution = 34) {
            solveProblem2(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 2", solution = 949) {
            solveProblem2(it)
        }
    }

    private fun solveProblem1(input: String): Int {
        val lines = input.split('\n')
        val width = lines[0].length
        val height = lines.size

        val map = HashMap<Char, MutableList<Pair<Int, Int>>>()
        lines.forEachIndexed { j, line ->
            line.forEachIndexed { i, c -> if (c != '.' && c != '#') map.getOrPut(c) { mutableListOf() }.add(Pair(i, j)) }
        }

        val interferences = mutableSetOf<Pair<Int, Int>>()
        map.entries.forEach { e ->
            for (i0 in 0..<e.value.size - 1)
                for (i1 in i0 + 1..<e.value.size) {
                    val incX = e.value[i1].first - e.value[i0].first
                    val incY = e.value[i1].second - e.value[i0].second
                    val p1 = Pair(e.value[i0].first - incX, e.value[i0].second - incY)
                    val p2 = Pair(e.value[i1].first + incX, e.value[i1].second + incY)
                    if (p1.first in 0..<width && p1.second in 0..<height) interferences += p1
                    if (p2.first in 0..<width && p2.second in 0..<height) interferences += p2
                }
        }

        return interferences.count()
    }

    private fun solveProblem2(input: String): Int {
        val lines = input.split('\n')
        val width = lines[0].length
        val height = lines.size

        val map = HashMap<Char, MutableList<Pair<Int, Int>>>()
        lines.forEachIndexed { j, line ->
            line.forEachIndexed { i, c -> if (c != '.' && c != '#') map.getOrPut(c) { mutableListOf() }.add(Pair(i, j)) }
        }

        val interferences = mutableSetOf<Pair<Int, Int>>()
        map.entries.forEach { e ->
            for (i0 in 0..<e.value.size - 1)
                for (i1 in i0 + 1..<e.value.size) {
                    val incX = e.value[i1].first - e.value[i0].first
                    val incY = e.value[i1].second - e.value[i0].second
                    interferences += getAllPoints( e.value[i0], -incX, -incY, width, height)
                    interferences += getAllPoints( e.value[i1], incX, incY, width, height)
                    interferences +=e.value[i0]
                    interferences += e.value[i1]
                }
        }

        return interferences.count()
    }

    private fun getAllPoints( p: Pair<Int, Int>, incX: Int, incY: Int, width: Int, height: Int) = buildList {
        var newP = Pair( p.first + incX, p.second + incY)
        while( newP.first in 0..<width && newP.second in 0..<height) {
            add( newP)
            newP = Pair( newP.first + incX, newP.second + incY)
        }
    }

}
