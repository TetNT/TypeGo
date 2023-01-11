package com.tetsoft.typego.ui.fragment.result

import android.graphics.Color
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.Word
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.PrebuiltTextGameMode
import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.game.mode.GameOnCount
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.GameResultListStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.roundToInt

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val resultListStorage: GameResultListStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage
) : ViewModel() {

    private val _gameMode = MutableLiveData<GameMode>()

    val gameMode : GameMode get() = _gameMode.value!!

    var result : GameResult? = null

    val selectedList = MutableLiveData<List<Word>>(ArrayList())

    private val resultList get() = resultListStorage.get()

    private val gameCompleted = MutableLiveData(false)

    val isGameCompleted : Boolean get() = gameCompleted.value ?: false

    fun setGameCompleted(boolean: Boolean) {
        gameCompleted.postValue(boolean)
    }

    fun selectGameMode(gameMode: GameMode) {
        _gameMode.postValue(gameMode)
    }

    fun selectTypedWordsList(list: List<Word>) {
        selectedList.value = list
    }

    fun hasWordsLog() = selectedList.value?.isNotEmpty() ?: false

    fun getCorrectWords() : Int {
        return result?.correctWords ?: 0
    }

    fun getIncorrectWords() : Int {
        return result?.wordsWritten?.minus(getCorrectWords()) ?: 0
    }

    fun getVisibilityForResult(wpmResult : Int) : Int {
        if (wpmResult == 0) {
            return View.GONE
        }
        return View.VISIBLE
    }

    fun subtractCurrentWpmAndOtherResult(result: Int) : Int {
        return (requireResult().wpm - result).toInt()
    }

    fun getBestWpmByCurrentLanguage() : Int {
        return resultList.getBestResultByLanguage(getLanguage())
    }

    fun getVisibilityForDifference(difference : Int) : Int {
        if (difference == 0) {
            return View.GONE
        }
        return View.VISIBLE
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
        return resultList.getPreviousResultByLanguage(getLanguage())
    }

    fun getWpm() : Int {
        return result?.wpm?.roundToInt() ?: 0
    }

    fun getLanguage() : Language {
        return if (isPrebuiltGameMode) {
            (result?.gameMode as PrebuiltTextGameMode).getLanguage()
        } else Language("ALL")
    }

    fun getDictionary() : DictionaryType {
        if (isPrebuiltGameMode) {
            return (gameMode as PrebuiltTextGameMode).getDictionary()
        } else throw IllegalStateException("Only prebuilt game modes have Dictionary type value.")
    }

    fun getScreenOrientation() : ScreenOrientation {
        return gameMode.screenOrientation
    }

    fun getTimeInSeconds() : Int {
        return if (isGameOnTime) {
            (result?.gameMode as GameOnTime).timeMode.timeInSeconds
        } else 0
    }

    fun resultIsValid() : Boolean {
        return (result != null && result!!.wpm > 0)
    }

    fun saveResult() {
        resultListStorage.addResult(result!!)
    }

    fun getEarnedAchievementsCount(achievementsList: AchievementsList) : Int {
        var newAchievements = 0
        val completedAchievements = achievementsProgressStorage.getAll()
        viewModelScope.launch {
            for (achievement in achievementsList.get()) {
                 if (!completedAchievements[achievement.id].isCompleted() &&
                    achievement.requirementsAreComplete(resultList)) {
                    achievementsProgressStorage.store(achievement.id.toString(), Calendar.getInstance().time.time)
                    newAchievements++
                }
            }
        }
        return newAchievements
    }

    val isGameOnTime get() = result?.gameMode is GameOnTime
    val isGameOnCount get() = result?.gameMode is GameOnCount
    val isPrebuiltGameMode get() = result?.gameMode is PrebuiltTextGameMode

    private fun requireResult() : GameResult {
        if (result == null) throw NullPointerException("Result has not been set!")
        return result as GameResult
    }

    companion object {
        const val PAGE_SIZE = 10
    }

}