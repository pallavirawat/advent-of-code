package d12

import Reader
import kotlin.math.abs

data class Coordinates(val x :Int, val y:Int){
    fun findQuadrant(): Quadrant{
        return if(x>=0){
            if(y>=0)
                Quadrant.Q1
            else
                Quadrant.Q2
        } else{
            if (y>0)
                Quadrant.Q4
            else
                Quadrant.Q3
        }
    }
}


enum class Quadrant(val xInc: Int, val yInc:Int){
    Q1(1,1),
    Q2(1,-1),
    Q3(-1,-1),
    Q4(-1,1)
}

enum class Direction(val xInc: Int, val yInc:Int){
    W(-1,0),
    E(1,0),
    N(0,1),
    S(0,-1),
    ERROR(0, 0);

    companion object{
        fun makeDirection(opcode: String): Direction {
            return when (opcode){
                "W" ->  W
                "E" ->  E
                "N" ->  N
                "S" ->  S
                else -> ERROR
            }
        }
    }
}

data class Ship(var coordinates: Coordinates, var direction: Direction){
    fun moveIntoDirection(dirToMove: Direction, instruction: Instruction){
        coordinates = Coordinates(coordinates.x + dirToMove.xInc * instruction.value,
        coordinates.y + dirToMove.yInc* instruction.value)
    }

    fun rotateDirection(ins: Instruction, clockwise: Boolean = true) {
        val directions = listOf(Direction.N, Direction.E, Direction.S, Direction.W).let {
            if(clockwise) it else it.reversed()
        }

        if(ins.value%90!==0){
            println("this ins is bad $ins")
        }
        val turnCount = ins.value/90

        val newDirectionIndex = (directions.indexOf(direction) + turnCount) % directions.size

        direction = directions[newDirectionIndex]
    }

    fun moveForward(instruction: Instruction) {
        coordinates = Coordinates(coordinates.x + direction.xInc * instruction.value,
            coordinates.y + direction.yInc* instruction.value)
    }

    fun moveForwardShipViaWayPoint(instruction: Instruction, wayPoint: WayPoint) {
        coordinates = Coordinates(coordinates.x + wayPoint.coordinates.x * instruction.value,
            coordinates.y + wayPoint.coordinates.y * instruction.value)
    }
}

data class WayPoint(var coordinates: Coordinates){
    fun moveIntoDirection(dirToMove: Direction, instruction: Instruction) {
        coordinates = Coordinates(coordinates.x + dirToMove.xInc * instruction.value,
            coordinates.y + dirToMove.yInc* instruction.value)
    }

    fun rotateDirection(ins: Instruction, clockwise: Boolean = true) {
        val quadrants = Quadrant.values().toList()
            .let {
            if(clockwise) it else it.reversed()
        }

        if(ins.value%90!==0){
            println("this ins is bad $ins")
        }
        val turnCount = ins.value/90

        println("$ins $quadrants $coordinates")

        val oldQad = coordinates.findQuadrant()
        val newQuadrantIndice = (quadrants.indexOf(oldQad) + turnCount) % quadrants.size

        val newQuadrant = quadrants[newQuadrantIndice]

        coordinates = if(turnCount%2==0){
            when(newQuadrant){
                Quadrant.Q1 -> Coordinates(abs(coordinates.x), abs(coordinates.y))
                Quadrant.Q2 -> Coordinates(abs(coordinates.x), toNegative(coordinates.y))
                Quadrant.Q3 -> Coordinates(toNegative(coordinates.x), toNegative(coordinates.y))
                Quadrant.Q4 -> Coordinates(toNegative(coordinates.x), abs(coordinates.y))
            }

        } else{
            when(newQuadrant){
                Quadrant.Q1 -> Coordinates(abs(coordinates.y), abs(coordinates.x))
                Quadrant.Q2 -> Coordinates(abs(coordinates.y), toNegative(coordinates.x))
                Quadrant.Q3 -> Coordinates(toNegative(coordinates.y), toNegative(coordinates.x))
                Quadrant.Q4 -> Coordinates(toNegative(coordinates.y), abs(coordinates.x))
            }
        }
//        println("new coords $ins $quadrants $coordinates $oldQad $newQuadrant ")
//        println()
    }

}

fun toNegative(n: Int): Int {
    if(n>0){
        return n*-1
    }
    return n
}

data class Instruction(val instruction: String, val value: Int)

fun main() {
    val input = Reader.read("12.txt") {
        rawToInstruction(it)
    }

//    shipManhattten(input)

    drownTheShipAndThisTimeSaveJack(input)
}

fun drownTheShipAndThisTimeSaveJack(input: List<Instruction>) {
    val ship = Ship(Coordinates(0, 0), Direction.E)
    val wayPoint = WayPoint(Coordinates(10, 1))
    input.forEach {
        when (it.instruction){
            "W" -> wayPoint.moveIntoDirection(Direction.W, it)
            "E" -> wayPoint.moveIntoDirection(Direction.E, it)
            "N" -> wayPoint.moveIntoDirection(Direction.N, it)
            "S" -> wayPoint.moveIntoDirection(Direction.S, it)
            "R" -> wayPoint.rotateDirection(it)
            "L" -> wayPoint.rotateDirection(it, false)
            "F" -> ship.moveForwardShipViaWayPoint(it, wayPoint)
            else -> Direction.ERROR
        }
    }

    println("ship is fine! ${ship.coordinates.x} ${ship.coordinates.y} " +
            "${abs(ship.coordinates.x) + abs(ship.coordinates.y)}")


}

fun shipManhattten(input: List<Instruction>) {
    val ship = Ship(Coordinates(0, 0), Direction.E)
    input.forEach {
        when (it.instruction){
            "W" -> ship.moveIntoDirection(Direction.W, it)
            "E" -> ship.moveIntoDirection(Direction.E, it)
            "N" -> ship.moveIntoDirection(Direction.N, it)
            "S" -> ship.moveIntoDirection(Direction.S, it)
            "R" -> ship.rotateDirection(it)
            "L" -> ship.rotateDirection(it, false)
            "F" -> ship.moveForward(it)
            else -> Direction.ERROR
        }
    }

    println("ship is fine! ${ship.coordinates.x} ${ship.coordinates.y} " +
            "${abs(ship.coordinates.x) + abs(ship.coordinates.y)}")

}

fun rawToInstruction(raw: String): Instruction{
    return Instruction(raw.take(1), raw.subSequence(1, raw.length).toString().toInt())
}
