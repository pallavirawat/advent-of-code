import java.math.BigDecimal
import kotlin.math.pow

fun main() {
    val input = Reader.read("10.txt") { it.toBigDecimal() }

//    findShockingVoltages(input.sorted())
    findArrangementsPossible(input.sorted()) //the answer is 56693912375296 having 12 5 2

}

// this one basically just makes use of the fact that how many continuous 1s will have how many combinations
// like for diff like 1 1 1 3 : we will have 4 possible outputs
// for diff like 1 1 3 : we will have 2 possible outputs
// for diff like 1 1 1 1 3 : we will have 7 possible outputs
// the strong assumption here is that the diff is always 1 and 3.
// and also for 5 continuous 1s the code will not count its combinations
fun findArrangementsPossible(input: List<BigDecimal>) {
    val diffs = findShockingVoltages(input)
    // add our device diff here as terminal elem
    diffs.add(BigDecimal(3))

    println(diffs)
    var counter = 0;

    val fourFactor = 7
    val threeFactor = 4
    val twoFactor = 2

    var fourCount = 0
    var threeCount = 0
    var twoCount = 0

    for (i in 0 until diffs.size){
        when {
            diffs[i]==BigDecimal(3) -> {
                if (counter==4){
                    fourCount++
                }
                if (counter==3){
                    threeCount++
                }
                if (counter==2){
                    twoCount++
                }
                counter=0
            }
            diffs[i]==BigDecimal(1) -> {
                counter++
            }
            else -> {
                println("this diff is not catered for ${diffs[i]}")
            }
        }
    }

    val pow = fourFactor.toDouble().pow(fourCount) * threeFactor.toDouble().pow(threeCount) * twoFactor.toDouble().pow(twoCount)
    println("the answer is ${pow.toBigDecimal()} having $fourCount $threeCount $twoCount")
}

fun findShockingVoltages(input: List<BigDecimal>): MutableList<BigDecimal> {
    val voltDiff = mutableListOf<BigDecimal>()
    voltDiff.add(input[0])

    for (i in 1 until input.size){
        voltDiff.add(input[i] - input[i-1])
    }

    println(voltDiff)
    val onesies = voltDiff.count { it == BigDecimal(1) }
    val threezies = voltDiff.count { it == BigDecimal(3) } + 1
    println("count is  $onesies $threezies ${onesies* threezies}")
    return voltDiff
}

