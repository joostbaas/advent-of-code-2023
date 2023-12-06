fun day06Part1(input: List<String>): Long {
    val (times, distances) = input.map { it.split(" ").filter { it.isNotBlank() }.drop(1).map { it.toLong() } }
    return times.zip(distances).map { (time, recordDistance) ->
        getNumberOfWaysToWin(time, recordDistance)
    }.fold(1) { acc, i ->
        acc * i
    }
}

private fun getNumberOfWaysToWin(time: Long, recordDistance: Long): Int =
    (0..time).count { holdTime ->
        val speed = holdTime
        val distanceTravelled = (time - holdTime) * speed
        distanceTravelled > recordDistance
    }

fun day06Part2(input: List<String>): Int {
    val (time, distance) = input.map { it.split(" ").filter { it.isNotBlank() }.drop(1).joinToString(separator = "").toLong() }
    return getNumberOfWaysToWin(time, distance)
}
