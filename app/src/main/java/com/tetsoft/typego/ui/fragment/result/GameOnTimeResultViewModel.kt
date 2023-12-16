package com.tetsoft.typego.ui.fragment.result

import android.graphics.Color
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.Word
import com.tetsoft.typego.data.achievement.Achievement
import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.history.GameOnTimeDataSelector
import com.tetsoft.typego.data.history.GameOnTimeHistoryFilter
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.history.GameOnNumberOfWordsHistoryStorage
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.roundToInt

@HiltViewModel
class GameOnTimeResultViewModel @Inject constructor(
    private val gameOnTimeHistoryStorage: GameOnTimeHistoryStorage,
    private val gameOnNumberOfWordsHistoryStorage: GameOnNumberOfWordsHistoryStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage,
) : ViewModel() {

    var wordsList = emptyList<Word>()

    var result : GameOnTime = GameOnTime.Empty()

    var isGameCompleted : Boolean = false

    fun getCorrectWords() : Int {
        return result.getCorrectWords()
    }

    fun getIncorrectWords() : Int {
        return result.getWordsWritten().minus(getCorrectWords())
    }

    fun hasWordsLog() = wordsList.isNotEmpty()

    fun getVisibilityForResult(wpmResult : Int) : Int {
        if (wpmResult == 0) {
            return View.GONE
        }
        return View.VISIBLE
    }

    fun getVisibilityForDifference(difference : Int) : Int {
        if (difference == 0) {
            return View.GONE
        }
        return View.VISIBLE
    }

    fun subtractCurrentWpmAndOtherResult(other: Int) : Int {
        return (result.getWpm() - other).toInt()
    }

    fun getDifferenceString(difference: Int) : String {
        if (difference > 0) return "+${abs(difference)}"
        if (difference < 0) return "-${abs(difference)}"
        else return ""
    }

    fun getDifferenceColor(difference: Int) : Int {
        if (difference > 0) return Color.GREEN
        if (difference < 0) return Color.RED
        else return Color.GRAY
    }

    fun getPreviousWpm() : Int {
        val resultsFiltered = GameOnTimeHistoryFilter(gameOnTimeHistoryStorage.get()).byLanguage(getLanguage()).getList()
        // TODO: Change it later to the "getSecondToLastResult()" method.
        return GameOnTimeDataSelector(resultsFiltered).getMostRecentResult().getWpm().roundToInt()
    }

    fun getBestWpmByCurrentLanguage() : Int {
        return GameOnTimeDataSelector(gameOnTimeHistoryStorage.get()).getBestResult().getWpm().roundToInt()
    }

    fun getWpm() : Int {
        return result.getWpm().roundToInt()
    }

    fun getCpm() : Int {
        return result.getCpm()
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
        return result.getTimeSpent()
    }

    fun resultIsValid() : Boolean {
        return (result.getWpm() > 0 && result !is GameOnTime.Empty)
    }

    fun saveResult() {
        if (resultIsValid()) {
            gameOnTimeHistoryStorage.add(result)
        }
    }

    fun getEarnedAchievementsCount(achievementsList: List<Achievement>) : Int {
        var newAchievements = 0

        val completedAchievements = achievementsProgressStorage.getAll()
        val classicGameModesHistoryList = ClassicGameModesHistoryList(gameOnTimeHistoryStorage, gameOnNumberOfWordsHistoryStorage)
        viewModelScope.launch {
            for (achievement in achievementsList) {
                if (!completedAchievements[achievement.getId()].isCompleted() &&
                    achievement.isCompleted(classicGameModesHistoryList)) {
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

    fun seedIsEmpty() = result.getSeed().isEmpty()
}