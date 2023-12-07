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

    fun jokerType(): HandType {
        var bestType = HandType.HIGH_CARD
        val replaceTypes = Card.entries.filterNot { it == Card.J }
        for (cardType in replaceTypes) {
            val newHand = Hand(cards.map { if (it == Card.J) cardType else it }, bet)
            val type = newHand.type()
            if (type.rank > bestType.rank) bestType = type
        }
        return bestType
    }

    override fun equals(other: Any?): Boolean {
        val otherHand = other as Hand
        return otherHand.cards.size == this.cards.size && otherHand.cards.containsAll(this.cards)
    }

    override fun toString(): String {
        return cards.toString()
    }
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

val JokerHandComparator = Comparator<Hand> { first, second ->
    if (first.jokerType().compareTo(second.jokerType()) == 0) {
        first.cards.forEachIndexed() { index, card ->
            val editedStrengthFirst = if(card == Card.J) 1 else card.strength
            val editedStrengthSecond = if(second.cards[index] == Card.J) 1 else second.cards[index].strength
            val comparedStrength = editedStrengthSecond.compareTo(editedStrengthFirst)
            if (comparedStrength != 0) return@Comparator comparedStrength
        }
    }
    return@Comparator first.jokerType().compareTo(second.jokerType())
}