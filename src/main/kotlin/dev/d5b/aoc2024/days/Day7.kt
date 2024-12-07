package dev.d5b.aoc2024.days

import java.math.BigInteger
import kotlin.math.pow

object Day7 : Day(7) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val emptyResults = data
            .trim()
            .split("\n")
            .map { it.split(": ") }
            .map { line ->
                val res = line[0].toBigInteger()
                val values = line[1].split(" ").map { it.toInt() }
                Pair(res, values)
            }

        var sum = 0.toBigInteger()

        emptyResults.forEach resultLoop@{ pair ->
            val result = pair.first
            val values = pair.second
            val slots = values.size - 1

            val max = (2F.pow(slots) - 1).toInt()

            for (i in 0..max) {
                val binary = i.toUInt().toString(2)
                    .padStart(slots, '0')
                    .replace("0", "+")
                    .replace("1", "*")

                val exp = values.mapIndexed { index, value ->
                    if (index < binary.length) value.toString() + binary[index]
                    else value.toString()
                }.joinToString("")

                val res = calculate(exp)

                if (res == result) {
                    sum += result
                    return@resultLoop
                }
            }
        }

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val emptyResults = data
            .trim()
            .split("\n")
            .map { it.split(": ") }
            .map { line ->
                val res = line[0].toBigInteger()
                val values = line[1].split(" ").map { it.toInt() }
                Pair(res, values)
            }

        var sum = 0.toBigInteger()

        emptyResults.forEach resultLoop@{ pair ->
            val result = pair.first
            val values = pair.second
            val slots = values.size - 1

            val max = (3F.pow(slots) - 1).toInt()

            for (i in 0..max) {
                val binary = i.toUInt().toString(3)
                    .padStart(slots, '0')
                    .replace("0", "+")
                    .replace("1", "*")
                    .replace("2", "|")

                val exp = values.mapIndexed { index, value ->
                    if (index < binary.length) value.toString() + binary[index].toString()
                    else value.toString()
                }.joinToString("")

                val res = calculate(exp)

                if (res == result) {
                    sum += result
                    return@resultLoop
                }
            }
        }

        println("\nAnswer: $sum")
    }

    private fun calculate(expression: String): BigInteger {
        val split = expression.split(Regex("[*+|]"))

        var num = split[0].toBigInteger()

        Regex("([*+|])(\\d+)").findAll(expression.substring(split[0].length - 1)).forEach {
            val (op, n) = it.destructured

            when (op) {
                "+" -> num += n.toBigInteger()
                "*" -> num *= n.toBigInteger()
                "|" -> num = (num.toString() + n).toBigInteger()
            }
        }

        return num
    }
}
