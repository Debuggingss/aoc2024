package dev.d5b.aoc2024.days

object Day4 : Day(4) {

    // Match without consuming characters
    private val reg = Regex("(?=(XMAS|SAMX))")

    override fun part1(debug: Boolean) {
        val data = getInput(debug)

        val board = data
            .trim()
            .split("\n")

        var count = 0

        // Horizontal
        count += board.sumOf {
            reg.findAll(it).toList().size
        }

        // Vertical
        val boardRotated = List(board.size) { index ->
            board[0].indices.map {
                board[it][index]
            }.joinToString("")
        }

        count += boardRotated.sumOf {
            reg.findAll(it).toList().size
        }

        // Diagonal 1
        val boardDiagonal = rotateBoard(board.map { it.map { c -> c.toString() } })
            .map { it.joinToString("") }

        count += boardDiagonal.sumOf {
            reg.findAll(it).toList().size
        }

        // Diagonal 2
        val boardDiagonal2 = rotateBoard(board.map { it.map { c -> c.toString() } }, true)
            .map { it.joinToString("") }

        count += boardDiagonal2.sumOf {
            reg.findAll(it).toList().size
        }

        println("\nAnswer: $count")
    }

    override fun part2(debug: Boolean) {
        val data = getInput(debug)

        val board = data
            .trim()
            .split("\n")

        var count = 0

        board.forEachIndexed { lnIdx, ln ->
            ln.toList().forEachIndexed charLoop@{ colIdx, c ->
                if (c != 'A') return@charLoop
                val prevLine = board.getOrNull(lnIdx - 1) ?: return@charLoop
                val nextLine = board.getOrNull(lnIdx + 1) ?: return@charLoop

                val topLeft = prevLine.getOrNull(colIdx - 1) ?: return@charLoop
                val topRight = prevLine.getOrNull(colIdx + 1) ?: return@charLoop
                val bottomLeft = nextLine.getOrNull(colIdx - 1) ?: return@charLoop
                val bottomRight = nextLine.getOrNull(colIdx + 1) ?: return@charLoop

                if (
                    (topLeft == 'M' && bottomRight == 'S' || topLeft == 'S' && bottomRight == 'M') &&
                    (topRight == 'M' && bottomLeft == 'S' || topRight == 'S' && bottomLeft == 'M')
                ) count++
            }
        }

        println("\nAnswer: $count")
    }

    private fun rotateBoard(board: List<List<String>>, backwards: Boolean = false): List<List<String>> {
        val size = board.size
        val rotated = mutableListOf<MutableList<String>>()
        var counter = 0

        while (counter < 2 * size - 1) {
            val list = mutableListOf<String>()

            for (i in 0 until size) {
                for (j in 0 until size) {
                    if (
                        (i + j == counter && !backwards) ||
                        ((j - i == counter - (size - 1) && backwards))
                    ) {
                        list.add(board[i][j])
                    }
                }
            }

            list.reverse()
            rotated.add(list)
            counter++
        }

        return rotated
    }
}
