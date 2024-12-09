import java.util.*


object Day7 {
    private const val debug = true
    private val day = this::class.simpleName!!.lowercase()

    @JvmStatic
    fun main(args: Array<String>) {
        println( ULong.MAX_VALUE)
        runProblemRaw("$day/example_1.txt", "$day.Example 1", solution = 3749UL) {
            solveProblem1(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 1", solution = 2664460013123UL) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1.txt", "$day.Example 2", solution = 11387UL) {
            solveProblem2(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 2", solution = 426214131924213UL) {
            solveProblem2(it)
        }
        //426214137279934
    }
//    18446744073709551615
    //     426214137279934
    private fun solveProblem1(input: String): ULong {
        val list = input.split('\n').map { line ->
//        val (result, figures) = input.split('\n').map { line ->
            val pair = line.split(":")
            Pair(pair[0].toULong(), pair[1].trim().split(" ").map { it.toInt() })
        }

//        println( list.joinToString("\n"))
        val listOk = list.filter { hasSolution(it.first, it.second, listOf(OperatorSum(), OperatorProd())) }
        return listOk.map {
            it.first
        }.sum()
    }

    sealed interface Operator {
        fun getSymbol(): Char
        fun operate(a: ULong, b: ULong): ULong
    }

    class OperatorSum() : Operator {
        override fun getSymbol() = '+'
        override fun operate(a: ULong, b: ULong) = a + b
    }

    class OperatorProd() : Operator {
        override fun getSymbol() = 'x'
        override fun operate(a: ULong, b: ULong) = a * b
    }
    class OperatorConcat() : Operator {
        override fun getSymbol() = '|'
        override fun operate(a: ULong, b: ULong) =
            (a.toString() + b.toString()).toULong()
    }

    private fun hasSolution( result: ULong, figures: List<Int>, operators: List<Operator>): Boolean {
        return hasSolution(result, figures.drop(1), figures[0].toULong(), operators) == result
    }

    private fun hasSolution( result: ULong, figures: List<Int>, total: ULong, operators: List<Operator>): ULong {
        if (total != result && figures.isEmpty()) return ULong.MAX_VALUE
        if (total > result) return ULong.MAX_VALUE
        if (total == result && figures.isEmpty()) return total
        if (figures.isEmpty()) return ULong.MAX_VALUE

        operators.forEach { op ->
            val newTotal = hasSolution( result, figures.drop(1), op.operate(total, figures[0].toULong()), operators)
            if (newTotal == result) return newTotal
        }
        return ULong.MAX_VALUE
    }





    private fun solveProblem2(input: String): ULong {
        val list = input.split('\n').map { line ->
//        val (result, figures) = input.split('\n').map { line ->
            val pair = line.split(":")
            Pair(pair[0].toULong(), pair[1].trim().split(" ").map { it.toInt() })
        }

//        println( list.joinToString("\n"))
        val listOk = list.filter { hasSolution(it.first, it.second, listOf( OperatorConcat(), OperatorSum(), OperatorProd())) }
        return listOk.sumOf {
            it.first
        }
    }


}


