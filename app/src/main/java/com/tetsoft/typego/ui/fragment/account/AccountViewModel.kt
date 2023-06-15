package com.tetsoft.typego.ui.fragment.account

import android.view.animation.Animation
import android.view.animation.AnimationSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.history.GameHistoryList
import com.tetsoft.typego.data.history.GameOnTimeHistoryFilter
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import com.tetsoft.typego.utils.AnimationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
@Deprecated("Replaced with the GameHistoryViewModel. To be deleted.")
class AccountViewModel @Inject constructor(private val gameOnTimeHistoryStorage: GameOnTimeHistoryStorage) :
    ViewModel() {

    //private val resultList get() = gameResultListStorage.get()

    private val onTimeResultList get() = gameOnTimeHistoryStorage.get()

    private val mutableSelectedLanguage = MutableLiveData<Language>()

    val selectedLanguage : LiveData<Language> = mutableSelectedLanguage

    fun selectLanguage(language: Language) {
        mutableSelectedLanguage.postValue(language)
    }

//    fun getResults(language: Language, inDescendingOrder: Boolean): GameResultList {
//        val byLang = resultList.getResultsByLanguage(language)
//        return if (inDescendingOrder) {
//            byLang.inDescendingOrder
//        } else byLang
//    }

    fun getOnTimeHistory(language: Language) : GameHistoryList<GameOnTime> {
        return GameOnTimeHistoryFilter(onTimeResultList).byLanguage(language).inDescendingOrder().getList()
    }

    fun historyCanBeShown(gameResultList: GameHistoryList<GameOnTime>) = gameResultList.isNotEmpty()

    fun averageWpmCanBeShown(gameResultList: GameHistoryList<GameOnTime>): Boolean = gameResultList.size >= 5

    fun getAverageWpm(gameResultList: GameHistoryList<GameOnTime>): Int {
        var wpmSum = 0.0
        for (res in gameResultList)
            wpmSum += res.getWpm()
        if (wpmSum == 0.0 || gameResultList.isEmpty()) return 0
        return (wpmSum / gameResultList.size).roundToInt()
    }

    fun getGameHistoryEnteringAnimation() : AnimationSet {
        val animationManager = AnimationManager()
        val slideIn: Animation = animationManager.getSlideInAnimation(0f, 50f, 500)
        val fadeIn: Animation = animationManager.getFadeInAnimation(500)
        val animationSet = AnimationSet(false)
        animationSet.addAnimation(slideIn)
        animationSet.addAnimation(fadeIn)
        return animationSet
    }

    fun getBestResultWpm(gameResultList: GameHistoryList<GameOnTime>): Int {
        return gameResultList.maxOf { result -> result.getWpm() }.toInt()
    }
}