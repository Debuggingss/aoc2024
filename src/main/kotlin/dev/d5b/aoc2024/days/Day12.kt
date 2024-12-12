package dev.d5b.aoc2024.days

import kotlin.math.abs

object Day12 : Day(12) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map { it.toCharArray() }

        val gardens = mutableListOf<Garden>()

        rows.forEachIndexed { y, row ->
            row.forEachIndexed colLoop@{ x, c ->
                if (gardens.any { it.nodes.contains(Node(x, y)) }) return@colLoop

                val nulNode = Node(x, y)

                val stack = mutableListOf<Node>()
                val discovered = mutableListOf<Node>()

                stack.add(nulNode)

                while (stack.isNotEmpty()) {
                    val w = stack.removeFirst()

                    if (discovered.contains(w)) continue
                    discovered.add(w)

                    val wVal = rows[w.y][w.x]

                    val l = rows.getOrNull(w.y)?.getOrNull(w.x - 1)
                    val r = rows.getOrNull(w.y)?.getOrNull(w.x + 1)
                    val u = rows.getOrNull(w.y - 1)?.get(w.x)
                    val d = rows.getOrNull(w.y + 1)?.get(w.x)

                    if (l != null && wVal == c) stack.add(Node(w.x - 1, w.y))
                    if (r != null && wVal == c) stack.add(Node(w.x + 1, w.y))
                    if (u != null && wVal == c) stack.add(Node(w.x, w.y - 1))
                    if (d != null && wVal == c) stack.add(Node(w.x, w.y + 1))
                }

                gardens.add(Garden(c, discovered.filter { rows[it.y][it.x] == c }))
            }
        }

        val sum = gardens.sumOf { garden ->
            val perimeter = garden.nodes.sumOf { node ->
                val adj = garden.nodes.filter {
                    val distX = abs(node.x - it.x)
                    val distY = abs(node.y - it.y)

                    (distX == 1 && distY == 0) || (distX == 0 && distY == 1)
                }

                4 - adj.size
            }

            perimeter * garden.nodes.size
        }

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map { it.toCharArray() }

        val gardens = mutableListOf<Garden>()

        rows.forEachIndexed { y, row ->
            row.forEachIndexed colLoop@{ x, c ->
                if (gardens.any { it.nodes.contains(Node(x, y)) }) return@colLoop

                val nulNode = Node(x, y)

                val stack = mutableListOf<Node>()
                val discovered = mutableListOf<Node>()

                stack.add(nulNode)

                while (stack.isNotEmpty()) {
                    val w = stack.removeFirst()

                    if (discovered.contains(w)) continue
                    discovered.add(w)

                    val wVal = rows[w.y][w.x]

                    val l = rows.getOrNull(w.y)?.getOrNull(w.x - 1)
                    val r = rows.getOrNull(w.y)?.getOrNull(w.x + 1)
                    val u = rows.getOrNull(w.y - 1)?.get(w.x)
                    val d = rows.getOrNull(w.y + 1)?.get(w.x)

                    if (l != null && wVal == c) stack.add(Node(w.x - 1, w.y))
                    if (r != null && wVal == c) stack.add(Node(w.x + 1, w.y))
                    if (u != null && wVal == c) stack.add(Node(w.x, w.y - 1))
                    if (d != null && wVal == c) stack.add(Node(w.x, w.y + 1))
                }

                gardens.add(Garden(c, discovered.filter { rows[it.y][it.x] == c }))
            }
        }

        val sum = gardens.sumOf { garden ->
            val corners = garden.nodes.sumOf { node ->
                listOf(
                    checkCorner(garden.nodes, node, Node(-1, -1)),
                    checkCorner(garden.nodes, node, Node(1, 1)),
                    checkCorner(garden.nodes, node, Node(1, -1)),
                    checkCorner(garden.nodes, node, Node(-1, 1)),
                ).filter { it }.size
            }

            corners * garden.nodes.size
        }

        println("\nAnswer: $sum")
    }

    private fun checkCorner(nodes: List<Node>, pos: Node, offset: Node): Boolean {
        return (
            !nodes.contains(Node(pos.x, pos.y + offset.y)) &&
            !nodes.contains(Node(pos.x + offset.x, pos.y))
        ) || (
            nodes.contains(Node(pos.x, pos.y + offset.y)) &&
            nodes.contains(Node(pos.x + offset.x, pos.y)) &&
            !nodes.contains(Node(pos.x + offset.x, pos.y + offset.y))
        )
    }

    private data class Node(val x: Int, val y: Int)
    private data class Garden(val id: Char, val nodes: List<Node>)
}
