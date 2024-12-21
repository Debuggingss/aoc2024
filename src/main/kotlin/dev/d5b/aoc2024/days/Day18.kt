package dev.d5b.aoc2024.days

object Day18 : Day(18) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map {
                val s = it.split(",")
                Node(s[0].toInt(), s[1].toInt())
            }

        val (width, height) = if (debug) 6 to 6 else 70 to 70
        val bytes = rows.subList(0, if (debug) 12 else 1024)

        val stack = mutableListOf<Cursor>()
        val costs = mutableMapOf<Node, Int>()
        val visited = mutableListOf<Node>()

        val start = Node(0, 0)
        stack.add(Cursor(start))

        var score = -1

        while (stack.isNotEmpty()) {
            val c = stack.removeFirst()
            val w = c.node

            if (bytes.contains(w) || visited.contains(w)) continue
            visited.add(w)

            if (costs.containsKey(w) && costs[w]!! < c.cost) continue
            costs[w] = c.cost

            if (w == Node(width, height)) {
                score = c.cost
                break
            }

            Direction.entries.forEach {
                val new = Node(w.x + it.x, w.y + it.y)

                if (new.x !in 0..width || new.y !in 0..height) return@forEach

                stack.add(Cursor(new, c.cost + 1))
            }
        }

        println("\nAnswer: $score")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map {
                val s = it.split(",")
                Node(s[0].toInt(), s[1].toInt())
            }

        val (width, height) = if (debug) 6 to 6 else 70 to 70

        // Manually guessed a starting point close enough, because the algo is still slow
        var counter = 2500

        for (i in 0..rows.size) {
            val bytes = rows.subList(0, 2500 + i)

            val stack = mutableListOf<Cursor>()
            val costs = mutableMapOf<Node, Int>()
            val visited = mutableListOf<Node>()

            val start = Node(0, 0)
            stack.add(Cursor(start))

            var score = -1

            while (stack.isNotEmpty()) {
                val c = stack.removeFirst()
                val w = c.node

                if (bytes.contains(w) || visited.contains(w)) continue
                visited.add(w)

                if (costs.containsKey(w) && costs[w]!! < c.cost) continue
                costs[w] = c.cost

                if (w == Node(width, height)) {
                    score = c.cost
                    break
                }

                Direction.entries.forEach {
                    val new = Node(w.x + it.x, w.y + it.y)

                    if (new.x !in 0..width || new.y !in 0..height) return@forEach

                    stack.add(Cursor(new, c.cost + 1))
                }
            }

            println("$i yielded $score")

            if (score == -1) break
            counter++
        }

        val lastByte = rows[counter - 1]

        println("\nAnswer: ${lastByte.x},${lastByte.y}")
    }

    private enum class Direction(val x: Int, val y: Int) {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);
    }

    private data class Cursor(val node: Node, var cost: Int = 0)

    private data class Node(val x: Int, val y: Int)
}
