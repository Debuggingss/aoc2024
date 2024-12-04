package dev.d5b.aoc2024.days

import dev.d5b.aoc2024.Utils

abstract class Day(private val day: Int) {

    fun getInput(debug: Boolean): String {
        println("Getting input for day $day...")

        if (debug) return Utils.getTestInput(day)
        return Utils.getInput(day)
    }

    abstract fun part1(debug: Boolean)
    abstract fun part2(debug: Boolean)
}
