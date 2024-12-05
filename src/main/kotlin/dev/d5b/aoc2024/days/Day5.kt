package dev.d5b.aoc2024.days

import java.util.Collections

object Day5 : Day(5) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val split = data
            .trim()
            .split("\n\n")
            .map { it.split("\n") }

        val rules = split[0].map { it.split("|").map { x -> x.toInt() } }
        val updates = split[1].map { it.split(",").map { x -> x.toInt() } }

        val sum = updates.sumOf { update ->
            val wrong = rules.any { rule ->
                val (r1, r2) = rule
                if (!update.contains(r1) || !update.contains(r2)) return@any false
                update.indexOf(r1) > update.indexOf(r2)
            }

            if (!wrong) update[update.size / 2] else 0
        }

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val split = data
            .trim()
            .split("\n\n")
            .map { it.split("\n") }

        val rules = split[0].map { it.split("|").map { x -> x.toInt() } }
        val updates = split[1].map { it.split(",").map { x -> x.toInt() } }

        val sum = updates.sumOf { update ->
            var fixed = false

            // Jank but works, ensure every re-ordering rule is checked for every page
            update.indices.forEach {
                rules.forEach ruleLoop@{ rule ->
                    val (r1, r2) = rule
                    if (!update.contains(r1) || !update.contains(r2)) return@ruleLoop

                    val i1 = update.indexOf(r1)
                    val i2 = update.indexOf(r2)

                    if (i1 > i2) {
                        fixed = true
                        Collections.swap(update, i1, i2)
                    }
                }
            }

            if (fixed) update[update.size / 2] else 0
        }

        println("\nAnswer: $sum")
    }
}
