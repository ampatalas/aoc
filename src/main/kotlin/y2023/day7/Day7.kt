package y2023.day7

enum class HandType(val rank: Int) {
    FIVE_KIND(7),
    FOUR_KIND(6),
    FULL_HOUSE(5),
    THREE_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}

enum class Card(val strength: Int) {
    A(14),
    K(13),
    Q(12),
    J(11),
    T(10),
    NINE(9),
    EIGHT(8),
    SEVEN(7),
    SIX(6),
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2);

    companion object {
        fun from(char: Char): Card {
            return when (char) {
                'A' -> A
                'K' -> K
                'Q' -> Q
                'J' -> J
                'T' -> T
                '9' -> NINE
                '8' -> EIGHT
                '7' -> SEVEN
                '6' -> SIX
                '5' -> FIVE
                '4' -> FOUR
                '3' -> THREE
                else -> TWO
            }
        }
    }
}

class Hand(val cards: List<Card>, val bet: Int) {
    fun groups(): Map<Int, List<Card>> {
        return cards.groupBy { it.strength }
    }

    fun type(): HandType {
        val groups = groups()
        if (groups.size == 1) return HandType.FIVE_KIND
        if (groups.size == 2) {
            val biggest = groups.maxBy { entry -> entry.value.size }
            return if (biggest.value.size == 4) HandType.FOUR_KIND else HandType.FULL_HOUSE
        }
        if (groups.size == 3) {
            val biggest = groups.maxBy { entry -> entry.value.size }
            return if (biggest.value.size == 3) HandType.THREE_KIND else HandType.TWO_PAIR
        }
        if (groups.size == 4) return HandType.ONE_PAIR
        return HandType.HIGH_CARD
    }

    override fun equals(other: Any?): Boolean {
        val otherHand = other as Hand
        return otherHand.cards.size == this.cards.size && otherHand.cards.containsAll(this.cards)
    }

    override fun toString(): String {
        return cards.toString()
    }
}

fun compareGroups(first: Map<Int, List<Card>>, second: Map<Int, List<Card>>): Int {
    if (first.size != second.size) {
        return first.size.compareTo(second.size)
    }
    val biggestGroupInOneSize = first.maxBy { entry -> entry.value.size }
    val biggestGroupInTwoSize = second.maxBy { entry -> entry.value.size }
    if (biggestGroupInOneSize.value.size != biggestGroupInTwoSize.value.size) {
        return biggestGroupInTwoSize.value.size.compareTo(biggestGroupInOneSize.value.size)
    }
    if (biggestGroupInTwoSize.key.compareTo(biggestGroupInOneSize.key) != 0) {
        return biggestGroupInTwoSize.key.compareTo(biggestGroupInOneSize.key)
    }
    val reducedFirst = first.filterNot { it.key == biggestGroupInOneSize.key }
    val reducedSecond = second.filterNot { it.key == biggestGroupInTwoSize.key }
    return compareGroups(reducedFirst, reducedSecond)
}

val HandComparator = Comparator<Hand> { first, second ->
    if (first.type().compareTo(second.type()) == 0) {
        first.cards.forEachIndexed() { index, card ->
            val comparedStrength = second.cards[index].strength.compareTo(card.strength)
            if (comparedStrength != 0) return@Comparator comparedStrength
        }
    }
    return@Comparator first.type().compareTo(second.type())
}