fun main() {
    val input = Reader.read("4.txt") { it }
    val passports = rawToPassports(input)

    println(countValidPassports(passports))
    println(countValidPassportFor2ndOne(passports))

}

private fun rawToPassports(input: List<String>): MutableList<Passport> {
    var passsportTemp = Passport();
    val passports = mutableListOf<Passport>()
    input.forEach {
        if (it.isBlank()) {
            passports.add(passsportTemp)
            passsportTemp = Passport();
        }
        val passportFields = it.split(" ")
        passportFields.forEach { field ->
            val split = field.split(":")
            when (split[0]) {
                "byr" -> passsportTemp.byr = BirthYear(split[1])
                "iyr" -> passsportTemp.iyr = IssueYear(split[1])
                "eyr" -> passsportTemp.eyr = ExpirationYear(split[1])
                "hgt" -> passsportTemp.hgt = Height(split[1])
                "hcl" -> passsportTemp.hcl = split[1]
                "ecl" -> passsportTemp.ecl = split[1]
                "pid" -> passsportTemp.pid = split[1]
                "cid" -> passsportTemp.cid = split[1]
            }
        }
    }
    passports.add(passsportTemp)
    return passports
}

fun countValidPassportFor2ndOne(input: List<Passport>): Int {
    return input.count{it.isValid2()}
}

fun countValidPassports(input: List<Passport>): Int {
    return input.count{it.isValid()}
}

open class Year(private val year: String, val minValidity: Int, val maxValidity: Int){
    fun isValid():Boolean{
        return year.isNotEmpty()
                && year.length==4
                && year.toInt()>=minValidity && year.toInt()<=maxValidity
    }
}

data class BirthYear(val year: String=""): Year(year, 1920, 2002)
data class IssueYear(val year: String=""): Year(year, 2010, 2020)
data class ExpirationYear(val year: String=""): Year(year, 2020, 2030)
data class Height(val raw: String="",
                  val height: Int? = if (raw.isNotBlank()) raw.dropLast(2).toIntOrNull() else -1,
                  val metric: String = if(raw.isNotEmpty()) raw.takeLast(2) else ""){
    fun isValid(): Boolean {
        if(raw.isEmpty()){
            return false
        }
        when(metric){
            "cm" -> if(height in 150..193)
                return true
            "in" -> if(height in 59..76)
                return true
        }
        return false
    }
}

data class Passport(var byr: BirthYear= BirthYear(),
                    var iyr: IssueYear=IssueYear(),
                    var eyr: ExpirationYear=ExpirationYear(),
                    var hgt: Height=Height(),
                    var hcl: String="",
                    var ecl: String="",
                    var pid: String="",
                    var cid: String="",
){
    fun isValid(): Boolean{
        if(
            byr.year.isNotEmpty()  &&
            iyr.year.isNotEmpty() &&
            eyr.year.isNotEmpty() &&
            hgt.raw.isNotEmpty() &&
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
            byr.isValid() &&
            iyr.isValid() &&
            eyr.isValid() &&
            hgt.isValid() &&
            isHclValid() &&
            isEclValid() &&
            isPidValid()){
            return true
        }
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