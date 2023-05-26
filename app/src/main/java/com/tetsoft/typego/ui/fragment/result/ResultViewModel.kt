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
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.roundToInt

@HiltViewModel
@Deprecated("To be deleted")
class ResultViewModel @Inject constructor(
    private val resultListStorage: GameOnTimeHistoryStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage
) : ViewModel() {

    private val _gameMode = MutableLiveData<GameMode>()

    //val gameMode : GameMode get() = _gameMode.value ?: GameMode.Empty()

    //var result : GameResult? = null

    var result : com.tetsoft.typego.game.GameOnTime? = null

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
        return result?.getCorrectWords() ?: 0
    }

    fun getIncorrectWords() : Int {
        return result?.getWordsWritten()?.minus(getCorrectWords()) ?: 0
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
        //return resultList.getBestResultByLanguage(getLanguage())
        return 0
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
        //return resultList.getPreviousResultByLanguage(getLanguage())
        return 0
    }

    fun getWpm() : Int {
        return result?.getWpm()?.roundToInt() ?: 0
    }

    fun getCpm() : Int {
        return result?.getCpm() ?: 0
    }

    fun getLanguage() : Language {
        return Language(result?.getLanguageCode() ?: "ALL")
    }

    fun getDictionary() : DictionaryType {
        return result?.getDictionaryType() ?: DictionaryType.BASIC
    }

    fun getScreenOrientation() : ScreenOrientation {
        return result?.getScreenOrientation() ?: ScreenOrientation.PORTRAIT
    }

    fun getTimeInSeconds() : Int {
        return result?.getTimeSpent() ?: 0
    }

    fun resultIsValid() : Boolean {
        return (result != null && result!!.getWpm() > 0)
    }

    fun saveResult() {
        if (result != null) {
            resultListStorage.add(result!!)
        }
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

    private fun requireResult() : GameResult {
        if (result == null) throw NullPointerException("Result has not been set!")
        return result as GameResult
    }

}