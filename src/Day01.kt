@file:JvmName("Day01")

import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val (leftList, rightList) = parseInput(input)
        return leftList.sorted().zip(rightList.sorted()).sumOf { (a, b) -> abs(a - b) }
    }

    fun part2(input: List<String>): Int {
        val (leftList, rightList) = parseInput(input)
        return leftList.sumOf { number -> number * rightList.count { it == number } }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
    val (leftList, rightList) = input.map(String::parseIntPair).unzip()
    return leftList to rightList
}

private fun String.parseIntPair(): Pair<Int, Int> {
    val (a, b) = split("\\s+".toRegex())
    return a.toInt() to b.toInt()
}
