package y2023.day8


data class Node(val nodeName: String, val toLeft: String, val toRight: String)
class Day8 {

    fun parse(input: List<String>): Pair<String, List<Node>> {
        val instruction = input.first()
        val nodes = mutableListOf<Node>()
        for (line in input.subList(1, input.size)) {
            if (line.isNotBlank()) {
                val splitLine = line.split(" = ")
                val nodeName = splitLine[0]
                val values = splitLine[1].substring(1, splitLine[1].length - 1).split(", ")
                val left = values[0]
                val right = values[1]
                nodes.add(Node(nodeName, left, right))
            }
        }
        return Pair(instruction, nodes.toList())
    }

    fun getToTheEnd(instructions: String, nodes: List<Node>): Int {
        var numberOfSteps = 0
        var currentNode = nodes.find { it.nodeName == "AAA" }

        while (currentNode!!.nodeName != "ZZZ") {
            val instruction = instructions[numberOfSteps % instructions.length]
            val nextNode = nextNode(instruction, currentNode, nodes)
            currentNode = nextNode
            numberOfSteps++
        }

        return numberOfSteps
    }

    private fun nextNode(instruction: Char, node: Node, allNodes: List<Node>): Node {
        val nextNodeName = if (instruction == 'L') node.toLeft else node.toRight
        return allNodes.find { it.nodeName == nextNodeName }!!
    }

}