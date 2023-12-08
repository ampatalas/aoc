package y2023.day8

import kotlin.test.Test
import kotlin.test.assertEquals

class Day8Test {

    @Test
    fun readInstructions() {
        val input = "RL\n" +
                "\n" +
                "AAA = (BBB, CCC)\n" +
                "BBB = (DDD, EEE)\n" +
                "CCC = (ZZZ, GGG)\n" +
                "DDD = (DDD, DDD)\n" +
                "EEE = (EEE, EEE)\n" +
                "GGG = (GGG, GGG)\n" +
                "ZZZ = (ZZZ, ZZZ)"
        val parser = Day8()
        val expectedMap = Pair("RL", listOf(
            Node("AAA", "BBB", "CCC"),
            Node("BBB", "DDD", "EEE"),
            Node("CCC", "ZZZ", "GGG"),
            Node("DDD", "DDD", "DDD"),
            Node("EEE", "EEE", "EEE"),
            Node("GGG", "GGG", "GGG"),
            Node("ZZZ", "ZZZ", "ZZZ"))
        )
        val map = parser.parse(input.split("\n"))
        assertEquals(expectedMap, map)

        val steps = parser.getToTheEnd(map.first, map.second)
        assertEquals(2, steps)
    }

    @Test
    fun readInstructionsWithMoreSteps() {
        val input = "LLR\n" +
                "\n" +
                "AAA = (BBB, BBB)\n" +
                "BBB = (AAA, ZZZ)\n" +
                "ZZZ = (ZZZ, ZZZ)"
        val parser = Day8()
        val map = parser.parse(input.split("\n"))

        val steps = parser.getToTheEnd(map.first, map.second)
        assertEquals(6, steps)
    }

}