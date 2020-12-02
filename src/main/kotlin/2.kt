fun main() {
    val input = Reader.read("2data.txt") { rawToPasswordConvo(it) }
//    println(input)
    println(findValidPasswordCountForOneStar(input)) //591
    println(findValidPasswordCountForTwoStar(input)) //335
}

fun rawToPasswordConvo(inputline: String): Password{
    val split = inputline.split(" ")
    return Password(split[0], split[1][0], split[2])
}
fun findValidPasswordCountForOneStar(input: List<Password>): Int {
    return input.count { it.isValid() }
}

fun findValidPasswordCountForTwoStar(input: List<Password>): Int {
    return input.count { it.isValidAsPer2StarRule() }
}

data class Password(val rule: String, val alphabet: Char, val value: String,
                    val minOcc: Int= rule.split("-")[0].toInt(),
                    val maxOcc: Int= rule.split("-")[1].toInt()){
    fun isValid(): Boolean{
        val occurencesFound = value.count { it == alphabet }
        if(occurencesFound<minOcc || occurencesFound>maxOcc)
            return false
        return true
    }

    fun isValidAsPer2StarRule(): Boolean{
//        if(minOcc+1 >= value.length || maxOcc+1 >= value.length)
//            return false
        val position1Contains = value[minOcc - 1] != alphabet
        val position2COntains = value[maxOcc - 1] != alphabet
        if((position1Contains && !position2COntains) || (!position1Contains && position2COntains))
            return true
        return false
    }
}



