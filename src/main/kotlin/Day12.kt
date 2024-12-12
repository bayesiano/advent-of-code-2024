import kotlin.math.abs

object Day12 {
    private val day = this::class.simpleName!!.lowercase()

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

        println( "**********************************************************************************")
        println( "**********************************************************************************")
        println( "**********************************************************************************")

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
        println()
        garden.regions.forEach { println("${it.plot}: ${it.perimeter()} x ${it.area()}") }
        val sum = garden.regions.sumOf { it.perimeter() * it.area() }
        return sum
    }

    private fun solveProblem2(input: String): Long {
        val map = input.split("\n").map(String::toCharArray)
        val garden = calculatePlots(map)
        println()
        garden.regions.forEach { println("${it.plot}: ${it.sides()} x ${it.area()}") }
        val sum = garden.regions.sumOf { it.sides() * it.area() }
        return sum
    }


    data class Point(val i: Int, val j: Int) {
        fun distance(other: Point): Int = abs(other.i - i) + abs(other.j - j)
//        fun inSameLine( other: Point) = other.i == i ||  other.j == j
    }
    data class Line(var p0: Point, var p1: Point) {
        val isHorizontal: Boolean
            get() = p0.i == p1.i

        fun continued( l2: Line): Boolean {
            return if( p0 == l2.p1 || p1 == l2.p0) {
                // touching
                if( isHorizontal && l2.isHorizontal) true
                else if( !isHorizontal && !l2.isHorizontal) true
                else false
            }
            else false
        }

        fun join( l2: Line) {
//            if( p0 == l2.p0) { p0 = l2.p1 }
//            else
                if( p0 == l2.p1) { p0 = l2.p0 }
//            else if( p1 == l2.p0) { p1 = l2.p1 }
            else if( p1 == l2.p0) { p1 = l2.p1 }
            else error( "XXX")
//            else { p1 = l2.p0 }
        }
    }

    data class Region(val plot: Char, val locations: MutableList<Point>) {
        fun area(): Long = locations.size.toLong()

        fun perimeter(): Long =
            locations.map { currentLocation -> 4L - locations.count { it.distance(currentLocation) == 1 } }.sum()

        fun sides(): Long {
            var sides = buildList {
                locations.map { currentLocation ->
//                val neighbours = locations.filter { it.distance(currentLocation) == 1 }
//                val res = when (neighbours.size) {
//                    0 -> 4L
//                    1 -> 2L
//                    2 -> {
//                        if( neighbours[0].inSameLine( neighbours[1])) 0L
//                        else 1L
//                    }
//                    else -> 0L
//                }
//                res
                    add( Line( currentLocation, Point(currentLocation.i+1, currentLocation.j)))
                    add( Line( Point(currentLocation.i+1, currentLocation.j), Point(currentLocation.i+1, currentLocation.j + 1)))
                    add( Line( Point(currentLocation.i+1, currentLocation.j+1), Point(currentLocation.i, currentLocation.j + 1)))
                    add( Line( Point(currentLocation.i, currentLocation.j+1), currentLocation))
                }
            }.toMutableList()
//            sides = sides.groupingBy { it }.eachCount().filter { it.value != 2 }.keys.toMutableList()
            var changed = true
            while( changed) {
                changed = false
                var i = 0
                while( i < sides.size-1) {
                    val current = sides[i]
                    var j = i+1
                    while( j < sides.size) {
                        if( sides[j].continued(current)) {
                            current.join( sides[j])
                            sides.removeAt(j)
                            changed = true
                        }
                        else j += 1
                    }
                    sides = sides.filter { it.p0 != it.p1 }.toMutableList()
                    i += 1
                }
            }
            return sides.size.toLong()
        }
    }

    class Garden() {
        val regions = mutableListOf<Region>()

        fun addPlot(plot: Char, location: Point) {
            val similars = regions.filter { it.plot == plot }
            val region = similars
                .find { it.locations.any { location.distance(it) == 1 } }
            if (region == null) regions.add((Region(plot, mutableListOf(location))))
            else {
                region.locations.add(location)
                similars.forEach { r ->
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
