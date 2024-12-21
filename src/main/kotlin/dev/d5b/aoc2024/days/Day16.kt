package dev.d5b.aoc2024.days

object Day16 : Day(16) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map {
                it.split("")
                    .filter {x -> x.isNotEmpty()}
            }

        val start = Node(1, rows.size - 2, Direction.RIGHT)

        val stack = mutableListOf<Pair<Node, Path>>()
        val paths = mutableListOf<Path>()
        val costs = mutableMapOf<Node, Int>()

        stack.add(start to Path.init(start))

        while (stack.isNotEmpty()) {
            val (w, path) = stack.removeFirst()

            if (costs.containsKey(w) && costs[w]!! < path.cost) continue
            costs[w] = path.cost

            val wVal = rows[w.y][w.x]

            if (wVal == "#") continue

            if (wVal == "E") {
                if (!paths.contains(path)) paths.add(path)
                continue
            }

            Direction.entries.forEach {
                if (it == Direction.NULL) return@forEach
                if (w.direction.opposite() == it) return@forEach
                if (w.x + it.x !in 0..rows[0].size || w.y + it.y !in 0..rows.size) return@forEach

                val new = Node(w.x + it.x, w.y + it.y, it)
                if (Node.contains(path.nodes, new)) return@forEach

                val newPath = path.cloneAdd(new)

                if (new.direction != path.nodes.last().direction) newPath.cost += 1000
                newPath.cost += 1

                stack.add(new to newPath)
            }
        }

        val cheapest = paths.minByOrNull { it.cost }!!

        rows.forEachIndexed { y, row ->
            row.forEachIndexed colLoop@{ x, c ->
                val n = Node(x, y)
                if (Node.contains(cheapest.nodes, n)) {
                    print(cheapest.nodes.find { it.equals(n) }?.direction?.char)
                }
                else print(c)
            }
            println()
        }

        println("\nAnswer: ${cheapest.cost}")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map {
                it.split("")
                    .filter {x -> x.isNotEmpty()}
            }

        val start = Node(1, rows.size - 2, Direction.RIGHT)

        val stack = mutableListOf<Pair<Node, Path>>()
        val paths = mutableListOf<Path>()
        val costs = mutableMapOf<Node, Int>()

        stack.add(start to Path.init(start))

        while (stack.isNotEmpty()) {
            val (w, path) = stack.removeFirst()

            if (costs.containsKey(w) && costs[w]!! < path.cost) continue
            costs[w] = path.cost

            val wVal = rows[w.y][w.x]

            if (wVal == "#") continue

            if (wVal == "E") {
                if (!paths.contains(path)) paths.add(path)
                continue
            }

            Direction.entries.forEach {
                if (it == Direction.NULL) return@forEach
                if (w.direction.opposite() == it) return@forEach
                if (w.x + it.x !in 0..rows[0].size || w.y + it.y !in 0..rows.size) return@forEach

                val new = Node(w.x + it.x, w.y + it.y, it)
                if (Node.contains(path.nodes, new)) return@forEach

                val newPath = path.cloneAdd(new)

                if (new.direction != path.nodes.last().direction) newPath.cost += 1000
                newPath.cost += 1

                stack.add(new to newPath)
            }
        }

        val shortest = paths.groupBy { it.cost }
        val sorted = shortest.keys.minOf { it }
        val seats = shortest[sorted]!!.map { it.nodes }.flatten().map { Node(it.x, it.y) }.distinct()

        println("\nAnswer: ${seats.size}")
    }

    private enum class Direction(val x: Int, val y: Int, val char: Char) {
        NULL(0, 0, '0'),
        UP(0, -1, '^'),
        DOWN(0, 1, 'v'),
        LEFT(-1, 0, '<'),
        RIGHT(1, 0, '>');

        fun opposite(): Direction {
            return when (this) {
                UP -> DOWN
                DOWN -> UP
                LEFT -> RIGHT
                RIGHT -> LEFT
                else -> NULL
            }
        }
    }

    private data class Path(val nodes: List<Node>, var cost: Int) {

        fun cloneAdd(node: Node): Path {
            return Path(nodes + node, cost)
        }

        companion object {

            fun init(start: Node): Path {
                return Path(mutableListOf(start), 0)
            }
        }
    }

    private data class Node(val x: Int, val y: Int, val direction: Direction = Direction.NULL) {

        fun equals(node: Node): Boolean {
            return node.x == x && node.y == y
        }

        companion object {

            fun contains(list: List<Node>, node: Node): Boolean {
                return list.any { it.equals(node) }
            }
        }
    }
}
