fun main() {
    val input = Reader.read("10.txt") { it.toInt() }

    findShockingVoltages(input.sorted())
}

fun findShockingVoltages(input: List<Int>) {
    val voltDiff = mutableListOf<Int>()
    voltDiff.add(input[0])

    for (i in 1 until input.size){
        voltDiff.add(input[i] - input[i-1])
    }

    println(input)
    val onesies = voltDiff.count { it == 1 }
    val threezies = voltDiff.count { it == 3 } +1
    println("count is  $onesies $threezies ${onesies* threezies}")
}

