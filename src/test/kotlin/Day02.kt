import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02 {

    private val testInput = listOf(
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",
    )
    private val input = readInput("Day2023_02")

    @Test
    fun `part 1`() {
        day02Part1(testInput) shouldBe 8
        day02Part1(input) shouldBe  2683
    }

    @Test
    fun `part 2`() {
        day02Part2(testInput) shouldBe 2286
        day02Part2(input) shouldBe 49710
    }
}