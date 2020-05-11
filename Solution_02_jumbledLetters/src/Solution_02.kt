fun main(args: Array<String>){

    fun checkJumbled(word01: String, word02: String): Boolean{

        var word01ToChar: CharArray = word01.toCharArray()
        var word02ToChar: CharArray = word02.toCharArray()
        var countMisspellings: Int = 0
        var misspellingRate: Float = 0.0F

        if(word01.length != word02.length) return false

        if(word01.length > 1 ) {
            if(word01ToChar[0] != word02ToChar[0]){
                return false //Violation of first character rule - They Must be Equal
            }

            if(word01.length <= 3) return true // Words with less/equals size than 3 and the first letter didn't change, return true

            //Counting Misspellings
            for (index: Int in 1 until (word01ToChar.size - 1)) {
                if(word01ToChar[index]!=word02ToChar[index]){
                    countMisspellings++
                }
            }
            misspellingRate = countMisspellings.toFloat() / word01ToChar.size.toFloat()

            return misspellingRate <= 0.6666

        } else
        {
            return true //Only 1 character word
        }
    }

    var case01 = checkJumbled("you", "yuo")
    var case02 = checkJumbled("probably", "porbalby")
    var case03 = checkJumbled("despite", "desptie")
    var case04 = checkJumbled("moon", "nmoo")
    var case05 = checkJumbled("misspellings", "mpeissngslli")
    var case06 = checkJumbled("you", "uoy")
    var case07 = checkJumbled("Done", "Done")


    println(case01)
    println(case02)
    println(case03)
    println(case04)
    println(case05)
    println(case06)
    println(case07)

}