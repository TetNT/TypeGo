package com.tetsoft.typego.ui.fragment.game

import android.text.InputType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetsoft.typego.data.Word
import com.tetsoft.typego.game.GameOnTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameOnTimeViewModel @Inject constructor() : ViewModel() {

    var gameOnTime: GameOnTime = GameOnTime.Empty()

    private var score : Int = 0

    private val typedWordsList = ArrayList<Word>()

    fun addWordToTypedList(inputted: String, original: String) {
        viewModelScope.launch {
            val mistakeIndexes = ArrayList<Int>()
            for (i in inputted.indices) {
                if (i >= original.length)
                    mistakeIndexes.add(i)
                else if (inputted.lowercase()[i] != original.lowercase()[i])
                    mistakeIndexes.add(i)
            }
            typedWordsList.add(Word(inputted, original, mistakeIndexes))
        }
    }

    fun clearTypedWords() {
        typedWordsList.clear()
    }

    fun getTypedWords() : ArrayList<Word> {
        return typedWordsList
    }

    fun wordIsCorrect(enteredWord: String, otherWord: String, ignoreCase: Boolean) : Boolean {
        return enteredWord.trim{ it <= ' ' }.equals(otherWord.trim { it <= ' ' }, ignoreCase)
    }

    fun calculateCorrectWords() : Int {
        var count = 0
        viewModelScope.launch {
            for (word in typedWordsList) {
                if (word.isCorrect()) count = count.inc()
            }
        }
        return count
    }

    fun getInputType() : Int {
        return if (gameOnTime.areSuggestionsActivated())
            InputType.TYPE_CLASS_TEXT
        else
            InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    fun addScore(amount: Int) {
        score += amount
    }

    fun getScore() : Int {
        return score
    }

    fun clearScore() {
        score = 0
    }
}