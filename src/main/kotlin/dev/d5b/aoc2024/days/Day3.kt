package dev.d5b.aoc2024.days

object Day3 : Day(3) {

    private val reg = Regex("mul\\((\\d+),(\\d+)\\)")
    private val reg2 = Regex("do\\(\\)(.+?)don't\\(\\)")

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val sum = reg.findAll(
            data.trim()
        ).sumOf {
            val values = it.destructured.toList()
            values[0].toInt() * values[1].toInt()
        }

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        // New solution, janky way but it works
        val sum = reg.findAll(
            "do()${data.trim()}don't()"
                .split("do()")
                .joinToString("") {
                    it.split("don't()")[0]
                }
        ).sumOf {
            val values = it.destructured.toList()
            values[0].toInt() * values[1].toInt()
        }

        println("\nAnswer: $sum")

        // Old solution, doesn't work
        val sum1 = reg2.findAll(
            "do()${data.trim()}don't()"
        ).sumOf { matches ->
            val doBlock = matches.destructured.toList().first()

            reg.findAll(doBlock).sumOf {
                val values = it.destructured.toList()
                values[0].toInt() * values[1].toInt()
            }
        }

        println("\nWrong Answer: $sum1")
    }
}
