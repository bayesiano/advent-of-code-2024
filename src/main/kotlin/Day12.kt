import kotlin.math.abs

object Day12 {
    private val day = this::class.simpleName!!.lowercase()
    private val debug = false

    @JvmStatic
    fun main(args: Array<String>) {
        println(ULong.MAX_VALUE)
        runProblemRaw("$day/example_1.txt", "$day.Example 1a", solution = 140) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1b.txt", "$day.Example 1b", solution = 772) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1c.txt", "$day.Example 1c", solution = 1930) {
            solveProblem1(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 1", solution = 1522850) {
            solveProblem1(it)
        }

        println("**********************************************************************************")
        println("**********************************************************************************")
        println("**********************************************************************************")

        runProblemRaw("$day/example_1.txt", "$day.Example 2a", solution = 80) {
            solveProblem2(it)
        }
        runProblemRaw("$day/example_1b.txt", "$day.Example 2b", solution = 436) {
            solveProblem2(it)
        }
        runProblemRaw("$day/example_2c.txt", "$day.Example 2c", solution = 236) {
            solveProblem2(it)
        }
        runProblemRaw("$day/example_2d.txt", "$day.Example 2d", solution = 368) {
            solveProblem2(it)
        }
        runProblemRaw("$day/example_1c.txt", "$day.Example 2d", solution = 1206) {
            solveProblem2(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 2", solution = 953738) {
            solveProblem2(it)
        }
    }


    private fun solveProblem1(input: String): Long {
        val map = input.split("\n").map(String::toCharArray)
        val garden = calculatePlots(map)
        if (debug) {
            println()
            garden.regions.forEach { println("${it.plot}: ${it.perimeter()} x ${it.area()}") }
        }
        val sum = garden.regions.sumOf { it.perimeter() * it.area() }
        return sum
    }

    private fun solveProblem2(input: String): Long {
        val map = input.split("\n").map(String::toCharArray)
        val garden = calculatePlots(map)
        if (debug) {
            println()
            garden.regions.forEach { println("${it.plot}: ${it.sides()} x ${it.area()}") }
        }
        val sum = garden.regions.sumOf { it.sides() * it.area() }
        return sum
    }


    data class Point(val i: Int, val j: Int) {
        fun distance(other: Point): Int = abs(other.i - i) + abs(other.j - j)
    }

    data class Line(var p0: Point, var p1: Point) {
        private val isHorizontal: Boolean
            get() = p0.i == p1.i

        fun isContinuation(other: Line): Boolean {
            return if (p0 == other.p1 || p1 == other.p0) {
                // touching, same orientation?
                if (isHorizontal && other.isHorizontal) true
                else if (!isHorizontal && !other.isHorizontal) true
                else false
            } else false
        }

        fun join(l2: Line) {
            if (p0 == l2.p1) p0 = l2.p0
            else if (p1 == l2.p0) p1 = l2.p1
            else error("XXX")
        }
    }

    data class Region(val plot: Char, val locations: MutableList<Point>) {
        fun area(): Long = locations.size.toLong()

        fun perimeter(): Long =
            locations.sumOf { currentLocation -> 4L - locations.count { it.distance(currentLocation) == 1 } }

        fun sides(): Long {
            val sides = buildList {
                locations.map { currentLocation ->
                    add(Line(currentLocation, Point(currentLocation.i + 1, currentLocation.j)))
                    add(
                        Line(
                            Point(currentLocation.i + 1, currentLocation.j),
                            Point(currentLocation.i + 1, currentLocation.j + 1)
                        )
                    )
                    add(
                        Line(
                            Point(currentLocation.i + 1, currentLocation.j + 1),
                            Point(currentLocation.i, currentLocation.j + 1)
                        )
                    )
                    add(Line(Point(currentLocation.i, currentLocation.j + 1), currentLocation))
                }
            }.toMutableList()

            var changed = true
            while (changed) {
                changed = false
                var i = 0
                while (i < sides.size - 1) {
                    val current = sides[i]
                    var j = i + 1
                    while (j < sides.size) {
                        if (sides[j].isContinuation(current)) {
                            current.join(sides[j])
                            sides.removeAt(j)
                            changed = true
                        } else j += 1
                    }
//                    sides = sides.filter { it.p0 != it.p1 }.toMutableList()
                    i += 1
                }
            }
            return sides.filterNot { it.p0 == it.p1 }.size.toLong()
        }
    }

    class Garden() {
        val regions = mutableListOf<Region>()

        fun addPlot(plot: Char, location: Point) {
            val similar = regions.filter { it.plot == plot }
            val region = similar
                .find { it.locations.any { location.distance(it) == 1 } }
            if (region == null) regions.add((Region(plot, mutableListOf(location))))
            else {
                region.locations.add(location)
                similar.forEach { r ->
                    if (r != region && r.locations.any { otherLoc ->
                            region.locations.any { it.distance(otherLoc) == 1 }
                        }) {
                        region.locations.addAll(r.locations)
                        regions.remove(r)
                    }
                }
            }
        }
    }

    private fun calculatePlots(map: List<CharArray>): Garden {
        val garden = Garden()

        for (j in map.indices) {
            for (i in map[j].indices) {
                val plotLocation = Point(i, j)
                val plotType = map[j][i]
                garden.addPlot(plotType, plotLocation)
            }
        }
        return garden
    }
}
