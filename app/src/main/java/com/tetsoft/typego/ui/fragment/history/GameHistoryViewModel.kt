package com.tetsoft.typego.ui.fragment.history

import android.view.animation.AnimationSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.history.GameHistoryList
import com.tetsoft.typego.data.history.GameOnTimeDataSelector
import com.tetsoft.typego.data.history.GameOnTimeHistoryFilter
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import com.tetsoft.typego.utils.AnimationsPreset
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class GameHistoryViewModel @Inject constructor(private val gameOnTimeHistoryStorage: GameOnTimeHistoryStorage) :
    ViewModel() {

    private val mutableSelectedLanguage = MutableLiveData<Language>()

    val selectedLanguage: LiveData<Language> = mutableSelectedLanguage

    fun selectLanguage(language: Language) {
        mutableSelectedLanguage.postValue(language)
    }

    fun getOnTimeHistory(language: Language): GameHistoryList<GameOnTime> {
        return GameOnTimeHistoryFilter(gameOnTimeHistoryStorage.get()).byLanguage(language)
            .inDescendingOrder().getList()
    }

    fun historyCanBeShown(gameResultList: GameHistoryList<GameOnTime>) = gameResultList.isNotEmpty()

    fun averageWpmCanBeShown(gameResultList: GameHistoryList<GameOnTime>): Boolean =
        gameResultList.size >= 5

    fun getAverageWpm(gameResultList: GameHistoryList<GameOnTime>): Int {
        var wpmSum = 0.0
        for (res in gameResultList)
            wpmSum += res.getWpm()
        if (wpmSum == 0.0 || gameResultList.isEmpty()) return 0
        return (wpmSum / gameResultList.size).roundToInt()
    }

    fun getBestWpm(gameResultList: GameHistoryList<GameOnTime>): Int {
        return GameOnTimeDataSelector(gameResultList).getBestResult().getWpm().roundToInt()
    }

    fun getGameHistoryEnteringAnimation(): AnimationSet {
        val animationSet = AnimationSet(false)
        animationSet.addAnimation(AnimationsPreset.SlideIn(ANIMATION_DURATION, 0f, 50f).get())
        animationSet.addAnimation(AnimationsPreset.FadeIn(ANIMATION_DURATION).get())
        return animationSet
    }

    private companion object {
        const val ANIMATION_DURATION = 500L
    }
}