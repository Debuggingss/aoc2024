package dev.d5b.aoc2024.days

import java.util.*

object Day9 : Day(9) {

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val rawDisk = data.trim()

        val disk = mutableListOf<Int>()

        rawDisk.chunked(2).forEachIndexed { fid, chunk ->
            val split = chunk
                .split("")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }

            for (i in 0 until split[0]) {
                disk.add(fid)
            }

            if (split.size > 1) {
                for (i in 0 until split[1]) {
                    disk.add(-1)
                }
            }
        }

        var prevLast = -1

        disk.forEachIndexed { index, i ->
            if (i != -1) return@forEachIndexed
            val last = disk.reversed().indexOfFirst { it != -1 }
            if (last == prevLast) return@forEachIndexed
            prevLast = last
            Collections.swap(disk, index, disk.size - last - 1)
        }

        Collections.swap(disk, disk.indexOfFirst { it == -1 }, disk.indexOfLast { it != -1 })

        val sum = disk
            .filter { it != -1 }
            .mapIndexed { index, i ->
                (index * i).toBigInteger()
            }
            .sumOf { it }

        println("\nAnswer: $sum")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val rawDisk = data.trim()

        val disk = mutableListOf<Int>()

        // start index, space
        val freeSpaces = TreeMap<Int, Int>()
        val blocks = TreeMap<Int, Int>()

        rawDisk.chunked(2).forEachIndexed { fid, chunk ->
            val split = chunk
                .split("")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }

            blocks[disk.size] = split[0]

            for (i in 0 until split[0]) {
                disk.add(fid)
            }

            if (split.size > 1) {
                freeSpaces[disk.size] = split[1]

                for (i in 0 until split[1]) {
                    disk.add(-1)
                }
            }
        }

        blocks.reversed().forEach { (idx, size) ->
            val free = freeSpaces
                .filter { it.value >= size }
                .map { it.key to it.value }
                .firstOrNull() ?: return@forEach

            // Credits to this comment https://www.reddit.com/r/adventofcode/comments/1ha71fr/comment/m16i80d/
            // Ensure free block is actually before current position
            if (free.first >= idx) return@forEach

            freeSpaces.remove(free.first)
            if (free.second - size != 0) {
                freeSpaces[free.first + size] = free.second - size
            }

            for (i in 0 until size) {
                Collections.swap(disk, idx + i, free.first + i)
            }
        }

        val sum = disk.mapIndexed { index, i ->
            if (i != -1) (index * i).toBigInteger()
            else (0).toBigInteger()
        }.sumOf { it }

        println("\nAnswer: $sum")
    }
}
