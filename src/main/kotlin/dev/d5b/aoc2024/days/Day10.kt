package dev.d5b.aoc2024.days

object Day10 : Day(10) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map {
                it.split("")
                    .filter {x -> x.isNotEmpty()}
                    .map{ x -> x.toInt() }
            }

        // MutableSet to avoid duplicate end nodes
        val counts = mutableMapOf<Node, MutableSet<Node>>()

        rows.forEachIndexed { y, row ->
            row.forEachIndexed colLoop@{ x, c ->
                if (c != 0) return@colLoop

                val nulNode = Node(x, y)

                val stack = mutableListOf<Node>()

                stack.add(nulNode)

                while (stack.isNotEmpty()) {
                    val w = stack.removeFirst()
                    val wVal = rows[w.y][w.x]

                    if (wVal == 9) {
                        if (counts.containsKey(nulNode)) {
                            counts[nulNode]?.add(w)
                        } else {
                            counts[nulNode] = mutableSetOf(w)
                        }
                    }

                    val l = rows.getOrNull(w.y)?.getOrNull(w.x - 1)
                    val r = rows.getOrNull(w.y)?.getOrNull(w.x + 1)
                    val u = rows.getOrNull(w.y - 1)?.get(w.x)
                    val d = rows.getOrNull(w.y + 1)?.get(w.x)

                    if (l != null && l - wVal == 1) stack.add(Node(w.x - 1, w.y))
                    if (r != null && r - wVal == 1) stack.add(Node(w.x + 1, w.y))
                    if (u != null && u - wVal == 1) stack.add(Node(w.x, w.y - 1))
                    if (d != null && d - wVal == 1) stack.add(Node(w.x, w.y + 1))
                }
            }
        }

        val sum = counts.map { it.value.size }.sum()

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map {
                it.split("")
                    .filter {x -> x.isNotEmpty()}
                    .map{ x -> x.toInt() }
            }

        // MutableList to store even duplicate end nodes
        val counts = mutableMapOf<Node, MutableList<Node>>()

        rows.forEachIndexed { y, row ->
            row.forEachIndexed colLoop@{ x, c ->
                if (c != 0) return@colLoop

                val nulNode = Node(x, y)

                val stack = mutableListOf<Node>()

                stack.add(nulNode)

                while (stack.isNotEmpty()) {
                    val w = stack.removeFirst()
                    val wVal = rows[w.y][w.x]

                    if (wVal == 9) {
                        if (counts.containsKey(nulNode)) {
                            counts[nulNode]?.add(w)
                        } else {
                            counts[nulNode] = mutableListOf(w)
                        }
                    }

                    val l = rows.getOrNull(w.y)?.getOrNull(w.x - 1)
                    val r = rows.getOrNull(w.y)?.getOrNull(w.x + 1)
                    val u = rows.getOrNull(w.y - 1)?.get(w.x)
                    val d = rows.getOrNull(w.y + 1)?.get(w.x)

                    if (l != null && l - wVal == 1) stack.add(Node(w.x - 1, w.y))
                    if (r != null && r - wVal == 1) stack.add(Node(w.x + 1, w.y))
                    if (u != null && u - wVal == 1) stack.add(Node(w.x, w.y - 1))
                    if (d != null && d - wVal == 1) stack.add(Node(w.x, w.y + 1))
                }
            }
        }

        val sum = counts.map { it.value.size }.sum()

        println("\nAnswer: $sum")
    }

    private data class Node(val x: Int, val y: Int)
}
