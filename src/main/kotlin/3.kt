fun main() {
    val input = Reader.read("3.txt") { it }

//    println(bangYourselfOnTreesP1(input))
    println(bangYourselfOnTreesP2(input))

}

fun bangYourselfOnTreesP2(input: List<String>): Double {
    val slope1 = bangYourselfOnTreesP1V2(input, 1, 1).toDouble()
    val slope2 = bangYourselfOnTreesP1V2(input, 3, 1).toDouble()
    val slope3 = bangYourselfOnTreesP1V2(input, 5, 1).toDouble()
    val slope4 = bangYourselfOnTreesP1V2(input, 7, 1).toDouble()
    val slope5 = bangYourselfOnTreesP1V2(input, 1, 2).toDouble()
    println("$slope1 $slope2 $slope3 $slope4 $slope5")
    return slope1*slope2*slope3*slope4*slope5;
}

fun bangYourselfOnTreesP1(input: List<String>, forward:Int = 3, down:Int = 1): Int {
    var column = 0
    var trees=0
    input.forEach { road ->
            val step0 = road[(column) % input[0].length]
            if(step0=='#')
                trees += 1
            column= (column + forward) % input[0].length
    }
    return trees;
}

fun bangYourselfOnTreesP1V2(input: List<String>, forward:Int = 3, down:Int = 1): Int {
        var column = 0
        var trees=0
        for (i in input.indices step down){
            val road = input[i]
            val step0 = road[(column) % input[0].length]
            if(step0=='#')
                trees += 1
            column= (column + forward) % input[0].length
        }
        return trees;
}

