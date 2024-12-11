object Day11 {
    private val day = this::class.simpleName!!.lowercase()

    @JvmStatic
    fun main(args: Array<String>) {
        println(ULong.MAX_VALUE)
        runProblemRaw("$day/example_1.txt", "$day.Example 1a", solution = 7) {
            solveProblem1(it, 1)
        }
        runProblemRaw("$day/example_1b.txt", "$day.Example 1b", solution = 55312) {
            solveProblem1(it, 25)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 1", solution = 216996) {
            solveProblem1(it, 25)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 2", solution = 257335372288947) {
            solveProblem1(it, 75)
        }
    }


    private fun solveProblem1(input: String, numBlinks: Int): Long {
        val initialStones = input.split( " ").map(String::toLong)
        return initialStones.sumOf { blink(it, numBlinks) }
    }

    private val cache = mutableMapOf<Pair<Long,Int>,Long>()

    private fun blink( stone: Long, numBlinks: Int): Long {
        if( numBlinks == 0) return 1

        val cacheKey = stone to numBlinks
        cache[cacheKey]?.let { return it }

        val totalStones = when {
                stone == 0L -> blink(1, numBlinks - 1)
                stone.toString().length % 2 == 0 -> {
                    val strStone = stone.toString()
                    val stone1 = strStone.substring(0, strStone.length / 2).toLong()
                    val stone2 = strStone.substring(strStone.length / 2).toLong()
                    blink(stone1, numBlinks - 1) + blink(stone2, numBlinks - 1)
                }
                else -> blink(stone * 2024, numBlinks - 1)
            }
        cache[cacheKey] = totalStones
        return totalStones
    }


}
