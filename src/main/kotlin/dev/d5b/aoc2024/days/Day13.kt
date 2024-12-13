package dev.d5b.aoc2024.days

object Day13 : Day(13) {

    private val reg = """
        Button A: X\+(\d+), Y\+(\d+)
        Button B: X\+(\d+), Y\+(\d+)
        Prize: X=(\d+), Y=(\d+)
    """.trimIndent().toRegex()

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val prizes = reg.findAll(data.trim())

        val wins = mutableListOf<Pair<Int, Int>>()

        prizes.forEach {
            val (aX, aY, bX, bY, pX, pY) = it.destructured

            for (a in 0..99) {
                var broken = false

                for (b in 0..99) {
                    if (
                        a * aX.toInt() + b * bX.toInt() == pX.toInt() &&
                        a * aY.toInt() + b * bY.toInt() == pY.toInt()
                    ) {
                        wins.add(a to b)

                        broken = true
                        break
                    }
                }

                if (broken) break
            }
        }

        val sum = wins.sumOf { it.first * 3 + it.second }

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val prizes = reg.findAll(data.trim())

        val wins = mutableListOf<Pair<Long, Long>>()

        prizes.forEach {
            val (aX, aY, bX, bY, pX, pY) = it.destructured

            val prizeX = pX.toLong() + 10000000000000L
            val prizeY = pY.toLong() + 10000000000000L

            val (a, b) = solve(aX.toLong(), aY.toLong(), bX.toLong(), bY.toLong(), prizeX, prizeY)

            if (
                a * aX.toLong() + b * bX.toLong() == prizeX &&
                a * aY.toLong() + b * bY.toLong() == prizeY
            ) wins.add(a to b)
        }

        val sum = wins.sumOf { it.first * 3 + it.second }

        println("\nAnswer: $sum")
    }

    // https://www.reddit.com/r/adventofcode/comments/1hd7irq/2024_day_13_an_explanation_of_the_mathematics/
    private fun solve(aX: Long, aY: Long, bX: Long, bY: Long, pX: Long, pY: Long): Pair<Long, Long> {
        val d = (aX * bY - aY * bX)
        val a = (pX * bY - pY * bX) / d
        val b = (pY * aX - pX * aY) / d

        return a to b
    }
}
