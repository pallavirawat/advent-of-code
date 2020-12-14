package d13

import Reader
import java.math.BigDecimal

fun main() {
    val input = Reader.read("13.txt") { it }
    val icanLeaveAt = input[0].toInt()
    val busTimings = input[1].split(",").filter { it != "x" }.map { it.toInt() }

//    findMyLovelyBus(icanLeaveAt, busTimings)
    findOverlap(input)
}

fun findOverlap(input: List<String>) {
    val busTimings = input[1].replace("x", "-1").split(",").mapIndexed { index, s ->
        BigDecimal(index) to s.toBigDecimal()
    }
    var prev = busTimings[0]
    var step = prev.second
    var startState = prev.second
    for (i in 1 until busTimings.size) {
        if (busTimings[i].second != BigDecimal(-1)) {
            var foundMatch = false
            var multiplier = BigDecimal(1)
            while (!foundMatch) {
                val plausibleMeetingPoint = startState + step * multiplier
                println(
                    "plausible check for ${prev.second} ${busTimings[i].second} " +
                            "$multiplier $plausibleMeetingPoint $step $startState"
                )
                if ((plausibleMeetingPoint + busTimings[i].first) % busTimings[i].second == BigDecimal.ZERO) {
                    println("match found at $i for $prev $plausibleMeetingPoint")
                    step *= busTimings[i].second
                    startState = plausibleMeetingPoint + step
                    prev = busTimings[i]
                    foundMatch = true
                }
                multiplier++

            }
        }
    }
}

fun checkIfAllOtherBusesMeet(
    busTimings: List<Pair<BigDecimal, BigDecimal>>,
    plausibleMeetingPoint: BigDecimal
): Boolean {
    return busTimings.filter { it.second != BigDecimal(-1) }.all { (index, bid) ->
        ((plausibleMeetingPoint + index) % bid) == BigDecimal.ZERO
    }
}

fun findMyLovelyBus(icanLeaveAt: Int, busTimings: List<Int>) {
    val maxOrNull = busTimings.map {
        val mod = icanLeaveAt % it
        it to it - mod
    }.minByOrNull { it.second }

    maxOrNull?.let {
        println("this is mah bus ${it.first} ${it.first * it.second}")
    }
}
