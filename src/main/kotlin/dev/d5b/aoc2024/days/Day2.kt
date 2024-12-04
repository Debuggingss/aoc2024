package dev.d5b.aoc2024.days

import kotlin.math.abs

object Day2 : Day(2) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val sum = data
            .trim()
            .split("\n")
            .map { it.split(" ") }
            .map { it.map { x -> x.toInt() } }
            .filter {
                (it == it.sorted() || it == it.sortedDescending()) &&
                filter(it)
            }.size

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val sum = data
            .trim()
            .split("\n")
            .map { it.split(" ") }
            .map { it.map { x -> x.toInt() } }
            .filter {
                ((it == it.sorted() || it == it.sortedDescending()) && filter(it)) ||
                it.indices.map { index ->
                    it.filterIndexed { i, _ -> i != index }
                }.any { x ->
                    (x == x.sorted() || x == x.sortedDescending()) &&
                    filter(x)
                }
            }.size

        println("\nAnswer: $sum")
    }

    private fun filter(x: List<Int>): Boolean {
        return x.indices.all { index ->
            val value = x[index]
            val prevValue = x.getOrElse(index - 1) { value - 1 }
            val diff = abs(value - prevValue)
            diff in 1..3
        }
    }
}
