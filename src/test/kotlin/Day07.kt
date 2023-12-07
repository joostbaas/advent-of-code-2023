import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.longs.shouldBeLessThan
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
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

    @Test
    fun scores() {
        assertTrue("32T3K".isPair())
        assertTrue("T55J5".isThreeOfAKind())
        assertTrue("KK677".isTwoPair())
        assertTrue("KTJJT".isTwoPair())
        assertTrue("QQQJA".isThreeOfAKind())

        "32222".highCard(cards) shouldBeGreaterThan "22222".highCard(cards)
        "23222".highCard(cards) shouldBeGreaterThan "22322".highCard(cards)
        "KK222".highCard(cards) shouldBeGreaterThan "KT222".highCard(cards)
        "2222A".highCard(cards) shouldBeGreaterThan "2222K".highCard(cards)
        "KTJJ2".handScore(cards) shouldBeLessThan "KK672".handScore(cards)

        "JKKK2".handScoreWithJokers() shouldBeLessThan "QQQQ2".handScoreWithJokers()
    }

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
