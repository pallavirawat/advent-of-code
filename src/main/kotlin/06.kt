fun main() {
    val input = Reader.read("06.txt") { it }

    val readAndGroup = Reader.readAndGroup("06.txt", groupBy = { it.isEmpty() }) {
        it.toCharArray()
    }
//    6596
//    3219
    println(findAnswersWithStyle(readAndGroup))
    println(findAnswersToWhichAllAnsweredYesWithStyle(readAndGroup))
}

fun findAnswersToWhichAllAnsweredYesWithStyle(groupedInput: List<List<CharArray>>): Int {
    return groupedInput.fold(0){acc, elem ->
        val fold = elem.reduce { acc, chars ->
            (acc intersect chars.toList()).toCharArray()
        }.size
        acc+fold
    }
}

fun findAnswersWithStyle(groupedInput: List<List<CharArray>>): Int {
    return groupedInput.fold(0){acc, elem ->
            val fold = elem.fold(mutableSetOf()) { acc: Set<Char>, chars ->
                acc union chars.toSet()
            }
        acc + fold.size
    }
}
