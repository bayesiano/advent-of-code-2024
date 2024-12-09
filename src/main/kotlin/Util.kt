import java.io.File
import kotlin.system.exitProcess
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

class Dummy()

@Suppress("UNUSED")
fun <T> runProblemSeq(filename: String, problem: String, solution: T, function: (Sequence<String>) -> T): T {
    val stream = Dummy::class.java.classLoader.getResourceAsStream(filename)?.bufferedReader()
    if( stream == null) {
        println( "ERROR! $filename not existst!!!")
        exitProcess( -1)
    }
//    val file = File(Dummy::class.java.classLoader.getResource(filename)!!.file)
    var res: T
    val us = measureNanoTime {
        res = function( stream.lineSequence())
    }
    if( res != solution) System.err.println( "SOLUTION FOR $problem IS WRONG !!!!!  Response=$res, expected=$solution, time=${us/1000/1000.0} ms")
    else println("It's the right solution for $problem: response = $res,  time=${us/1000/1000.0} ms")
    return res
}

fun <T> runProblemRaw(filename: String, problem: String, solution: T, function: (String) -> T): T {
    val stream = Dummy::class.java.classLoader.getResourceAsStream(filename)?.bufferedReader()
    if( stream == null) {
        println( "ERROR! $filename not existst!!!")
        exitProcess( -1)
    }
//    val file = File(Dummy::class.java.classLoader.getResource(filename)!!.file)
    var res: T
    val us = measureNanoTime {
        res = function( stream.readText())
    }
    if( res != solution) System.err.println( "SOLUTION FOR $problem IS WRONG !!!!!  Response=$res, expected=$solution, time=${us/1000/1000.0} ms")
    else println("It's the right solution for $problem: response = $res,  time=${us/1000/1000.0} ms")
    return res
}

fun <T> runProblemRawTest(filename: String, problem: String, solution: T, function: (String) -> T): T {
    val stream = Dummy::class.java.classLoader.getResourceAsStream(filename)?.bufferedReader()
    if( stream == null) {
        println( "ERROR! $filename not existst!!!")
        exitProcess( -1)
    }
//    val file = File(Dummy::class.java.classLoader.getResource(filename)!!.file)
    val input = stream.readText()
    var res = function(input)
    val us = measureNanoTime {
        (1..10).forEach { _ ->
            res = function(input)
        }
    }
    if( res != solution) System.err.println( "SOLUTION FOR $problem IS WRONG !!!!!  Response=$res, expected=$solution, time=${us/1000/1000.0} ms")
    else println("It's the right solution for $problem: response = $res,  time=${us/1000/1000.0} ms")
    return res
}


@Suppress("UNUSED")
fun <T> runProblem(filename: String, problem: String, solution: T, function: (List<String>) -> T): T {
    val stream = Dummy::class.java.classLoader.getResource(filename)
    if( stream == null) {
        println( "ERROR! $filename not existst!!!")
        exitProcess( -1)
    }
//    val file = File(Dummy::class.java.classLoader.getResource(filename)!!.file)
    val lines = stream.readText().split("\n")
    var res: T
    val ms = measureTimeMillis {
        res = function( lines)
    }
    if( res != solution) System.err.println( "SOLUTION FOR $problem IS WRONG !!!!!  Response=$res, expected=$solution, time=$ms ms")
    else println("It's the right solution for $problem: response = $res,  time=$ms ms")
    return res
}

@Suppress("UNUSED")
fun <T> runProblem(filename: String, problem: String, solution: T, splitter: String = "\n", function: (List<String>) -> T): T {
    val chunks = File(Dummy::class.java.classLoader.getResource(filename)!!.file).readText().split(splitter)
    var res: T
    val ms = measureNanoTime {
        res = function( chunks)
    }
    if( res != solution) System.err.println( "SOLUTION FOR $problem IS WRONG !!!!!  Response=$res, expected=$solution, time=$ms ms")
    else println("It's the right solution for $problem: response = $res,  time=$ms ms")
    return res
}

@Suppress("UNUSED")
fun String.getInts( end: Char = '\n'): Pair<MutableList<Int>, String> {
    var n = 0
    var thereIsANumber = false
    val res = mutableListOf<Int>()
    this.forEachIndexed { i, c ->
        if( c.isDigit()) {
            n = 10 * n + c.digitToInt()
            thereIsANumber = true
        }
        else if( c == end ) return Pair( res, substring(i+1))
        else if (thereIsANumber) {
            res += n
            n = 0
        }
    }
    if (thereIsANumber) res += n
    return Pair( res, "")
}
//
//@Suppress("UNUSED")
//fun String.getULongs( end: Char = '|'): Pair<MutableList<ULong>, String> {
//    var n = 0L
//    var thereIsANumber = false
//    val res = mutableListOf<ULong>()
//    this.forEachIndexed { i, c ->
//        if( c.isDigit()) {
//            n = 10 * n + c.digitToInt()
//            thereIsANumber = true
//        }
//        else if( c == end) return Pair( res, substring(i+1))
//        else if (thereIsANumber) {
//            res += n
//            n = 0
//        }
//    }
//    if (thereIsANumber) res += n
//    return Pair( res, "")
//}