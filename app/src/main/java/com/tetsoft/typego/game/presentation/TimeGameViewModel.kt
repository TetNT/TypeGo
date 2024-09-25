package com.tetsoft.typego.game.presentation

import android.content.res.AssetManager
import android.text.InputType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetsoft.typego.BuildConfig
import com.tetsoft.typego.Config
import com.tetsoft.typego.core.domain.GameSettings
import com.tetsoft.typego.core.domain.OwnText
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.core.data.Word
import com.tetsoft.typego.core.utils.DictionaryTypeConverter
import com.tetsoft.typego.core.utils.ScreenOrientationConverter
import com.tetsoft.typego.game.data.AssetStringReader
import com.tetsoft.typego.game.data.CpmCalculationImpl
import com.tetsoft.typego.game.data.DictionaryAssetPathResolver
import com.tetsoft.typego.game.data.TextSourceFromAsset
import com.tetsoft.typego.game.data.TextSourceUserText
import com.tetsoft.typego.game.data.WpmCalculationImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class TimeGameViewModel @Inject constructor() : ViewModel() {

    var gameSettings: GameSettings.TimeBased = GameSettings.TimeBased.Empty()

    private var score: Int = 0

    private val typedWordsList = ArrayList<Word>()

    fun addWordToTypedList(inputted: String, original: String) {
        viewModelScope.launch {
            typedWordsList.add(Word(inputted, original, gameSettings.ignoreCase))
        }
    }

    fun getTypedWords(): ArrayList<Word> {
        return typedWordsList
    }

    fun wordIsCorrect(enteredWord: String, otherWord: String, ignoreCase: Boolean): Boolean {
        return enteredWord.equals(otherWord, ignoreCase)
    }

    fun calculateCorrectWords(): Int {
        var count = 0
        viewModelScope.launch {
            for (word in typedWordsList) {
                if (word.isCorrect()) count = count.inc()
            }
        }
        return count
    }

    fun getInputType(): Int {
        return if (gameSettings.suggestionsActivated)
            InputType.TYPE_CLASS_TEXT
        else
            InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    fun addScore(amount: Int) {
        score += amount
    }

    fun getScore(): Int {
        return score
    }

    fun resetGame() {
        score = 0
        typedWordsList.clear()
    }

    fun getInterstitialAdsId(): String {
        if (BuildConfig.DEBUG) {
            return "ca-app-pub-3940256099942544/1033173712" // demo ad unit from Google
        }
        return Config.INTERSTITIAL_ID
    }

    fun getAmountOfLoadedWordsRequired(): Int {
        return max((250.0 * ((gameSettings.time) / 60.0)), 100.0).toInt()
    }

    fun getDictionaryPath(): String {
        val assetPathResolver = DictionaryAssetPathResolver(
            (gameSettings as GameSettings.ForRandomlyGeneratedWords).dictionaryType,
            (gameSettings as GameSettings.ForRandomlyGeneratedWords).languageCode
        )
        return assetPathResolver.getPath()
    }

    fun generateRandomWordsResult(completionDateTime: Long): RandomWords {
        if (gameSettings !is GameSettings.ForRandomlyGeneratedWords)
            throw IllegalStateException("The method was called with wrong game settings: " + gameSettings.javaClass.name)
        val rndSettings = gameSettings as GameSettings.ForRandomlyGeneratedWords
        return RandomWords(
            WpmCalculationImpl(rndSettings.time, getScore()).calculate(),
            CpmCalculationImpl(rndSettings.time, getScore()).calculate(),
            getScore(),
            rndSettings.time,
            rndSettings.languageCode,
            DictionaryTypeConverter().convertToString(rndSettings.dictionaryType),
            ScreenOrientationConverter().convertToString(rndSettings.screenOrientation),
            rndSettings.suggestionsActivated,
            rndSettings.ignoreCase,
            getTypedWords().size,
            calculateCorrectWords(),
            completionDateTime,
            rndSettings.seed
        )
    }

    fun generateOwnTextResult(secondsPassed: Int, completionDateTime: Long) : OwnText {
        if (gameSettings !is GameSettings.ForUserText)
            throw IllegalStateException("The method was called with wrong game settings: " + gameSettings.javaClass.name)
            val userTextSettings = gameSettings as GameSettings.ForUserText
            return OwnText(
                userTextSettings.userText,
                WpmCalculationImpl(secondsPassed, getScore()).calculate(),
                CpmCalculationImpl(secondsPassed, getScore()).calculate(),
                userTextSettings.time,
                secondsPassed,
                getScore(),
                userTextSettings.suggestionsActivated,
                ScreenOrientationConverter().convertToString(userTextSettings.screenOrientation),
                userTextSettings.ignoreCase,
                completionDateTime,
                getTypedWords().size,
                calculateCorrectWords()
            )
    }

    fun generateText(assetManager: AssetManager): String {
        return when (gameSettings) {
            is GameSettings.ForRandomlyGeneratedWords -> {
                TextSourceFromAsset(
                    AssetStringReader(assetManager),
                    getDictionaryPath(),
                    getAmountOfLoadedWordsRequired(),
                    (gameSettings as GameSettings.ForRandomlyGeneratedWords).seed
                ).getString()
            }

            is GameSettings.ForUserText -> {
                TextSourceUserText((gameSettings as GameSettings.ForUserText).userText).getString()
            }

            else -> ""
        }
    }
}