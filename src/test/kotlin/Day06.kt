import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class Day06 {

    private val testInput = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent().lines()
    private val input = readInput("Day2023_06")

    @TestFactory
    fun `part 1`() =
        listOf(
            dynamicTest("test input") { day06Part1(testInput) shouldBe 288 },
            dynamicTest("input") { day06Part1(input) shouldBe 220320 }
        )

    @TestFactory
    fun `part 2`() =
        listOf(
            dynamicTest("test input") { day06Part2(testInput) shouldBe 71503 },
            dynamicTest("input") { day06Part2(input) shouldBe 34454850 }
        )
}
