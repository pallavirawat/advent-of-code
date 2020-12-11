import kotlin.collections.set

fun main() {
//    val input = Reader.read("7.txt") {
//        rawToBags(it)
//    }
//
//    println(input)
//    findGoldenBagWithProbablyNoMoneyJustBags(input)
    val inputForP2 = Reader.read("07.txt") {
        rawToBagsCount(it)
    }.toMap()
//    println(inputForP2)
    findBagInBags(inputForP2) //20189
}

fun findBagInBags(inputForP2: Map<String, List<ChildBag>>) {
    val lvl1Bags = mutableListOf<ChildBag>()

    inputForP2["shiny gold bag"]?.let { shinyBag ->
        lvl1Bags.addAll(shinyBag)
    }

    println("sum is ${findSubBagCount(inputForP2, lvl1Bags, 1)}")
}

fun findSubBagCount(inputForP2: Map<String, List<ChildBag>>, cbl: List<ChildBag>, factor: Int): Int {
    if(cbl.isEmpty()){
        return 0
    }
    var sum = 0
    cbl.forEach { cb->
        inputForP2[cb.color]?.let {
            sum += cb.count * factor + findSubBagCount(inputForP2, it, cb.count*factor)
        }
    }

    return sum
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
    return lcounter
}

fun rawToBags(it: String): Bag {
    val replace = it.dropLast(1).replace("bags", "bag").replace(Regex("[0-9]+"), " ")
    val split = replace.split("contain")
    return Bag(split[0].trim(),
        split[1].split(",").map { it.trim() })
}
fun rawToBagsCount(it: String): Pair<String, List<ChildBag>> {
    val consistentInp = it.dropLast(1).replace("bags", "bag")
    val split = consistentInp.split("contain")
    val color = split[0].trim()

    val pair = color to rawToChildBag(split[1])
    println(pair)
    return pair
}

fun rawToChildBag(raw: String): List<ChildBag> {
    if(raw.contains("no other bag")){
        return mutableListOf()
    }
    return raw.split(",").map {
        it.trim()
        val bagColor = it.trim().replace(Regex("^[0-9]+"), "").trim()
        val count = it.replace(Regex("[^0-9]+"), "").trim().toInt()
        ChildBag(bagColor, count)
    }
}

data class Bag(val color: String, val contains: List<String>)
data class ChildBag(val color: String, val count: Int)