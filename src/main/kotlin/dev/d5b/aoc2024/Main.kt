package dev.d5b.aoc2024

import dev.d5b.aoc2024.days.*
import java.time.LocalDate

val days = mapOf(
    1 to Day1,
    2 to Day2,
    3 to Day3,
    4 to Day4,
    5 to Day5,
    6 to Day6,
    7 to Day7,
    8 to Day8,
    9 to Day9,
    10 to Day10,
    11 to Day11,
    12 to Day12,
    13 to Day13,
    14 to Day14,
)

fun main() {
    print("Enter day: ")
    var day = readln()

    if (day.isEmpty()) day = LocalDate.now().dayOfMonth.toString()

    print("Enter part: ")
    val part = readln()

    print("Debug (Y/N): ")
    val debug = readln().lowercase() == "y"

    if (debug) println("--- RUNNING WITH TEST INPUT ---")

    when (part) {
        "1" -> days[day.toInt()]?.part1(debug)
        "2" -> days[day.toInt()]?.part2(debug)
        else -> {
            println("\nPart 1")
            days[day.toInt()]?.part1(debug)
            println("\nPart 2")
            days[day.toInt()]?.part2(debug)
        }
    }
}
