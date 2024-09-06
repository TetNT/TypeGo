package com.tetsoft.typego.ui.fragment.history

import android.view.animation.AnimationSet
import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.game.GameResult
import com.tetsoft.typego.data.history.GameHistory
import com.tetsoft.typego.storage.history.OwnTextGameHistoryStorage
import com.tetsoft.typego.storage.history.RandomWordsHistoryStorage
import com.tetsoft.typego.utils.AnimationsPreset
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
        get() = GameHistory.Standard(
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