package com.tetsoft.typego.ui.fragment.result

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetsoft.typego.R
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.Word
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.data.game.GameSettings
import com.tetsoft.typego.data.game.RandomWords
import com.tetsoft.typego.data.history.DataSelector
import com.tetsoft.typego.data.history.GameHistory
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.history.OwnTextGameHistoryStorage
import com.tetsoft.typego.storage.history.RandomWordsHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class RandomWordsResultViewModel @Inject constructor(
    private val randomWordsHistoryStorage: RandomWordsHistoryStorage,
    private val ownTextGameHistoryStorage: OwnTextGameHistoryStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage,
) : ViewModel() {

    // FIXME: remove that and inject gameHistory properly
    private val gameHistory get() = GameHistory.Standard(randomWordsHistoryStorage.get(), ownTextGameHistoryStorage.get())

    var wordsList = emptyList<Word>()

    private var result : RandomWords = RandomWords.Empty()

    fun setRandomWordsResult(randomWords: RandomWords) {
        result = randomWords
    }

    var isGameCompleted : Boolean = false

    fun getWpm() : Int {
        return result.getWpm().roundToInt()
    }

    fun getCpm() : String {
        return result.getCpm().toString()
    }

    fun getLanguage() : Language {
        return Language(result.getLanguageCode())
    }

    fun getDictionary() : DictionaryType {
        return result.getDictionaryType()
    }

    fun getScreenOrientation() : ScreenOrientation {
        return result.getScreenOrientation()
    }

    fun getTimeInSeconds() : Int {
        return result.getChosenTimeInSeconds()
    }

    fun getEarnedAchievementsCount() : Int {
        var newAchievements = 0
        val completedAchievements = achievementsProgressStorage.getAll()
        viewModelScope.launch {
            for (achievement in AchievementsList.get()) {
                if (!completedAchievements[achievement.getId()].isCompleted() &&
                    achievement.isCompleted(gameHistory)) {
                    achievementsProgressStorage.store(achievement.getId().toString(), Calendar.getInstance().time.time)
                    newAchievements++
                }
            }
        }
        return newAchievements
    }

    fun areSuggestionsActivated(): Boolean {
        return result.areSuggestionsActivated()
    }

    fun getSeedOrBlankSign(): String {
        if (seedIsEmpty()) return "-"
        return result.getSeed()
    }

    fun seedIsEmpty() : Boolean {
        return result.getSeed().isEmpty()
    }

    fun saveResultIfValid() : Boolean {
        if (result is RandomWords.Empty ||
            result.getWpm() == 0.0 ||
            randomWordsHistoryStorage.get().find { it.getCompletionDateTime() == result.getCompletionDateTime() } !== null)
            return false
        randomWordsHistoryStorage.add(result)
        return true
    }

    fun getWordsWritten(): String {
        return result.getWordsWritten().toString()
    }

    fun getCorrectWordsCount() : Int {
        return result.getCorrectWords()
    }

    fun getLastResultOrBlank(): String {
        val lastResult = DataSelector.Standard(randomWordsHistoryStorage.get()).getLastResult()
        return lastResult?.getWpm()?.roundToInt()?.toString() ?: "-"
    }

    fun getTypedWordsList(): List<Word> {
        return wordsList
    }

    fun getGameSettings(): GameSettings.ForRandomlyGeneratedWords {
        return result.toSettings() as GameSettings.ForRandomlyGeneratedWords
    }

    @StringRes
    fun getLastResultDifferenceStringResId() : Int  {
        val currentToLastResultDifference = getLastResultDifference()
        return if (currentToLastResultDifference == null)
            R.string.no_last_results
        else if (currentToLastResultDifference > 0)
            R.string.current_to_last_wpm_more
        else if (currentToLastResultDifference < 0)
            R.string.current_to_last_wpm_less
        else
            R.string.current_to_last_wpm_same
    }

    fun getLastResultDifference(): Int? {
        val lastResult = DataSelector.Standard(randomWordsHistoryStorage.get()).getLastResult()?.getWpm()?.roundToInt()
        return if (lastResult == null)
            null
        else
            result.getWpm().roundToInt() - lastResult
    }

    fun getBestResultOrBlank(): String {
        val bestResult = DataSelector.Standard(randomWordsHistoryStorage.get()).getBestResult()
        return bestResult?.getWpm()?.roundToInt()?.toString() ?: "-"
    }

    @StringRes
    fun getBestResultDifferenceStringResId() : Int  {
        val currentToBestResultDifference = getBestResultDifference()
        return if (currentToBestResultDifference == null)
            R.string.no_last_results
        else if (currentToBestResultDifference > 0)
            R.string.current_to_best_wpm_more
        else if (currentToBestResultDifference < 0)
            R.string.current_to_best_wpm_less
        else
            R.string.current_to_best_wpm_same
    }

    fun getBestResultDifference(): Int? {
        val bestResult = DataSelector.Standard(randomWordsHistoryStorage.get()).getBestResult()?.getWpm()?.roundToInt()
        return if (bestResult == null)
            null
        else
            result.getWpm().roundToInt() - bestResult
    }

}