package com.hfad.guessinggame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData

class GameViewModel : ViewModel() {
    //Possible Words
    private val words = listOf(
        "Horse",
        "Mouse",
        "Interlocutor",
        "Shrimp",
        "Ghost",
        "Frog",
        "Waffle",
        "Geriatric",
        "Serendipitous",
        "Hone",
        "Loquat",
        "Burble",
        "Sauce"
    )
    private val secretWord = words.random().uppercase()
    private val _secretWordDisplay = MutableLiveData<String>()
    val secretWordDisplay: LiveData<String>
        get() = _secretWordDisplay
    var correctGuesses = ""
    private val _incorrectGuesses = MutableLiveData<String>("")
    val incorrectGuesses: LiveData<String>
        get() = _incorrectGuesses
    private val _livesLeft = MutableLiveData<Int>(8)
    val livesLeft : LiveData<Int>
        get() = _livesLeft
    private val _gameOver = MutableLiveData<Boolean>(false)
    val gameOver : LiveData<Boolean>
        get() = _gameOver

    init {
        //See how the secret word should display, and update the screen
        _secretWordDisplay.value = deriveSecretWordDisplay()
    }

    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }

    private fun checkLetter(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }

    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuesses += guess
                _secretWordDisplay.value = deriveSecretWordDisplay()
            } else {
                _incorrectGuesses.value += "$guess "
                _livesLeft.value = _livesLeft.value?.minus(1)
            }
            if(isWon()||isLost()) _gameOver.value = true
        }
    }

    private fun isWon() = secretWord.equals(secretWordDisplay.value, true)

    private fun isLost() = (livesLeft.value ?: 0) <= 0


    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You won!"
        if (isLost()) message = "You lost!"
        message += " The word was $secretWord"
        return message
    }

    fun finishGame(){
        _gameOver.value = true
    }

}