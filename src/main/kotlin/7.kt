import kotlin.collections.set

fun main() {
    val input = Reader.read("7.txt") {
        rawToBags(it)
    }
    println(input)
    findGoldenBagWithProbablyNoMoneyJustBags(input)
}

fun findGoldenBagWithProbablyNoMoneyJustBags(input: List<Bag>) {
    val containedBy = mutableMapOf<String, List<String>>()
    input.forEach { bag ->
        bag.contains.forEach {
            if (!containedBy.containsKey(it)) {
                containedBy[it] = mutableListOf(bag.color)
            } else {
                containedBy[it] = containedBy[it]!! + mutableListOf(bag.color)
            }
        }
    }

    val visitedNodes = mutableSetOf("shiny gold bag")
    val nodesToBeChecked = ArrayDeque<String>()
    nodesToBeChecked.addAll(
        containedBy["shiny gold bag"]!!
    )
//    println(nodesToBeChecked)
    println(doSearchNow(nodesToBeChecked, containedBy, visitedNodes))
}

fun doSearchNow(
    nodesToBeChecked: ArrayDeque<String>,
    containedByMapping: MutableMap<String, List<String>>,
    visitedNodes: MutableSet<String>
): Int {
    var lcounter = 0
    while (nodesToBeChecked.isNotEmpty()) {
        val currentBag = nodesToBeChecked.removeFirst()
        val containsKey = containedByMapping.containsKey(currentBag)
        if (containsKey) {
            val list = containedByMapping[currentBag]
            println("$list $currentBag")
            val parentBags = list!!
            parentBags.forEach { parentBag ->
                if (!visitedNodes.contains(parentBag)) {
                    println("hello")
                    nodesToBeChecked.addLast(parentBag)
                }
            }
        }
        if (!visitedNodes.contains(currentBag)) {
            lcounter++
        }
        visitedNodes.add(currentBag)
    }
//    println(visitedNodes.size)
    return lcounter
}

fun rawToBags(it: String): Bag {
    val replace = it.dropLast(1).replace("bags", "bag").replace(Regex("[0-9]+"), " ")
    val split = replace.split("contain")
    return Bag(split[0].trim(), split[1].split(",").map { it.trim() })
}

data class Bag(val color: String, val contains: List<String>)
