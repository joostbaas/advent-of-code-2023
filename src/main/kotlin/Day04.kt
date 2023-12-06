fun day04Part1(input: List<String>): Int =
    input.sumOf { card ->
        1.shl(card.score() - 1)
    }

val cardRegex = """Card\s+(\d+): (.*)""".toRegex()
private fun String.score(): Int =
    idAndScore().second

private fun String.idAndScore(): Pair<Int, Int> =
    cardRegex.find(this)!!.destructured.let { (cardId, numbersPart) ->
        val (winning, mine) = numbersPart.split("""|""").map {
            it.trim().split(" ").filter { it.isNotBlank() }
                .map { it.toInt() }.toSet()
        }
        cardId.toInt() to winning.intersect(mine).size
    }

fun day04Part2(input: List<String>): Int {
    val idsAndScore: Map<Int, Int> = input.associate { it.idAndScore() }

    fun addCardAndDependencies(cardId: Int) : List<Int> {
        val scoreForCard: Int = idsAndScore[cardId] ?: 0
        return if (scoreForCard == 0) listOf(cardId)
        else {
            listOf(cardId) + (1..scoreForCard).flatMap { nextCardOffset -> addCardAndDependencies(cardId + nextCardOffset)}
        }
    }

    val resultingDeck: MutableList<Int> = mutableListOf()
    for(card in idsAndScore.keys) {
        resultingDeck.addAll(addCardAndDependencies(card))
    }
    return resultingDeck.size
}
