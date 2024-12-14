package dev.d5b.aoc2024.days

object Day14 : Day(14) {

    // Position cannot be negative in the input
    private val reg = "p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)".toRegex()

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val robots = reg.findAll(data.trim())
            .map {
                val (x, y, vx, vy) = it.destructured
                Robot(
                    Pos(x.toInt(), y.toInt()),
                    Pos(vx.toInt(), vy.toInt())
                )
            }.toMutableList()

        val width = if (debug) 11 else 101
        val height = if (debug) 7 else 103

        for (i in 0..99) {
            for (r in robots.indices) {
                val robot = robots[r]

                val pos = robot.pos
                val v = robot.velocity

                robot.pos = Pos(
                    wrap(pos.x + v.x, width),
                    wrap(pos.y + v.y, height)
                )
            }
        }

        for (y in 0 until height) {
            for (x in 0 until width) {
                // if (x == width / 2 || y == height / 2) {
                //     print(" ")
                //     continue
                // }

                print(robots.filter { it.pos == Pos(x, y) }.toList().size.toString().replace("0", "."))
            }
            println()
        }

        val topLeft = robots.filter {
            it.pos.x in 0 until width / 2 &&
            it.pos.y in 0 until height / 2
        }

        val topRight = robots.filter {
            it.pos.x in width / 2 + 1 until width &&
            it.pos.y in 0 until height / 2
        }

        val bottomLeft = robots.filter {
            it.pos.x in 0 until width / 2 &&
            it.pos.y in height / 2 + 1 until height
        }

        val bottomRight = robots.filter {
            it.pos.x in width / 2 + 1 until width &&
            it.pos.y in height / 2 + 1 until height
        }

        val sum = topLeft.size * topRight.size * bottomLeft.size * bottomRight.size

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val robots = reg.findAll(data.trim())
            .map {
                val (x, y, vx, vy) = it.destructured
                Robot(
                    Pos(x.toInt(), y.toInt()),
                    Pos(vx.toInt(), vy.toInt())
                )
            }.toMutableList()

        val width = if (debug) 11 else 101
        val height = if (debug) 7 else 103

        var count = 0

        while (true) {
            count++
            println("--- $count seconds elapsed ---")

            for (r in robots.indices) {
                val robot = robots[r]

                val pos = robot.pos
                val v = robot.velocity

                robot.pos = Pos(
                    wrap(pos.x + v.x, width),
                    wrap(pos.y + v.y, height)
                )
            }

            // Ensure no robots overlap
            if (robots.groupBy { it.pos }.any { it.value.size != 1 }) {
                continue
            }

            for (y in 0 until height) {
                for (x in 0 until width) {
                    print(robots.filter { it.pos == Pos(x, y) }.toList().size.toString().replace("0", "."))
                }
                println()
            }

            readln() // Pause
        }
    }

    private fun wrap(n: Int, max: Int): Int {
        return ((n % max) + max) % max
    }

    private data class Pos(val x: Int, val y: Int)

    private data class Robot(var pos: Pos, val velocity: Pos) {
        override fun toString(): String {
            return "Robot(p=${pos.x},${pos.y} v=${velocity.x},${velocity.y})"
        }
    }
}
