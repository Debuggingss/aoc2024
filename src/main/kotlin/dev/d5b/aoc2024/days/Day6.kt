package dev.d5b.aoc2024.days

object Day6 : Day(6) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val area = data
            .trim()
            .split("\n")
            .map { it.split("").filter { cell -> cell.isNotEmpty() } }

        var guardRow = area.indexOf(area.find { it.indexOf("^") != -1 })
        var guardCol = area[guardRow].indexOf("^")
        var direction = Direction.UP
        val visits = mutableListOf<Pair<Int, Int>>()

        visits.add(Pair(guardRow, guardCol))

        while (true) {
            when (direction) {
                Direction.UP -> {
                    val next = area.getOrNull(guardRow - 1)?.get(guardCol) ?: break
                    if (next == "#") direction = Direction.RIGHT
                    else guardRow--
                }
                Direction.DOWN -> {
                    val next = area.getOrNull(guardRow + 1)?.get(guardCol) ?: break
                    if (next == "#") direction = Direction.LEFT
                    else guardRow++
                }
                Direction.LEFT -> {
                    val next = area[guardRow].getOrNull(guardCol - 1) ?: break
                    if (next == "#") direction = Direction.UP
                    else guardCol--
                }
                Direction.RIGHT -> {
                    val next = area[guardRow].getOrNull(guardCol + 1) ?: break
                    if (next == "#") direction = Direction.DOWN
                    else guardCol++
                }
            }

            visits.add(Pair(guardRow, guardCol))
        }

        val sum = visits.distinct().size

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val realArea = data
            .trim()
            .split("\n")
            .map { it.split("").filter { cell -> cell.isNotEmpty() } }

        var infinites = 0
        var counter = 0

        for (x in realArea.indices) {
            for (y in realArea[0].indices) {
                if (realArea[x][y] == "#" || realArea[x][y] == "^") continue
                val area = realArea.toMutableList().map { it.toMutableList() }
                area[x][y] = "#"

                var guardRow = area.indexOf(area.find { it.indexOf("^") != -1 })
                var guardCol = area[guardRow].indexOf("^")
                var direction = Direction.UP

                counter++

                var exited = true

                while (true) {
                    when (direction) {
                        Direction.UP -> {
                            val next = area.getOrNull(guardRow - 1)?.get(guardCol) ?: break
                            if (next == "#") direction = Direction.RIGHT
                            else guardRow--
                        }

                        Direction.DOWN -> {
                            val next = area.getOrNull(guardRow + 1)?.get(guardCol) ?: break
                            if (next == "#") direction = Direction.LEFT
                            else guardRow++
                        }

                        Direction.LEFT -> {
                            val next = area[guardRow].getOrNull(guardCol - 1) ?: break
                            if (next == "#") direction = Direction.UP
                            else guardCol--
                        }

                        Direction.RIGHT -> {
                            val next = area[guardRow].getOrNull(guardCol + 1) ?: break
                            if (next == "#") direction = Direction.DOWN
                            else guardCol++
                        }
                    }

                    if (counter > 10000) {
                        exited = false
                        break
                    }
                    counter++
                }

                if (!exited) infinites++
                counter = 0
            }
        }

        println("\nAnswer: $infinites")
    }

    private enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
