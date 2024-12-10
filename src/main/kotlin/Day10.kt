object Day10 {
    private const val debug = true
    private val day = this::class.simpleName!!.lowercase()

    @JvmStatic
    fun main(args: Array<String>) {
        println(ULong.MAX_VALUE)
        runProblemRaw("$day/example_1.txt", "$day.Example 1a", solution = 2) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1b.txt", "$day.Example 1b", solution = 4) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1c.txt", "$day.Example 1c", solution = 3) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1d.txt", "$day.Example 1d", solution = 36) {
            solveProblem1(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 1", solution = 709) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_2.txt", "$day.Example 2", solution = 3) {
            solveProblem2(it)
        }
        runProblemRaw("$day/example_2b.txt", "$day.Example 2b", solution = 227) {
            solveProblem2(it)
        }
        runProblemRaw("$day/example_2c.txt", "$day.Example 2c", solution = 81) {
            solveProblem2(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 2", solution = 1326) {
            solveProblem2(it)
        }
    }

    data class Point( val i: Int, val j: Int) {
        fun neighbours( width: Int, height: Int): Set<Point> {
            return setOf( Point( i-1,j), Point( i+1,j), Point( i,j-1), Point( i,j+1))
                .filter { it.i in 0..<width && it.j in 0..<height }.toSet()
        }
    }

    private fun solveProblem1(input: String): Int {
        val map = input.split( "\n").map { line ->
            line.map { if( it == '.') Int.MIN_VALUE else it.digitToInt() }
        }
        val trailHeads = map.flatMapIndexed { j, heights ->
            heights.mapIndexedNotNull { i, height ->
                if( height == 0) Point(i,j)
                else null
            }
        }
        val scores = trailHeads.map { findTrailHeadScores( it, map).count() }

        return scores.sum()
    }

    private fun findTrailHeadScores( coord: Point, map: List<List<Int>>): Set<Point> {
        val currentHeight = map[coord.j][coord.i]
        if( currentHeight == 9)
            return setOf(coord)
        val candidates = coord.neighbours( map[0].size, map.size)
            .filter{ map[it.j][it.i] == currentHeight + 1}
        if( candidates.isEmpty())
            return emptySet()
        return candidates.flatMap{ findTrailHeadScores(it, map) }.toSet()
    }

    private fun solveProblem2(input: String): Int {
        val map = input.split( "\n").map { line ->
            line.map { if( it == '.') Int.MIN_VALUE else it.digitToInt() }
        }
        val trailHeads = map.flatMapIndexed { j, heights ->
            heights.mapIndexedNotNull { i, height ->
                if( height == 0) Point(i,j)
                else null
            }
        }
        val ratings = trailHeads.map { findTrailHeadRating( it, map)}

        return ratings.sum()
    }

    private fun findTrailHeadRating( coord: Point, map: List<List<Int>>): Int {
        val currentHeight = map[coord.j][coord.i]
        if( currentHeight == 9)
            return 1
        val candidates = coord.neighbours( map[0].size, map.size)
            .filter{ map[it.j][it.i] == currentHeight + 1}
        if( candidates.isEmpty())
            return 0
        return candidates.map{ findTrailHeadRating(it, map) }.sum()
    }



}
