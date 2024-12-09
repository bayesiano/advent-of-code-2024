object Day9 {
    private const val debug = true
    private val day = this::class.simpleName!!.lowercase()

    @JvmStatic
    fun main(args: Array<String>) {
        println(ULong.MAX_VALUE)
        runProblemRaw("$day/example_1.txt", "$day.Example 1a", solution = 60) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1b.txt", "$day.Example 1b", solution = 1928) {
            solveProblem1(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 1", solution = 6430446922192) {
            solveProblem1(it)
        }
        runProblemRaw("$day/example_1b.txt", "$day.Example 2", solution = 2858) {
            solveProblem2(it)
        }
        runProblemRaw("$day/problem_1.txt", "$day.Problem 2", solution = 6460170593016) {
            solveProblem2(it)
        }
//        //426214137279934
    }

    data class Block(var id: Int, var length: Int) {

    }

    private fun solveProblem1(input: String): Long {
        val disk = buildList {
            input.mapIndexed { index, c ->
                add(Block(if (index % 2 == 0) index / 2 else -1, c.digitToInt()))
            }
        }.toMutableList()

        val compacted = buildList {
            var i0 = 0
            var iN = disk.size - 1
            while (iN >= i0) {
                if (disk[iN].id < 0 || disk[iN].length == 0) {
                    iN -= 1
                } else if (disk[i0].id >= 0) {
                    add(Block(disk[i0].id, disk[i0].length))
                    i0 += 1
                } else {
                    val diff = disk[iN].length - disk[i0].length
                    if (diff >= 0) {
//                        val diff = disk[iN].length - disk[i0].length
                        add(Block(disk[iN].id, disk[i0].length))
                        disk[iN].length = diff
                        i0 += 1
                    } else {
                        add(Block(disk[iN].id, disk[iN].length))
                        disk[i0].length = -diff
                        iN -= 1
                    }
                }
            }
        }

//        compacted.forEach {
//            println("- $it")
//        }
        var i = 0
        return compacted.asSequence().mapIndexed { index, block ->
            (1..block.length).fold(0L) { acc, value ->
                acc + block.id * i++
            }
        }.sum()
    }


    private fun solveProblem2(input: String): Long {
        var disk = buildList {
            input.mapIndexed { index, c ->
                add(Block(if (index % 2 == 0) index / 2 else -1, c.digitToInt()))
            }
        }.toMutableList()

        var i = disk.size - 1
        while (i > 0) {
            if (disk[i].id >= 0) {
                if (disk[i].id >= 0) {
                    for (j in 0..i - 1) {
                        if (disk[j].id < 0) {
                            val diff = disk[i].length - disk[j].length
                            if (diff <= 0) {
                                disk = buildList {
                                    disk.subList(0, j).forEach { add(it) }
                                    add(Block(disk[i].id, disk[i].length))
                                    if (diff < 0)
                                        add(Block(-1, -diff))
                                    addAll(disk.subList(j + 1, i))
                                    add(Block(-1, disk[i].length))
//                                disk[i].id = -1
                                    addAll(disk.subList(i + 1, disk.size))
                                }.toMutableList()
                                var z = 0
                                while (z < disk.size - 1) {
                                    if (disk[z].id == -1 && disk[z + 1].id == -1) {
                                        disk[z].length += disk[z + 1].length
                                        disk.removeAt(z + 1)
                                    } else z += 1
                                }
                                break
                            }
                        }
                    }
                }
            }
            i -= 1
        }
//        disk.forEach {
//            println("- $it")
//        }
//        val compacted = buildList {
//            var i0 = 0
//            var iN = disk.size - 1
//            while (iN >= i0) {
//                if (disk[iN].id < 0 || disk[iN].length == 0) {
////                    disk.removeAt(iN)
//                    iN -= 1
//                } else if (disk[i0].id >= 0) {
//                    add(Block(disk[i0].id, disk[i0].length))
//                    i0 += 1
//                } else {
//                    val diff = disk[iN].length - disk[i0].length
//                    if( diff >= 0) {
////                        val diff = disk[iN].length - disk[i0].length
////                        add(Block(disk[iN].id, disk[i0].length))
////                        disk[iN].length = diff
////                        i0 += 1
//                        iN -= 1
//                    }
//                    else {
//                        add(Block(disk[iN].id, disk[iN].length))
//                        disk[i0].length = -diff
//                        iN -= 1
//                    }
//                }
//            }
//        }

//        disk.forEach {
//            println( "- $it")
//        }
        var n = 0
        val totals = disk.asSequence().mapIndexed { index, block ->
            (1..block.length).fold(0L) { acc, value ->
                if( block.id < 0) {
                    n++
                    0
                }
                else acc + block.id * n++
            }
        }.toList()
        return totals.sum()
    }


}
