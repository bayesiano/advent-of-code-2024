import java.math.BigDecimal
import java.util.*


object Day5 {
    private const val debug = true
    private val day = this::class.simpleName!!.lowercase()

    @JvmStatic
    fun main(args: Array<String>) {
        runProblemRaw("$day/example_1.txt", "$day.Example 1", solution = 143) {
            solveProblem1(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 1", solution = 5762) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1.txt", "$day.Example 2", solution = 123) {
            solveProblem2(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 2", solution = 5762) {
            solveProblem2(it)
        }
    }

    private fun solveProblem1(input: String): Int {
        val (rulesRaw, manualsRaw) = input.split("\n\n")

        val rules = rulesRaw.split('\n').map { line ->
            val pair = line.split("|").map { it.toInt() }
            pair[0] * 10000 +pair[1]
//            (pair[0].shl(16)) or pair[1]
        }.sorted()

        val manuals = manualsRaw.split( "\n").map { line ->
            line.split(",").map {
                it.toInt()
            }
        }
        println( manuals.joinToString("\n"))
        val manualsOk = manuals.filter { isOrdered( it, rules) }
        return manualsOk.map { it[it.size/2] }.sum()
    }

    private fun isOrdered( manual: List<Int>, rules: List<Int>): Boolean {
        for (i in 0..manual.size - 2)
            for (j in i + 1..manual.size-1) {
                val candidate = manual[j] * 10000 + manual[i]
//                val candidate = manual[j].shl(16) or manual[i]
                if (rules.binarySearch(candidate) >= 0)
                    return false
            }
        return true
    }



    private fun solveProblem2(input: String): Int {
        val (rulesRaw, manualsRaw) = input.split("\n\n")

        val rules = rulesRaw.split('\n').map { line ->
            val pair = line.split("|").map { it.toInt() }
            pair[0] * 10000 +pair[1]
//            (pair[0].shl(16)) or pair[1]
        }.sorted()

        val manuals = manualsRaw.split( "\n").map { line ->
            line.split(",").map {
                it.toInt()
            }
        }
        println( manuals.joinToString("\n"))
        val manualsKo = manuals.filter { !isOrdered( it, rules) }
        val manualsOk = manualsKo.map{ sort( it, rules) }
        return manualsOk.map { it[it.size/2] }.sum()
    }

    private fun sort(manual: List<Int>, rules: List<Int>): List<Int> {
        val manualMut = manual.toIntArray()
        for (i in 0..<manualMut.size-1)
            for (j in i + 1..<manualMut.size) {
                val candidate = manualMut[j] * 10000 + manualMut[i]
                if (rules.binarySearch(candidate) >= 0) {
                    val tmp = manualMut[j]
                    manualMut[j] = manualMut[i]
                    manualMut[i] = tmp
                }
            }
        return manualMut.toList()
    }
}