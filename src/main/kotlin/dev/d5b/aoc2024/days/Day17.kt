package dev.d5b.aoc2024.days

import kotlin.math.pow

object Day17 : Day(17) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val input = data.trim()

        val program = input.split("Program: ")[1].split(",").map { it.toInt() }
        var regA = input.split("Register A: ")[1].split("\n")[0].toLong()
        var regB = input.split("Register B: ")[1].split("\n")[0].toLong()
        var regC = input.split("Register C: ")[1].split("\n")[0].toLong()

        val output = mutableListOf<Long>()

        var pointer = 0

        while (pointer < program.size) {
            val prevPointer = pointer

            val opcode = program[pointer]
            val operand = program[pointer + 1]

            val combo = when (operand) {
                4 -> regA
                5 -> regB
                6 -> regC
                else -> operand
            }.toDouble()

            when (opcode) {
                0 -> regA /= 2.0.pow(combo).toLong()
                1 -> regB = regB xor operand.toLong()
                2 -> regB = combo.toLong() % 8
                3 -> if (regA != 0L) pointer = operand
                4 -> regB = regB xor regC
                5 -> output.add(combo.toLong() % 8)
                6 -> regB = regA / 2.0.pow(combo).toLong()
                7 -> regC = regA / 2.0.pow(combo).toLong()
            }

            if (pointer == prevPointer) pointer += 2
        }

        val out = output.joinToString(",")

        println("\nAnswer: $out")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val input = data.trim()

        val program = input.split("Program: ")[1].split(",").map { it.toInt() }.reversed()

        val values = mutableListOf<Long>()
        values.add(0)

        // [1, 2, 3, 4] -> [[1], [1, 2], [1, 2, 3], [1, 2, 3, 4]]
        program.indices.map { program.subList(0, it + 1) }.forEach { lf ->
            val valuesCopy = values.toList()
            values.clear()

            valuesCopy.forEach {
                for (i in 0..7) {
                    val magic = it shl 3 or i.toLong()

                    var a = magic
                    var b = 0L
                    var c = 0L

                    val out = mutableListOf<Int>()

                    while (a != 0L) {
                        // Not sure if this works for other inputs.
                        b = a % 8
                        b = b xor 5
                        c = a shr b.toInt()
                        a /= 8
                        b = b xor 6
                        b = b xor c

                        out.add((b % 8).toInt())

                        if (out == lf.reversed()) {
                            values.add(magic)
                            break
                        }
                    }
                }
            }
        }

        println("\nAnswer: ${values.first()}")
    }
}
