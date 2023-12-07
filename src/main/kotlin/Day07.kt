fun String.isFiveOfAKind(): Boolean = cardCounts().maxOf { it.value } == 5
fun String.isFourOfAKind(): Boolean = cardCounts().maxOf { it.value } == 4
fun String.isFullHouse(): Boolean = cardCounts().values.sorted() == listOf(2, 3)
fun String.isThreeOfAKind(): Boolean = cardCounts().maxOf { it.value } == 3
fun String.isTwoPair(): Boolean = cardCounts().values.sorted() == listOf(1, 2, 2)
fun String.isPair(): Boolean = cardCounts().maxOf { it.value } == 2

private class HighCardComparator(val cards: List<Char>) : Comparator<String> {
    override fun compare(o1: String, o2: String): Int =
        when {
            o1.isEmpty() && o2.isEmpty() -> 0
            o1.isEmpty() && o2.isNotEmpty() -> -1
            o1.isNotEmpty() && o2.isEmpty() -> 1
            else -> {
                val firstCharCompared = cards.indexOf(o2[0]).compareTo(cards.indexOf(o1[0]))
                if (firstCharCompared == 0) {
                    this.compare(o1.drop(1), o2.drop(1))
                } else firstCharCompared
            }
        }
}

private object HandSetComparator : Comparator<String> {

    private fun comparator(fromBool: (String) -> Boolean): Comparator<String> =
        Comparator { o1, o2 ->
            val fromBool1 = fromBool(o1)
            val fromBool2 = fromBool(o2)
            when {
                fromBool1 == fromBool2 -> 0
                fromBool1 -> 1
                else -> -1
            }
        }

    override fun compare(hand1: String, hand2: String): Int =
        comparator(String::isFiveOfAKind)
            .thenComparing(comparator(String::isFourOfAKind))
            .thenComparing(comparator(String::isFullHouse))
            .thenComparing(comparator(String::isThreeOfAKind))
            .thenComparing(comparator(String::isTwoPair))
            .thenComparing(comparator(String::isPair))
            .compare(hand1, hand2)
}

val cards = listOf( //A22 -> 1200 vs KQ5 -> 1100+100+5 -> 1205
    'A', 'K', 'Q', 'J', 'T',
    '9', '8',
    '7', '6', '5', '4', '3', '2'
)

private val NormalHandComparator = HandSetComparator.thenComparing(HighCardComparator(cards))

private object HandWithJokerComparator : Comparator<String> {

    private fun String.generateAlternatives(): List<String> =
        when {
            this.isEmpty() -> emptyList()
            this[0] == 'J' -> cardsWithoutJoker
                .flatMap { newChar ->
                    (newChar + this.drop(1)).generateAlternatives()
                }
            else -> listOf(this) + this.drop(1).generateAlternatives().map {
                this[0] + it
            }
        }

    override fun compare(hand1: String, hand2: String): Int {
        val hand1Alternatives: List<String> = hand1.generateAlternatives()
        val hand2Alternatives: List<String> = hand2.generateAlternatives()
        val bestHand1Alternative = hand1Alternatives.sortedWith(HandSetComparator).last()
        val bestHand2Alternative = hand2Alternatives.sortedWith(HandSetComparator).last()
        return HandSetComparator.thenComparing(HighCardComparator(cardsWithJokerLast))
            .compare(bestHand1Alternative, bestHand2Alternative)
    }
}

private fun String.cardCounts() =
    this.toSet()
        .associateWith {
            this.count { countC -> it == countC }
        }

fun String.handScoreWithJokers(): Long {
    fun String.generateAlternatives(): List<String> =
        when {
            this.isEmpty() -> emptyList()
            this[0] == 'J' -> cardsWithoutJoker
                .flatMap { newChar ->
                    (newChar + this.drop(1)).generateAlternatives()
                }

            else -> listOf(this) + this.drop(1).generateAlternatives().map {
                this[0] + it
            }
        }
    return this.generateAlternatives().maxOf { it.handSetScore() } + highCard(cardsWithJokerLast)
}

fun String.handSetScore(): Long =
    when {
        isFiveOfAKind() -> 900000000000
        isFourOfAKind() -> 800000000000
        isFullHouse() -> 700000000000
        isThreeOfAKind() -> 600000000000
        isTwoPair() -> 500000000000
        isPair() -> 400000000000
        else -> 0L
    }

fun String.handScore(cardsInOrder: List<Char>): Long =
    handSetScore() + highCard(cardsInOrder)



val cardsWithoutJoker = cards.filterNot { it == 'J' }
val cardsWithJokerLast = cardsWithoutJoker + 'J'

fun String.highCard(cardsInOrder: List<Char>): Long {
    val scores = cardsInOrder
        .reversed()
        .mapIndexed { index, c ->
            c to index
        }.toMap()
    return this.reversed().fold("") { acc, c ->
        scores[c]!!.toString().padStart(2, '0') + acc
    }.toLong()
}

fun day07Part1(input: List<String>): Long {
    val sortedHands = input.map {
        it.split(" ")
    }.map { (hand, bid) ->
        hand to bid.toInt()
    }.sortedWith { o1, o2 ->
        NormalHandComparator.compare(o1.first, o2.first)
    }

    return sortedHands.foldIndexed(0) { index, acc, (_, bid) ->
        acc + ((index + 1) * bid)
    }
}

fun day07Part2(input: List<String>): Int {
    val sortedHands = input.map {
        it.split(" ")
    }.map { (hand, bid) ->
        hand to bid.toInt()
    }.sortedWith { o1, o2 ->
        HandWithJokerComparator.compare(o1.first, o2.first)
    }
    return sortedHands.foldIndexed(0) { index, acc, (_, bid) ->
        acc + ((index + 1) * bid)
    }
}
