import kotlin.math.max

data class Game(
    val id: Int,
    val setsOfCubes: List<Bag>,
) {
    fun wouldBePossibleWithLessCubes() = setsOfCubes.all { it.wouldBePossibleWithLessCubes() }
}

fun String.parseGame(): Game {
    val regex = "Game ([0-9]+): (.*)".toRegex()
    val matchResult = regex.find(this)!!
    val (id, bagsString) = matchResult.destructured
    val bags: List<Bag> = bagsString.split("; ").map { bag ->
        bag.split(", ").associate {
            val (number, colour) = it.split(" ")
            colour to number.toInt()
        }.let {
            Bag(it["red"] ?: 0, it["blue"] ?: 0, it["green"] ?: 0)
        }
    }
    return Game(id.toInt(), bags)
}

data class Bag(
    val red: Int = 0,
    val blue: Int = 0,
    val green: Int = 0,
) {
    fun wouldBePossibleWithLessCubes() =
        (red <= 12 && green <= 13 && blue <= 14)
}

fun day02Part1(input: List<String>): Int = input.parseGames()
    .filter { it.wouldBePossibleWithLessCubes() }
    .sumOf { it.id }

fun day02Part2(input: List<String>): Int =
    input.parseGames().sumOf { game ->
        val (maxRed, maxBlue, maxGreen) = game.setsOfCubes.fold(Bag()) { acc, bag ->
            acc.copy(
                red = max(acc.red, bag.red),
                blue = max(acc.blue, bag.blue),
                green = max(acc.green, bag.green),
            )
        }
        maxRed * maxBlue * maxGreen
    }


private fun List<String>.parseGames() = map { it.parseGame() }

