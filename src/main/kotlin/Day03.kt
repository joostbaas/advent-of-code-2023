import kotlin.math.abs

data class PartsGrid (
    val points: Map<Point, Char>,
) {
    private val symbols: Set<Point> = // startingPoint -> value
        points.filter { (_, char) ->
            when  {
                char == '.' -> false
                char.isDigit() -> false
                else -> true
            }
        }.keys

    fun hasSymbolAdjacent(point:Point) =
        symbolsAdjacent(point).isNotEmpty()

    fun symbolsAdjacent(point:Point) =
        symbols.filter { (x,y) ->
            abs(point.x - x) <= 1 &&
                    abs(point.y - y) <= 1 }
}



fun day03Part1(input: List<String>): Int {
    val symbolGrid = input.flatMapIndexed { lineNumber, line ->
        line.mapIndexed { position, char ->
            Point(position, lineNumber) to char
        }
    }.toMap().let {
        PartsGrid(it)
    }
    val numberRegex = Regex("""(\d+)\D""")
    val ints = input.flatMapIndexed { lineNumber, line ->
        numberRegex.findAll(line).map {matchResult ->
            val (number, range) = matchResult.groups[1]!!
            val numberPoints = range.map { Point(it, lineNumber) }
            if (numberPoints.any {numberPoint ->
                    symbolGrid.hasSymbolAdjacent(numberPoint)
                }) {
                number.toInt()
            } else 0
        }
    }
    return ints.sum()
}

fun day03Part2(input: List<String>): Int {
    val potentialGears = input.flatMapIndexed { lineNumber, line ->
        line.mapIndexed { position, char ->
            if (char == '*')
                Point(position, lineNumber) to char
            else null
        }.filterNotNull()
    }.toMap().let {
        PartsGrid(it)
    }

    val numberRegex = Regex("""(\d+)\D""")
    val allPotentialGearsWithNumber: List<Pair<Point, Int>> = input.flatMapIndexed { lineNumber, line ->
        numberRegex.findAll(line).flatMap {matchResult ->
            val (number, range) = matchResult.groups[1]!!
            val numberPoints = range.map { Point(it, lineNumber) }
            val potentialGearsWithNumber: List<Pair<Point, Int>> = numberPoints.flatMap { numberPoint ->
                potentialGears.symbolsAdjacent(numberPoint)
            }.toSet().map { symbol -> symbol to number.toInt() }
            potentialGearsWithNumber
        }
    }
    return allPotentialGearsWithNumber.groupBy({it.first}, {it.second}).filterValues {
        it.size == 2
    }.values.sumOf { (n1, n2) -> n1*n2 }
}
