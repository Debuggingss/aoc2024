package dev.d5b.aoc2024.days

object Day8 : Day(8) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map { it.split("").filter { cell -> cell.isNotEmpty() } }

        val antennas = mutableListOf<Antenna>()

        rows.forEachIndexed { y, row ->
            row.forEachIndexed col@{ x, col ->
                if (col == ".") return@col
                antennas.add(Antenna(col, x, y))
            }
        }

        val antennasGrouped = antennas.groupBy { it.frequency }

        val antiNodes = mutableListOf<Pair<Int, Int>>()

        antennasGrouped.values.forEach { group ->
            group.forEach { a ->
                val other = group.filter { it != a }

                other.forEach {
                    val (offsetX, offsetY) = it.offsetTo(a)

                    val x = a.x - offsetX
                    val y = a.y - offsetY

                    if (x in rows.indices && y in rows[0].indices) {
                        antiNodes.add(Pair(x, y))
                    }
                }
            }
        }

        val sum = antiNodes.distinct().size

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map { it.split("").filter { cell -> cell.isNotEmpty() } }

        val antennas = mutableListOf<Antenna>()

        rows.forEachIndexed { y, row ->
            row.forEachIndexed col@{ x, col ->
                if (col == ".") return@col
                antennas.add(Antenna(col, x, y))
            }
        }

        val antennasGrouped = antennas.groupBy { it.frequency }

        val antiNodes = mutableListOf<Pair<Int, Int>>()

        antennasGrouped.values.forEach { group ->
            group.forEach { a ->
                val other = group.filter { it != a }

                other.forEach {
                    val (offsetX, offsetY) = it.offsetTo(a)

                    for (i in -rows.size..rows.size) {
                        val x = a.x - offsetX * i
                        val y = a.y - offsetY * i

                        if (x in rows.indices && y in rows[0].indices) {
                            antiNodes.add(Pair(x, y))
                        }
                    }
                }
            }
        }

        val sum = antiNodes.distinct().size

        println("\nAnswer: $sum")
    }

    private data class Antenna(val frequency: String, val x: Int, val y: Int) {

        fun offsetTo(antenna: Antenna): Pair<Int, Int> {
            return Pair(x - antenna.x, y - antenna.y)
        }
    }
}
