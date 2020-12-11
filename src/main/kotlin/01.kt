fun main() {
    val input = Reader.read("01.txt") { it.toInt() }
    println(expenseitoutFor1Star(input))    //840324
    println(expenseitoutFor2Star(input))     //170098110
}

fun expenseitoutFor2Star(input: List<Int>): Int {
    for( i in 0..(input.size-2)){
        if(input[i]< 2020){
            val remaining = 2020 - input[i]
            for(j in (i+1) until input.size){
                if(input[j] < remaining){
                    val thirdrem = remaining- input[j]
                    val num3Index = input.indexOf(thirdrem)
                    if (num3Index!=-1){
                        return input[i] * input[num3Index] * input[j]
                    }
                }
            }

        }
    }
    return 0
}

fun expenseitoutFor1Star(input: List<Int>): Int {
    for( i in 0..input.size){
        if(i< 2020){
            val remaining = 2020 - input[i]
            val num2Index = input.indexOf(remaining)
            if (num2Index!=-1){
                return input[i] * input[num2Index]
            }
        }
    }
    return 0
}