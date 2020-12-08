fun main() {
    val input = Reader.read("8.txt") { rawToInstruction(it) }

//    println(getSumBeforeIninity(input))
    println(getSumBeforeIninityPe3(input))
}

fun getSumBeforeIninity(input: List<Instruction>): Boolean {
    var infinityDetected = false
    var ci = 0
    var accval = 0
    while (!infinityDetected){
        if(input[ci].executed){
            infinityDetected=true
        }
        else{
            when(input[ci].op){
                "nop" -> {
                    ci++
                }
                "acc" -> {
                    accval+=input[ci].value
                    ci++
                }
                "jmp" -> ci+=input[ci].value
            }
        }
    }

    return infinityDetected
}


fun getSumBeforeIninityP2(input: List<Instruction>): Boolean {
    var infinityDetected = false
    var ci = 0
    var accval = 0
    while (!infinityDetected){
        if (ci>=input.size){
            println("found it! $ci $accval")
            return false
        }
        if(input[ci].executed){
            infinityDetected=true
        }
        else{
            when(input[ci].op){
                "nop" -> {
                    ci++
                }
                "acc" -> {
                    accval+=input[ci].value
                    ci++
                }
                "jmp" -> ci+=input[ci].value
            }
        }
    }
    return infinityDetected
}

fun rawToInstruction(raw: String): Instruction{
    val split = raw.split(" ")
    return Instruction(split[0], split[1].toInt())
}


fun getSumBeforeIninityPe3(input: List<Instruction>) {
    val inputToBeUsed = input.toMutableList()
    input.forEachIndexed { index, instruction ->
        if (instruction.isSwapable()){
            val swapped = instruction.swap()
            val modInput = inputToBeUsed.toMutableList()
            modInput[index] = swapped
            modInput.forEach {
                it.executed=false
            }
            val infinityDetect = getSumBeforeIninityP2(modInput)
            if (!infinityDetect){
                println("FOUND IT")
            }
        }
    }

}


class Instruction{
    var op: String
     get() {
         executed=true
         return field
     }
    val value:Int
    var executed:Boolean =false

    constructor(op: String, value: Int) {
        this.value = value
        this.op = op
    }

    fun isSwapable(): Boolean{
        val b = op == "jmp" || op == "nop"
        executed = false
        return b
    }

    fun swap(): Instruction {
        when(op){
            "jmp" -> {
                executed = false
                return Instruction("nop", value)
            }
            "nop" -> {
                executed = false
                return Instruction("jmp", value)
            }
        }
        executed = false
        return this
    }
}