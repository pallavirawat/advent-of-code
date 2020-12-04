fun main() {
    val input = Reader.read("4.txt") { it }
    var count = 0
    var passsportTemp = Passport();
    val passports = mutableListOf<Passport>()
    input.forEach {
        if(it.isBlank()){
            count++
            passports.add(passsportTemp)
            passsportTemp = Passport();
        }
        val passportFields = it.split(" ")
        passportFields.forEach { field->
            val split = field.split(":")
            when (split[0]) {
                "byr" -> passsportTemp.byr = split[1]
                "iyr" -> passsportTemp.iyr = split[1]
                "eyr" -> passsportTemp.eyr = split[1]
                "hgt" -> passsportTemp.hgt = split[1]
                "hcl" -> passsportTemp.hcl = split[1]
                "ecl" -> passsportTemp.ecl = split[1]
                "pid" -> passsportTemp.pid = split[1]
                "cid" -> passsportTemp.cid = split[1]
            }
        }
    }
    passports.add(passsportTemp)

//    println(countValidPassports(passports))
    println(countValidPassportFor2ndOne(passports))

}

fun countValidPassportFor2ndOne(input: List<Passport>): Int {
    return input.count{it.isValid2()}
}

fun countValidPassports(input: List<Passport>): Int {
    return input.count{it.isValid()}
}

data class Passport(var byr: String="",
                    var iyr: String="",
                    var eyr: String="",
                    var hgt: String="",
                    var hcl: String="",
                    var ecl: String="",
                    var pid: String="",
                    var cid: String="",
){
    fun isValid(): Boolean{
        if(
            byr.isNotEmpty()  &&
            iyr.isNotEmpty() &&
            eyr.isNotEmpty() &&
            hgt.isNotEmpty() &&
            hcl.isNotEmpty() &&
            ecl.isNotEmpty() &&
            pid.isNotEmpty()){
            return true
        }
        return false
    }

    fun isValid2(): Boolean{
        if(
            isValid() &&
            isByrValid() &&
            isIvrValid() &&
            isEyrValid() &&
            isHgtValid() &&
            isHclValid() &&
            isEclValid() &&
            isPidValid()){
            return true
        }
        return false
    }

    fun isByrValid(): Boolean {
        if(byr.length==4 && byr.toInt()>=1920 && byr.toInt()<=2002)
            return true
        println("byr $byr")
        return false
    }

    fun isIvrValid(): Boolean {
        if(iyr.length==4 && iyr.toInt()>=2010 && iyr.toInt()<=2020)
            return true
        println("iyr $iyr")

        return false
    }

    fun isEyrValid(): Boolean {
        if(eyr.length==4 && eyr.toInt()>=2020 && eyr.toInt()<=2030)
            return true
        println("eyr $eyr")

        return false
    }

    fun isHgtValid(): Boolean {
        val height = hgt.dropLast(2).toInt()
        if(hgt.endsWith("cm", true)){
            if(height in 150..193)
                return true
        }
        if(hgt.endsWith("in", true)){
            if(height in 59..76)
                return true
        }
        println("hgt $hgt")

        return false
    }

    fun isHclValid(): Boolean {
        if(hcl.startsWith("#") && hcl.length==7 && hcl.drop(1).all { "abcdef0123456789".contains(it) }){
            return true
        }
        println("hcl $hcl")

        return false
    }

    fun isEclValid(): Boolean {
        when(ecl){
            "amb", "blu", "brn", "gry", "grn", "hzl", "oth" -> return true
        }

        println("ecl $ecl")

        return false
    }

    fun isPidValid(): Boolean {
        if(pid.length==9 && pid.all { "0123456789".contains(it) }){
            return true
        }
        return false
    }
}