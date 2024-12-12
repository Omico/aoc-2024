@file:JvmName("Day02")

import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return parseSafeLevelsCount(input, 0)
    }

    fun part2(input: List<String>): Int {
        return parseSafeLevelsCount(input, 1)
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun parseSafeLevelsCount(input: List<String>, allowedUnsafeLevelCount: Int): Int =
    input.count { line -> line.split(' ').map(String::toInt).isSafe(allowedUnsafeLevelCount) }

private fun List<Int>.isSafe(allowedUnsafeLevelCount: Int = 0): Boolean {
    if (allowedUnsafeLevelCount < 0) return false
    val isIncreasing = this[0] > this[1]
    for (index in 1 until size) {
        val previous = this[index - 1]
        val current = this[index]
        if (previous > current != isIncreasing || isOutOfRange(previous, current)) {
            if (allowedUnsafeLevelCount <= 0) return false
            return removeIndices(index - 1).isSafe(allowedUnsafeLevelCount - 1) ||
                removeIndices(index).isSafe(allowedUnsafeLevelCount - 1) ||
                (index == 2 && removeIndices(0).isSafe(allowedUnsafeLevelCount - 1))
        }
    }
    return true
}

private fun isOutOfRange(previous: Int, current: Int): Boolean = abs(previous - current) !in 1..3

private fun <T> Iterable<T>.removeIndices(vararg indices: Int): List<T> =
    filterIndexed { index, _ -> index !in indices }
