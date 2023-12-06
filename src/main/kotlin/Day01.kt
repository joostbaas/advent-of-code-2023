
fun day01Part1(input: List<String>): Int {
    val digitPairs: List<Int> = input.map { line ->
        calculate(line)
    }
    return digitPairs.sum()
}
/*
mv Day202301.kt Day01.kt
mv Day202302.kt Day02.kt
mv Day202303.kt Day03.kt
mv Day202304.kt Day04.kt
mv Day202305.kt Day05.kt
mv Day202306.kt Day06.kt

 */

fun day01Part2(input: List<String>): Int {
    val numbers = mapOf(
        "zero" to 0,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )
    val everythingThatCounts = numbers.keys + numbers.values.map { it.toString() }
    val digitPairs: List<Int> = input.map { line ->

        val firstNumber = line.findAnyOf(everythingThatCounts)!!.let {(_, str) ->
            if (str.length == 1) str[0].digitToInt()
            else numbers[str]
        }
        val lastNumber = line.findLastAnyOf(everythingThatCounts)!!.let { (_, str) ->
            if (str.length == 1) str[0].digitToInt()
            else numbers[str]
        }

        firstNumber!! * 10 + lastNumber!!
    }
    return digitPairs.sum()
}

private fun calculate(line: String): Int {
    val firstDigit = line.first { it.isDigit() }
    val lastDigit = line.last { it.isDigit() }
    return (10 * firstDigit.digitToInt()) + lastDigit.digitToInt()
}

