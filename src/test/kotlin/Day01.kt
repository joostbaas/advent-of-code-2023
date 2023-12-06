import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01 {

    private val testInput1 = listOf(
        "1abc2",
        "pqr3stu8vwx",
        "a1b2c3d4e5f",
        "treb7uchet",
    )
    private val testInput2 = listOf(
        "two1nine",
        "eightwothree",
        "abcone2threexyz",
        "xtwone3four",
        "4nineeightseven2",
        "zoneight234",
        "7pqrstsixteen",
    )
    private val input = readInput("Day2023_01")

    @Test
    fun `part 1`() {
        day01Part1(testInput1) shouldBe 142
        day01Part1(input) shouldBe 54304
    }

    @Test
    fun `part 2`() {
        day01Part2(listOf("six____eightwo")) shouldBe 62

        day01Part2(testInput2) shouldBe 281
        day01Part2(input) shouldBe 54418
    }
}