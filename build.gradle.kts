import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

plugins {
    kotlin("jvm") version "2.1.0"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}

val generateTemplateFilesForNewDays by tasks.registering {
    doLast {
        val unlockedZoneId = ZoneId.of("UTC-5")
        val currentDateTime = ZonedDateTime.now(unlockedZoneId)
        val decemberFirst = ZonedDateTime.of(2024, 12, 1, 0, 0, 0, 0, unlockedZoneId)
        val daysBetween = ChronoUnit.DAYS.between(decemberFirst, currentDateTime) + 1
        val dayIds = (1..daysBetween).map { index -> index.toString().padStart(2, '0') }
        fun File.createIfNotExists(content: () -> String = { "" }) {
            if (exists()) return
            writeText(content())
        }
        dayIds.forEach { dayId ->
            val name = "Day$dayId"
            file("src/$name.kt").createIfNotExists {
                """
                |@file:JvmName("$name")
                |
                |fun main() {
                |    fun part1(input: List<String>) : Int {
                |        return 0
                |    }
                |
                |    fun part2(input: List<String>) : Int {
                |        return 0
                |    }
                |
                |    val testInput = readInput("${name}_test")
                |    check(part1(testInput) == 0)
                |    check(part2(testInput) == 0)
                |
                |    val input = readInput("$name")
                |    part1(input).println()
                |    part2(input).println()
                |}
                """.trimMargin()
            }
            file("src/$name.txt").createIfNotExists()
            file("src/${name}_test.txt").createIfNotExists()
        }
    }
}

tasks.prepareKotlinBuildScriptModel {
    dependsOn(generateTemplateFilesForNewDays)
}
