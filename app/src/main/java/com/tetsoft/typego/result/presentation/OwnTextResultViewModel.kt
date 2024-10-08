package com.tetsoft.typego.result.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetsoft.typego.R
import com.tetsoft.typego.achievements.data.AchievementsProgressStorage
import com.tetsoft.typego.achievements.domain.AchievementsList
import com.tetsoft.typego.core.domain.GameSettings
import com.tetsoft.typego.core.domain.OwnText
import com.tetsoft.typego.core.data.ScreenOrientation
import com.tetsoft.typego.core.data.Word
import com.tetsoft.typego.history.data.DataSelectorImpl
import com.tetsoft.typego.history.data.GameHistoryImpl
import com.tetsoft.typego.history.data.OwnTextGameHistoryStorage
import com.tetsoft.typego.history.data.RandomWordsHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class OwnTextResultViewModel @Inject constructor(
    private val randomWordsHistoryStorage: RandomWordsHistoryStorage,
    private val ownTextGameHistoryStorage: OwnTextGameHistoryStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage
) : ViewModel() {

    // FIXME: remove that and inject gameHistory properly
    private val gameHistory get() = GameHistoryImpl(randomWordsHistoryStorage.get(), ownTextGameHistoryStorage.get())

    private var ownTextResult: OwnText = OwnText.Empty()
    fun setOwnTextResult(result: OwnText) {
        ownTextResult = result
    }

    private var typedWordsList : List<Word> = emptyList()
    fun setTypedWordsList(list: List<Word>) {
        typedWordsList = list
    }

    fun getTypedWordsList(): List<Word> {
        return typedWordsList
    }

    fun saveResult() {
        if (resultIsValid() && !resultIsAlreadyStored()) {
            ownTextGameHistoryStorage.add(ownTextResult)
        }
    }

    fun resultIsValid() : Boolean {
        return (ownTextResult !is OwnText.Empty && ownTextResult.getWpm() != 0.0)
    }

    fun resultIsAlreadyStored() : Boolean {
        return ownTextGameHistoryStorage.get().find {
            it.getCompletionDateTime() == ownTextResult.getCompletionDateTime()
        } !== null
    }

    fun getEarnedAchievementsCount(): Int {
        var newAchievements = 0
        val completedAchievements = achievementsProgressStorage.getAll()
        viewModelScope.launch {
            for (achievement in AchievementsList.get()) {
                if (!completedAchievements[achievement.getId()].isCompleted() &&
                    achievement.isCompleted(gameHistory)
                ) {
                    achievementsProgressStorage.store(achievement.getId().toString(), Calendar.getInstance().time.time)
                    newAchievements++
                }
            }
        }
        return newAchievements
    }

    fun getWpm(): Int {
        return ownTextResult.getWpm().roundToInt()
    }

    fun getLastResultOrBlank(): String {
        val lastResult = DataSelectorImpl(ownTextGameHistoryStorage.get()).getLastResult()
        return lastResult?.getWpm()?.roundToInt()?.toString() ?: "-"
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
        val lastResult = DataSelectorImpl(ownTextGameHistoryStorage.get()).getLastResult()?.getWpm()?.roundToInt()
        return if (lastResult == null)
            null
        else
            ownTextResult.getWpm().roundToInt() - lastResult
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
        val bestResult = DataSelectorImpl(ownTextGameHistoryStorage.get()).getBestResult()?.getWpm()?.roundToInt()
        return if (bestResult == null)
            null
        else
            ownTextResult.getWpm().roundToInt() - bestResult
    }

    fun getBestResultOrBlank(): String {
        val bestResult = DataSelectorImpl(ownTextGameHistoryStorage.get()).getBestResult()
        return bestResult?.getWpm()?.roundToInt()?.toString() ?: "-"
    }

    fun getCurrentCpm(): String {
        return ownTextResult.getCpm().toString()
    }

    fun getWordsWritten(): String {
        return ownTextResult.getWordsWritten().toString()
    }

    fun getCorrectWordsCount(): Int {
        return ownTextResult.getCorrectWords()
    }

    fun getUserText(): String {
        return ownTextResult.getText()
    }

    fun getTimeSpent(): Int {
        return ownTextResult.getTimeSpent()
    }

    fun getChosenTime(): Int {
        return ownTextResult.getChosenTimeInSeconds()
    }

    fun getScreenOrientation(): ScreenOrientation {
        return ownTextResult.getScreenOrientation()
    }

    fun suggestionsActivated() : Boolean {
        return ownTextResult.areSuggestionsActivated()
    }

    fun getGameSettings(): GameSettings.ForUserText {
        return ownTextResult.toSettings() as GameSettings.ForUserText
    }

}