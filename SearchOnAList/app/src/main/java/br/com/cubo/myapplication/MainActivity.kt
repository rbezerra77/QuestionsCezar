package br.com.cubo.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var filterEditText: EditText
    private var listOfWords: ArrayList<String> = ArrayList<String>()
    private lateinit var arrayAdapter: ArrayAdapter<*>

    private fun compareChars(chars01: CharArray, chars02: CharArray): Int{
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

    fun checkJumbled(word01: String, word02: String): Boolean{

        var word01ToChar: CharArray = word01.toCharArray()
        var word02ToChar: CharArray = word02.toCharArray()
        var countMisspellings: Int = 0
        var misspellingRate: Float = 0.0F

        if(word01.length != word02.length) return false

        if(word01.length > 1 ) {

            if(word01ToChar[0] != word02ToChar[0]) return false //Violation of first character rule - They Must be Equal
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

    fun doFilter(view: View){
        var filtered: ArrayList<String> = ArrayList<String>()
        val filterWord = filterEditText.text.toString()

        if(filterWord.isEmpty()) {
            resetListView()
            return
        }

        for(word: String in listOfWords){
            if(checkTypos(filterWord, word)) {
                filtered.add(word)
            } else if(checkJumbled(filterWord, word)){
                filtered.add(word)
            }
        }

        listOfWords.clear()
        listOfWords.addAll(filtered)
        arrayAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filterEditText = findViewById<EditText>(R.id.filterEditText)

        var listOfWordsListView = findViewById<ListView>(R.id.listOfWordsListView)

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfWords)

        resetListView()

        listOfWordsListView.adapter = arrayAdapter

    }
    private fun resetListView(){
        listOfWords.clear()
        listOfWords.add("you")
        listOfWords.add("probably")
        listOfWords.add("despite")
        listOfWords.add("moon")
        listOfWords.add("misspellings")
        listOfWords.add("pale")
        listOfWords.add("pales")
        listOfWords.add("Rodrigo")
        listOfWords.add("Bezerra")

        arrayAdapter.notifyDataSetChanged()
    }
}
