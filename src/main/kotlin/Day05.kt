import java.util.concurrent.Callable
import java.util.concurrent.Executors

data class SeedMap(val name: String, val rules: List<MappingRule>) {

    fun map(numberIn: Long): Long =
        rules.firstOrNull { it.matches(numberIn) }?.map(numberIn) ?: numberIn

}

data class MappingRule(
    val range: LongRange,
    val offset: Long,
) {
    fun map(numberIn: Long): Long = numberIn + offset

    fun matches(numberIn: Long) = range.contains(numberIn)
}

fun day05Part1(input: List<String>): Long {
    val seeds = input[0].split(" ").drop(1).map { it.toLong() }

    return seeds.minOf { resolveLocation(it, parseMaps(input)) }
}

private fun resolveLocation(seed: Long, seedMaps: List<SeedMap>): Long =
    seedMaps.fold(seed) { number, seedMap ->
        seedMap.map(number)
    }


private fun parseMaps(input: List<String>) =
    input.drop(1).filter { it.isNotBlank() }.let { maps ->
        maps.fold(emptyList<SeedMap>()) { acc, line ->
            if (line.endsWith(':')) {
                val mapName = line.takeWhile { it != ' ' }
                acc + SeedMap(mapName, emptyList())
            } else {
                val (destinationRangeStart, sourceRangeStart, rangeLength) = line.split(" ").map { it.toLong() }
                fun SeedMap.addToLastMappingRule(rule: MappingRule): SeedMap = copy(rules = rules + rule)
                val mappingRule = MappingRule(
                    range = sourceRangeStart until sourceRangeStart+rangeLength,
                    offset = destinationRangeStart - sourceRangeStart)
                val lastSeedMap = acc.last()
                acc.dropLast(1) + lastSeedMap.addToLastMappingRule(mappingRule)
            }
        }
    }

fun day05Part2(input: List<String>): Long {
    val seedMaps = parseMaps(input)
    val es = Executors.newFixedThreadPool(4)
    return input[0].split(" ").drop(1)
        .map { it.toLong() }
        .chunked(2)
        .map { (start, rangeLength) ->(start until (start + rangeLength)) }
        .sortedBy { it.last - it.first }
        .map {
            es.submit(Callable {
                println("new pair: ${it.first} to ${it.last}")
                it.minOf { seed ->
                    resolveLocation(seed, seedMaps)
                }
            })
        }.minOf { it.get() }
}
