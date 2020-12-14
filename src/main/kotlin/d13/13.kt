package d13

import java.math.BigDecimal

fun main() {
    val input = Reader.read("13.txt") { it }
    val icanLeaveAt = input[0].toInt()
    val busTimings = input[1].split(",").filter { it!="x" }.map { it.toInt() }

//    findMyLovelyBus(icanLeaveAt, busTimings)
    findOverlap(input)
}

fun findOverlap(input: List<String>) {
    val icanLeaveAt = input[0].toBigDecimal()
    val busTimings = input[1].replace("x","-1").split(",").mapIndexed {
        index, s -> BigDecimal(index) to s.toBigDecimal()
    }
    val lessFrequentBus = busTimings.maxByOrNull { it.second }
//    println(lessFrequentBus)
//    939
//    7,13,x,x,59,x,31,19
    lessFrequentBus?.let { (index, bid) ->
        var foundMatch = false
        var multiplier = BigDecimal(106723585912)
        while (!foundMatch){
            val plausibleMeetingPoint = bid * multiplier - index
            println("plausible check for $bid $multiplier " +
                    " $index " +
                    "$plausibleMeetingPoint")
            if(checkIfAllOtherBusesMeet(busTimings, plausibleMeetingPoint)){
                foundMatch = true
                println("matching point where all the buses meet and crash and party $plausibleMeetingPoint")
            }
//            if(plausibleMeetingPoint> BigDecimal(1068781)){
//                foundMatch=true
//            }

            multiplier++
        }
    }
}

fun checkIfAllOtherBusesMeet(busTimings: List<Pair<BigDecimal, BigDecimal>>, plausibleMeetingPoint: BigDecimal): Boolean {
    return busTimings.filter { it.second!= BigDecimal(-1) }.all { (index, bid) ->
        ((plausibleMeetingPoint+index)%bid) == BigDecimal.ZERO
    }
}

fun findMyLovelyBus(icanLeaveAt: Int, busTimings: List<Int>) {
    val maxOrNull = busTimings.map {
        val mod = icanLeaveAt % it
        it to it - mod
    }.minByOrNull { it.second }

    maxOrNull?.let {
        println("this is mah bus ${it.first} ${it.first*it.second}")
    }
}
