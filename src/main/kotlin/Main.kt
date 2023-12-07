import y2023.day7.Hand
import y2023.day7.JokerHandComparator
import y2023.day7.Parser

fun main(args: Array<String>) {
    println("Hello World!")

    val handsAsLines = object {}.javaClass.getResourceAsStream("day7.txt")!!.bufferedReader().readLines()
    val parser = Parser()

    val hands = handsAsLines.map { parser.toHand(it) }.sortedWith(JokerHandComparator)
    var sum = 0

    hands.forEachIndexed { index: Int, hand: Hand ->
        val rank = hands.size - index
        sum += hand.bet * rank
    }

    println(sum)

    println("Program arguments: ${args.joinToString()}")
}