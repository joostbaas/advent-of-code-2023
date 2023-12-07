import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class Day07 {

    private val testInput = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent().lines()
    private val input = readInput("Day2023_07")

    @TestFactory
    fun `part 1`() =
        listOf(
            dynamicTest("test input") { day07Part1(testInput) shouldBe 6440 },
            dynamicTest("input") { day07Part1(input) shouldBe 248179786 }
        )

    @TestFactory
    fun `part 2`() =
        listOf(
            dynamicTest("test input") { day07Part2(testInput) shouldBe 5905 },
            dynamicTest("input") { day07Part2(input) shouldBe 247885995 })
}
