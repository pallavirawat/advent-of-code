fun main() {
    val input = Reader.read("5.txt") { it }
//    996
//    671
    println(doBinaryBoarding(input))
    println(findMySeat(input))
}

fun doBinaryBoarding(input: List<String>): Int? {
    val map = letsMakeSeatNumbersComplicatedBecauseCovidWasntEnough(input)
    return map.maxOrNull()
}

fun findMySeat(input: List<String>): Int {
    val allSeatsIds = letsMakeSeatNumbersComplicatedBecauseCovidWasntEnough(input)
    val sorted = allSeatsIds.sorted()
    for (i in sorted.indices){
        if(sorted[i+1]-sorted[i]==2){
            return sorted[i]+1
        }
    }
    return -1
}

private fun letsMakeSeatNumbersComplicatedBecauseCovidWasntEnough(input: List<String>): List<Int> {
    return input.map {
        if (it.length !== 10) {
            println(it)
        }
        val row = it.take(7).replace('F', '0').replace('B', '1').toInt(2)
        val column = it.takeLast(3).replace('L', '0').replace('R', '1').toInt(2)
        (row * 8 + column)
    }
}
