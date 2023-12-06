import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03 {

    private val testInput = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """.trimIndent().lines()
    private val input = readInput("Day2023_03")

    @Test
    fun `part 1`() {
        day03Part1(testInput) shouldBe 4361
        day03Part1(input) shouldBe  556057
    }

    @Test
    fun `part 2`() {
        day03Part2(testInput) shouldBe 467835
        day03Part2(input) shouldBe 82824352
    }
}