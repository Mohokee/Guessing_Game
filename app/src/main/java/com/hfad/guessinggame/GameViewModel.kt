package com.hfad.guessinggame

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    //Possible Words
    val words = listOf("Horse","Mouse","Interlocutor","Shrimp","Ghost","Frog","Waffle","Geriatric","Serendipitous")
    val secretWord = words.random().uppercase()
    var secretWordDisplay = ""
    var correctGuesses = ""
    var incorrectGuesses = ""
    var livesLeft = 8

    init {
        //See how the secret word should display, and update the screen
        secretWordDisplay = deriveSecretWordDisplay()
    }

    fun deriveSecretWordDisplay(): String{
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    fun checkLetter(str:String) = when (correctGuesses.contains(str)){
        true -> str
        false -> "_"
    }

    fun makeGuess(guess:String){
        if(guess.length == 1){
            if(secretWord.contains(guess)){
                correctGuesses += guess
                secretWordDisplay = deriveSecretWordDisplay()
            } else {
                incorrectGuesses += "$guess "
                livesLeft --
            }
        }
    }

    fun isWon() = secretWord.equals(secretWordDisplay,true)

    fun isLost() = livesLeft <= 0


    fun wonLostMessage(): String{
        var message = ""
        if(isWon()) message = "You won!"
        if(isLost()) message = "You lost!"
        message += " The word was $secretWord"
        return message
    }

}