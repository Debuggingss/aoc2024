package dev.d5b.aoc2024.days

object Day15 : Day(15) {

    private const val EMPTY = "."
    private const val WALL = "#"
    private const val ROBOT = "@"
    private const val BOX = "O"
    private const val BOX_L = "["
    private const val BOX_R = "]"

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val split = data
            .trim()
            .split("\n\n")

        val grid = split[0]
            .split("\n")
            .map {
                it.split("")
                    .filter { c -> c.isNotEmpty() }
                    .toMutableList()
            }
            .filter { it.isNotEmpty() }
            .toMutableList()

        val moves = split[1].replace("\n", "")

        val gridSize = Pos(grid[0].size, grid.size)

        val robot = Pos(
            grid[grid.indexOfFirst { it.contains(ROBOT) }].indexOf(ROBOT),
            grid.indexOfFirst { it.contains(ROBOT) }
        )

        moves.forEach { move ->
            val direction = Direction.from(move) ?: return@forEach
            val dirOffset = direction.offset

            val next = robot.offset(dirOffset)
            val nextChar = grid[next.y][next.x]

            // No boxes
            if (nextChar == EMPTY) {
                grid[robot.y][robot.x] = EMPTY  // remove current char
                robot.offsetMut(dirOffset)      // update position
                grid[robot.y][robot.x] = ROBOT  // add new char
                return@forEach
            }

            // Do nothing for wall
            if (nextChar == WALL) {
                return@forEach
            }

            // Move boxes
            val boxes = mutableListOf<Pos>()

            val range = when (direction) {
                Direction.LEFT -> {
                    robot.x - 1 downTo 0
                }
                Direction.RIGHT -> {
                    robot.x + 1 until gridSize.x
                }
                Direction.UP -> {
                    robot.y - 1 downTo 0
                }
                Direction.DOWN -> {
                    robot.y + 1 until gridSize.y
                }
            }

            for (i in range) {
                val cell = if (dirOffset.x != 0) {  // movement on X axis
                    grid[robot.y][i]
                } else {                            // movement on Y axis
                    grid[i][robot.x]
                }

                if (cell == BOX) {
                    if (dirOffset.x != 0) {         // movement on X axis
                        boxes.add(Pos(i, robot.y))
                    } else {                        // movement on Y axis
                        boxes.add(Pos(robot.x, i))
                    }
                }
                else if (cell == EMPTY) break       // empty means we can move the boxes
                else if (cell == WALL) {            // wall means the whole row is fucked
                    boxes.clear()
                    break
                }
            }

            boxes.reversed().forEach {
                grid[it.y][it.x] = EMPTY
                grid[it.y + dirOffset.y][it.x + dirOffset.x] = BOX
            }

            // move the damn robot
            if (boxes.isNotEmpty()) {
                grid[robot.y][robot.x] = EMPTY      // remove current char
                robot.offsetMut(dirOffset)          // update position
                grid[robot.y][robot.x] = ROBOT      // add new char
            }
        }

        println(grid.joinToString("\n") { it.joinToString("") })

        var sum = 0

        for (y in 0 until gridSize.y) {
            for (x in 0 until gridSize.x) {
                if (grid[y][x] == BOX) {
                    sum += y * 100 + x
                }
            }
        }

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val split = data
            .trim()
            .split("\n\n")

        val newNodes = mapOf(
            "#" to "##",
            "." to "..",
            "O" to "[]",
            "@" to "@."
        )

        // Incredible development
        val grid = split[0]
            .split("\n")
            .map {
                it.split("")
                    .filter { c -> c.isNotEmpty() }
                    .map { c -> newNodes[c] }
                    .joinToString("")
                    .split("")
                    .filter { c -> c.isNotEmpty() }
                    .toMutableList()
            }
            .filter { it.isNotEmpty() }
            .toMutableList()

        val moves = split[1].replace("\n", "")

        val gridSize = Pos(grid[0].size, grid.size)

        val robot = Pos(
            grid[grid.indexOfFirst { it.contains(ROBOT) }].indexOf(ROBOT),
            grid.indexOfFirst { it.contains(ROBOT) }
        )

        moves.forEach { move ->
            val direction = Direction.from(move) ?: return@forEach
            val dirOffset = direction.offset

            val next = robot.offset(dirOffset)
            val nextChar = grid[next.y][next.x]

            // No boxes
            if (nextChar == EMPTY) {
                grid[robot.y][robot.x] = EMPTY  // remove current char
                robot.offsetMut(dirOffset)      // update position
                grid[robot.y][robot.x] = ROBOT  // add new char
                return@forEach
            }

            // Do nothing for wall
            if (nextChar == WALL) {
                return@forEach
            }

            // Move boxes
            val boxes = mutableListOf<Pos>()
            val range = when (direction) {
                Direction.LEFT -> {
                    robot.x - 1 downTo 0
                }
                Direction.RIGHT -> {
                    robot.x + 1 until gridSize.x
                }
                else -> null  // All I want to say is that they don't really care about us
            }

            if (dirOffset.x != 0) {  // movement on X axis
                for (i in range!!) {
                    val cell = grid[robot.y][i]

                    if (cell == BOX_L || cell == BOX_R) boxes.add(Pos(i, robot.y))
                    else if (cell == EMPTY) break       // empty means we can move the boxes
                    else if (cell == WALL) {            // wall means the whole row is fucked
                        boxes.clear()
                        break
                    }
                }
            } else {
                boxes.addAll(
                    mapBoxesY(grid, robot.offset(dirOffset), direction)
                        ?.sortedBy { it.y }
                        ?: emptyList()
                )

                if (direction == Direction.UP) boxes.reverse()
            }

            boxes.reversed().forEach {
                val type = grid[it.y][it.x]

                grid[it.y][it.x] = EMPTY
                grid[it.y + dirOffset.y][it.x + dirOffset.x] = type
            }

            // move the damn robot
            if (boxes.isNotEmpty()) {
                grid[robot.y][robot.x] = EMPTY      // remove current char
                robot.offsetMut(dirOffset)          // update position
                grid[robot.y][robot.x] = ROBOT      // add new char
            }
        }

        println(grid.joinToString("\n") { it.joinToString("") })

        var sum = 0

        for (y in 0 until gridSize.y) {
            for (x in 0 until gridSize.x) {
                if (grid[y][x] == BOX_L) {
                    sum += y * 100 + x
                }
            }
        }

        println("\nAnswer: $sum")
    }

    private fun mapBoxesY(grid: List<List<String>>, start: Pos, direction: Direction): List<Pos>? {
        if (grid[start.y][start.x] == WALL) return null

        val boxes = mutableListOf<Pos>()

        val cell = grid[start.y][start.x]

        if (cell == EMPTY) return emptyList()

        boxes.add(Pos(start.x, start.y))
        boxes.add(Pos(start.x, start.y).offset(if (cell == BOX_R) Pos(-1, 0) else Pos(1, 0)))

        val above = mapBoxesY(
            grid,
            start.offset(direction.offset),
            direction
        ) ?: return null

        val aboveOffset = mapBoxesY(
            grid,
            start
                .offset(direction.offset)
                .offset(if (cell == BOX_R) Pos(-1, 0) else Pos(1, 0)),
            direction
        ) ?: return null

        boxes.addAll(above)
        boxes.addAll(aboveOffset)

        return boxes.distinct()
    }

    private enum class Direction(private val char: Char, val offset: Pos) {
        LEFT('<', Pos(-1, 0)),
        RIGHT('>', Pos(1, 0)),
        UP('^', Pos(0, -1)),
        DOWN('v', Pos(0, 1));

        companion object {
            private val map = entries.associateBy { it.char }
            infix fun from(char: Char) = map[char]
        }
    }

    private data class Pos(var x: Int, var y: Int) {
        fun offsetMut(by: Pos) {
            x += by.x
            y += by.y
        }

        fun offset(by: Pos): Pos {
            return Pos(x + by.x, y + by.y)
        }
    }
}
