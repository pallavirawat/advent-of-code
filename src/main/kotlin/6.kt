fun main() {
    val input = Reader.read("6.txt") { it }
    println(findAnswers(input))
    println(findAnswersToWhichAllAnsweredYes(input))
}

fun findAnswers(input: List<String>): Int {
    var count = 0;
    val groupsAnswers = mutableListOf<Set<Char>>()
    var groupAnswer = mutableSetOf<Char>();
    input.forEach {
        if (it.isBlank()) {
            groupsAnswers.add(groupAnswer)
            count+=groupAnswer.size
            groupAnswer = mutableSetOf<Char>()

        }
        it.forEach { individualAnswer -> groupAnswer.add(individualAnswer) }
    }
    groupsAnswers.add(groupAnswer)
    count+=groupAnswer.size

    return count
}

fun findAnswersToWhichAllAnsweredYes(input: List<String>): Int {
    var count = 0;
    var groupAnswer = mutableListOf<Char>();
    var groupSize=0
    input.forEach {
        if (it.isBlank()) {
            val maxYes = groupAnswer.groupBy { charac -> charac }.values.filter {
                    it -> it.size==groupSize
            }

           if (maxYes != null) {
                count+=maxYes.size
            }
            groupSize=0
            groupAnswer = mutableListOf()

        }
        else{
            it.forEach { individualAnswer ->
                groupAnswer.add(individualAnswer)
            }
            groupSize++
        }
    }
    val maxYes = groupAnswer.groupBy { charac -> charac }.values.filter {
            it -> it.size==groupSize
    }
    if (maxYes != null) {
        count+=maxYes.size
    }

    return count
}
