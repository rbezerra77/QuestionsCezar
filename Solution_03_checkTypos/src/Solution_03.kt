fun main(args: Array<String>){

    fun compareChars(chars01: CharArray, chars02: CharArray): Int{
        var charFound: Boolean = false
        var countTypos: Int = 0

        for(indexW1:Int in chars01.indices){
            charFound = false
            for(indexW2:Int in chars02.indices){
                if(chars01[indexW1] == chars02[indexW2]){
                    charFound = true
                    continue
                }
            }
            if(!charFound) countTypos++
        }

        return countTypos
    }

    fun checkTypos(word01: String, word02: String): Boolean {

        var word01CharArray: CharArray = word01.toCharArray()
        var word02CharArray: CharArray = word02.toCharArray()
        var countTypos: Int = 0
        var differenceCount: Int = word01.length - word02.length

        if(differenceCount > 1 || differenceCount < -1 ) return false //Words cannot have more than 1 char distance from each other

        when {
            word01.length == word02.length -> { //Same size, check number of replaced letters
                for(index:Int in word01CharArray.indices){
                    if (word01CharArray[index] != word02CharArray[index])
                        countTypos++
                }
            }
            word01.length < word02.length -> { // at least one letter added
                countTypos = compareChars(word02CharArray, word01CharArray)
            }
            else -> { // at least one letter missing
                countTypos = compareChars(word01CharArray, word02CharArray)
            }
        }
        //println("Comparing $word01 with $word02 typos")
        return countTypos < 2
    }

    println(checkTypos("pale","ple"))
    println(checkTypos("pales","pale"))
    println(checkTypos("pale","bale"))
    println(checkTypos("pale","bake"))

}
