import y2023.day8.Day8

fun main(args: Array<String>) {
    println("Hello World!")

    val lines = object {}.javaClass.getResourceAsStream("day8.txt")!!.bufferedReader().readLines()
    val day8 = Day8()
    val desertMap = day8.parse(lines)
    val numberOfSteps = day8.getToTheEnd(desertMap.first, desertMap.second)

    println(numberOfSteps)

    println("Program arguments: ${args.joinToString()}")
}