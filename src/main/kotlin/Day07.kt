
fun String.isFiveOfAKind(): Boolean = cardCounts().maxOf { it.value } == 5
fun String.isFourOfAKind(): Boolean = cardCounts().maxOf { it.value } == 4
fun String.isFullHouse(): Boolean = cardCounts().values.sorted() == listOf(2, 3)
fun String.isThreeOfAKind(): Boolean = cardCounts().maxOf { it.value } == 3
fun String.isTwoPair(): Boolean = cardCounts().values.sorted() == listOf(1, 2, 2)
fun String.isPair(): Boolean = cardCounts().maxOf { it.value } == 2

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

val cards = listOf( //A22 -> 1200 vs KQ5 -> 1100+100+5 -> 1205
    'A', 'K', 'Q', 'J', 'T',
    '9', '8',
    '7', '6', '5', '4', '3', '2')

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
        hand.handScore(cards) to bid.toInt()
    }.sortedBy {
        it.first
    }
    return sortedHands.foldIndexed(0) { index, acc, (_, bid) ->
        acc + ((index+1) * bid)
    }
}

fun day07Part2(input: List<String>): Int {
    val sortedHands = input.map {
        it.split(" ")
    }.map { (hand, bid) ->
        hand.handScoreWithJokers() to bid.toInt()
    }.sortedBy {
        it.first
    }
    return sortedHands.foldIndexed(0) { index, acc, (_, bid) ->
        acc + ((index+1) * bid)
    }
}
