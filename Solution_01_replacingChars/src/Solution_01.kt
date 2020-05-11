fun main(args: Array<String>){

    fun replacingChars(myPhrase: CharArray, actualArraySize: Int): CharArray{
        var noOfSpaces: Int = 0;

        for( index in 0 until actualArraySize) if (myPhrase[index]==' '){
            noOfSpaces++
        }

        var fullSize: Int = myPhrase.size - noOfSpaces - 1

        for(index in (actualArraySize - 1) downTo 0){
            if(myPhrase[index] == ' '){
                myPhrase[fullSize] = '2';
                myPhrase[fullSize - 1] = '3';
                myPhrase[fullSize - 2] = '&';
                fullSize -= 3
            }else
            {
                myPhrase[fullSize] = myPhrase[index]
                fullSize--
            }
        }
        return myPhrase
    }

    var anyPhrase = replacingChars("Rodrigo de Azevedo Bezerra         ".toCharArray(), 26)
    var spacesInTheEdges = replacingChars(" Hire me!          ".toCharArray(), 10)
    var allSpaces = replacingChars("            ".toCharArray(), 3)
    var noSpace = replacingChars("rodrigo&32de&32azevedo&32bezerra".toCharArray(), 32)

    println(anyPhrase)
    println(spacesInTheEdges)
    println(allSpaces)
    println(noSpace)
}