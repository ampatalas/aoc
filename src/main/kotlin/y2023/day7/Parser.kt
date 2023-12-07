package y2023.day7

class Parser {
    fun toHand(string: String): Hand {
        val split = string.split(" ")
        val cards = mutableListOf<Card>()
        for (char in split[0].toCharArray()) {
            cards.add(Card.from(char))
        }
        return Hand(cards, split[1].toInt())
    }
}