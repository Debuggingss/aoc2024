package dev.d5b.aoc2024.days

object Day11 : Day(11) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        var stones = data
            .trim()
            .split(" ")
            .map { it.toLong() }

        for (i in 0 until 25) {
            val newStones = mutableListOf<Long>()

            stones.forEach {
                val string = it.toString()
                val length = string.length

                if (it == 0L) newStones.add(1L)
                else if (length % 2 == 0) {
                    newStones.add(string.substring(0, length / 2).toLong())
                    newStones.add(string.substring(length / 2).toLong())
                } else {
                    newStones.add(it * 2024L)
                }
            }

            stones = newStones
        }

        println("\nAnswer: ${stones.size}")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val stones = data
            .trim()
            .split(" ")
            .map { it.toLong() }

        val cache = mutableMapOf<Pair<Long, Int>, Long>()
        val result = stones.sumOf { calculateStone(it, 100, cache) }

        println("\nAnswer: $result")
    }

    private fun calculateStone(stone: Long, round: Int, cache: MutableMap<Pair<Long, Int>, Long>): Long {
        if (round == 0) return 1
        val pair = stone to round

        if (cache.containsKey(pair)) return cache[pair]!!

        val string = stone.toString()
        val length = string.length

        var count = 0L

        if (stone == 0L) {
            count += calculateStone(1L, round - 1, cache)
        } else if (length % 2 == 0) {
            count += calculateStone(string.substring(0, length / 2).toLong(), round - 1, cache)
            count += calculateStone(string.substring(length / 2).toLong(), round - 1, cache)
        } else {
            count += calculateStone(stone * 2024L, round - 1, cache)
        }

        cache[pair] = count

        return count
    }
}
