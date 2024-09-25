package com.tetsoft.typego.history.presentation

import android.view.animation.AnimationSet
import androidx.lifecycle.ViewModel
import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.history.domain.GameHistory
import com.tetsoft.typego.history.data.OwnTextGameHistoryStorage
import com.tetsoft.typego.history.data.RandomWordsHistoryStorage
import com.tetsoft.typego.core.utils.AnimationsPreset
import com.tetsoft.typego.history.data.GameHistoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class GameHistoryViewModel @Inject constructor(
    private val randomWordsHistoryStorage: RandomWordsHistoryStorage,
    private val ownTextGameHistoryStorage: OwnTextGameHistoryStorage
) :
    ViewModel() {

    val gameHistory: GameHistory
        get() = GameHistoryImpl(
            randomWordsHistoryStorage.get(),
            ownTextGameHistoryStorage.get()
        )

    fun getHistory(): List<GameResult> {
        return gameHistory.getAllResults().reversed()
    }

    fun historyCanBeShown() = gameHistory.getAllResults().isNotEmpty()

    fun averageWpmCanBeShown(): Boolean = gameHistory.getAllResults().size >= 5

    fun getAverageWpmString(): String {
        if (!averageWpmCanBeShown()) return "-"
        var wpmSum = 0.0
        for (res in gameHistory.getAllResults())
            wpmSum += res.getWpm()
        if (wpmSum == 0.0) return "0"
        return (wpmSum / gameHistory.getAllResults().size).roundToInt().toString()
    }

    fun getBestWpmString(): String {
        if (gameHistory.getAllResults().isEmpty()) return "-"
        return gameHistory.getBestResult().getWpm().roundToInt().toString()
    }

    fun getTotalResultsCountString(): String {
        if (gameHistory.getAllResults().isEmpty()) return "-"
        return gameHistory.getAllResults().size.toString()
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