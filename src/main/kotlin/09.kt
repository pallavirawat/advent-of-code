import java.math.BigDecimal

fun main() {
    val input = Reader.read("09.txt") { it.toBigDecimal() }

    val invalidNumber = getheBadSeed(input)
    println(invalidNumber)
    println(findContiguousSetToGiveSuperpowerTo(invalidNumber, input))
}

fun findContiguousSetToGiveSuperpowerTo(invalidNumber: BigDecimal, input: List<BigDecimal>): BigDecimal {
    for (i in input.indices){
        var sum = input[i]
        var end = i+1
        while (sum<invalidNumber){
            sum+=input[end]
            end++
        }
        if(sum==invalidNumber){
            println("$i $end")
            val subList = input.subList(i, end)
            return subList.maxOrNull()!! + subList.minOrNull()!!
        }
    }


    return BigDecimal(-1)
}

fun getheBadSeed(input: List<BigDecimal>): BigDecimal {
    var start = 0
    var end = 25
    for (i in (end) until input.size) {
        if(isNumberValid(input.subList(start, end), input[i])){
            start++
            end++
        }
        else{
            println("$i")
            return input[i]
        }
    }

    return BigDecimal(-1)
}

fun isNumberValid(subList: List<BigDecimal>, number: BigDecimal): Boolean {
    val valid = false
    for (i in 0 until (subList.size-1)){
        val remaining = number-subList[i]
        val contains = subList.subList(i + 1, subList.size).contains(remaining)
        if (contains) return true
    }
    return valid
}
