package y2023.day7

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day7Test {

    @Test
    fun parseCards() {
        val inputString = "32T3K 111"
        val parser = Parser()

        val outputHand = parser.toHand(inputString)
        val expectedHand = Hand(listOf(Card.THREE, Card.TWO, Card.T, Card.THREE, Card.K), 111)

        assertEquals(expectedHand, outputHand)
    }

    @Test
    fun sortHands() {
        val parser = Parser()
        val hands = listOf(
            "32T3K 1",
            "T55J5 1",
            "KK677 1",
            "KTJJT 1",
            "QQQJA 1"
        )

        val expectedRankedHands = listOf(
            "QQQJA 1",
            "T55J5 1",
            "KK677 1",
            "KTJJT 1",
            "32T3K 1"
        ).map { parser.toHand(it) }

        val parsedHands = hands.map { parser.toHand(it) }
        val rankedHands = parsedHands.sortedWith(HandComparator)

        assertEquals(expectedRankedHands, rankedHands)
    }

    @Test
    fun sortJokerHands() {
        val parser = Parser()
        val hands = listOf(
            "32T3K 1",
            "T55J5 1",
            "KK677 1",
            "KTJJT 1",
            "QQQJA 1"
        )

        val expectedRankedHands = listOf(
            "KTJJT 1",
            "QQQJA 1",
            "T55J5 1",
            "KK677 1",
            "32T3K 1"
        ).map { parser.toHand(it) }

        val parsedHands = hands.map { parser.toHand(it) }
        val rankedHands = parsedHands.sortedWith(JokerHandComparator)

        assertEquals(expectedRankedHands, rankedHands)
    }

}