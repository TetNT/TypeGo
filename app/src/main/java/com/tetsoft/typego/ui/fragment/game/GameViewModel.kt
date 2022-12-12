package com.tetsoft.typego.ui.fragment.game

import android.text.InputType
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetsoft.typego.data.Word
import com.tetsoft.typego.game.mode.GameMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class GameViewModel @Inject constructor(): ViewModel() {

    private val _gameMode = MutableLiveData<GameMode>()

    val gameMode : GameMode get() = _gameMode.value!!

    private val typedWordsList = ArrayList<Word>()

    var score : Int = 0

    fun selectGameMode(gameMode: GameMode) {
        _gameMode.postValue(gameMode)
    }

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
        if (gameMode.suggestionsActivated) return InputType.TYPE_CLASS_TEXT
        else return InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    fun addScore(amount: Int) {
        score += amount
    }

    fun clearScore() {
        score = 0
    }
}