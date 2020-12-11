@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val input = Reader.read("11.txt") {
        it.replace('L', '#').toCharArray()
    }
    input.forEach {
        it.forEach { char ->
            print(char)
        }
        println()
    }
    println()
    pleaseDontSitOnMe(input)
}

@ExperimentalStdlibApi
fun pleaseDontSitOnMe(seating: List<CharArray>) {
    // for part 1
//    val newSeating = peopleGetUpSeeAroundAndSit(seating)

    // for part 2
    val newSeating = peopleGetUpSeeAroundAndSitAndHaveALazorNonMyopicVision(seating)

    if (!areWeGettingUpAndDownForNoReason(seating, newSeating)) {
        pleaseDontSitOnMe(newSeating)
    } else {
        var count = 0
        seating.forEach {
            it.forEach { char ->
                print(char)
                if (char == '#') {
                    count++
                }
            }
            println()
        }
        println()
        println("count of occupied seats are $count")
    }
}


fun areWeGettingUpAndDownForNoReason(currentSeating: List<CharArray>, newSeating: List<CharArray>): Boolean {
    // compare if both the seatmaps are same! return true then
    currentSeating.forEachIndexed { ri, row ->
        row.forEachIndexed { ci, seat ->
            if (newSeating[ri][ci] !== seat)
                return false
        }
    }
    return true
}

@ExperimentalStdlibApi
fun peopleGetUpSeeAroundAndSit(currentSeating: List<CharArray>): List<CharArray> {
    val myNeighbors = buildList {
        add(Increment(0, 1))
        add(Increment(1, 1))
        add(Increment(1, 0))
        add(Increment(0, -1))
        add(Increment(-1, -1))
        add(Increment(-1, 0))
        add(Increment(-1, 1))
        add(Increment(1, -1))
    }

    val newSeating = currentSeating.map {
        it.toMutableList().toCharArray()
    }

    currentSeating.forEachIndexed { ri, row ->
        row.forEachIndexed { ci, seat ->

            //for when seat is occupied and free it up in case of too many neighbors
            if (seat != '.') {
                var peopleAroundMe = 0
                myNeighbors.forEach {
                    if ((ri + it.ycoord) in currentSeating.indices &&
                        (ci + it.xcoord) in row.indices &&
                        currentSeating[ri + it.ycoord][ci + it.xcoord] == '#'
                    )
                        peopleAroundMe++
                }
                if (peopleAroundMe >= 4)
                    newSeating[ri][ci] = 'L'

                //for seat which is empty
                if (peopleAroundMe == 0)
                    newSeating[ri][ci] = '#'
            }
        }
    }

    return newSeating
}


@ExperimentalStdlibApi
fun peopleGetUpSeeAroundAndSitAndHaveALazorNonMyopicVision(currentSeating: List<CharArray>): List<CharArray> {
    val myNeighbors = buildList {
        add(Increment(0, 1))
        add(Increment(1, 1))
        add(Increment(1, 0))
        add(Increment(0, -1))
        add(Increment(-1, -1))
        add(Increment(-1, 0))
        add(Increment(-1, 1))
        add(Increment(1, -1))
    }

    val newSeating = currentSeating.map {
        it.toMutableList().toCharArray()
    }

    currentSeating.forEachIndexed { ri, row ->
        row.forEachIndexed { ci, seat ->
//            println("$ri $ci")
            //for when seat is occupied and free it up in case of too many neighbors
            if (seat != '.') {
                var peopleAroundMe = 0
                myNeighbors.forEach {
//                    println(" neighbors ${it}")
                    var curInc = it
                    while ((ri + curInc.ycoord) in currentSeating.indices &&
                        (ci + curInc.xcoord) in row.indices
                    ) {
                        if (currentSeating[ri + curInc.ycoord][ci + curInc.xcoord] == '#') {
                            peopleAroundMe++
                            break
                        }
                        if (currentSeating[ri + curInc.ycoord][ci + curInc.xcoord] == 'L') {
                            break
                        }
                        curInc = curInc.getNextIncrement(it.xcoord, it.ycoord)
                    }
                }
                if (peopleAroundMe >= 5)
                    newSeating[ri][ci] = 'L'

                //for seat which is empty
                if (peopleAroundMe == 0)
                    newSeating[ri][ci] = '#'
            }
        }
    }

    return newSeating
}

// from:inclusive, to: exclusive
fun Int.validInRange(from: Int, to: Int) {

}

data class Increment(val xcoord: Int, val ycoord: Int) {
    fun getNextIncrement(xStep: Int, yStep: Int): Increment {
        return Increment(xcoord + xStep, ycoord + yStep)
    }
}