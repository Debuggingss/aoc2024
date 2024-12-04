package dev.d5b.aoc2024.days

import kotlin.math.abs

object Day1 : Day(1) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map { it.split("   ") }
            .map { it.map { x -> x.toInt() } }

        val r1 = rows.map { it[0] }.sorted()
        val r2 = rows.map { it[1] }.sorted()

        val sum = rows.indices.sumOf { abs(r1[it] - r2[it]) }

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val rows = data
            .trim()
            .split("\n")
            .map { it.split("   ") }
            .map { it.map { x -> x.toInt() } }

        val r1 = rows.map { it[0] }.sorted()
        val r2 = rows.map { it[1] }.sorted()

        val sum = r1.sumOf { lf -> lf * r2.filter { it == lf }.size }

        println("\nAnswer: $sum")
    }
}
