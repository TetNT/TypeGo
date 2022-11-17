package com.tetsoft.typego.ui.fragment.statistics

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.data.statistics.*
import com.tetsoft.typego.data.statistics.calculation.*
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.GameResultListStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val gameResultListStorage: GameResultListStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage
) : ViewModel() {

    companion object {
        const val RESULTS_DEFAULT_POOL_SIZE = 20
    }

    fun toggleStatisticsCard(
        cardView: CardView,
        statisticsCalculation: Statistics
    ) {
        cardView.visibility = statisticsCalculation.getVisibility().get()
    }

    fun getTotalCardsCount(parent: LinearLayout): Int {
        var total = 0
        for (i in 0 until parent.childCount) {
            val childView: View = parent.getChildAt(i)
            if (childView is CardView) {
                total = total.inc()
            }
        }
        return total
    }

    fun getHiddenCardsCount(parent: LinearLayout): Int {
        var hidden = 0
        for (i in 0 until parent.childCount) {
            val childView: View = parent.getChildAt(i)
            if (childView is CardView && childView.visibility == View.GONE) {
                hidden = hidden.inc()
            }
        }
        return hidden
    }

    fun getAccuracyTextColor(): Int {
        val accuracy = accuracyStatistics.provide()
        return when {
            accuracy < 50 -> Color.rgb(128, 0, 0)
            accuracy < 80 -> Color.rgb(255, 255, 0)
            else -> Color.rgb(0, 192, 0)
        }
    }

    val averagePastWpmStatistics = AveragePastWpmStatistics(
        AveragePastWpmCalculation(gameResultListStorage.get(), RESULTS_DEFAULT_POOL_SIZE)
    )

    val averageCurrentWpmStatistics = AverageCurrentWpmStatistics(
        AverageCurrentWpmCalculation(gameResultListStorage.get(), RESULTS_DEFAULT_POOL_SIZE)
    )

    val progressionStatistics = ProgressionStatistics(
        ProgressionCalculation(averagePastWpmStatistics.provide(), averageCurrentWpmStatistics.provide())
    )

    val totalWordsWrittenStatistics = TotalWordsWrittenStatistics(
        TotalWordsWrittenCalculation(gameResultListStorage.get())
    )

    val accuracyStatistics = AccuracyStatistics(AccuracyCalculation(gameResultListStorage.get()))

    val timeSpentStatistics = TimeSpentStatistics(TimeSpentCalculation(gameResultListStorage.get()))

    val bestResultStatistics = BestResultStatistics(BestResultCalculation(gameResultListStorage.get()))

    val daysSinceNewRecordStatistics = DaysSinceNewRecordStatistics(
        DaysSinceNewRecordCalculation(Calendar.getInstance().timeInMillis,
            gameResultListStorage.get().bestResult.completionDateTime
        )
    )

    val daysSinceFirstTestStatistics = DaysSinceFirstTestStatistics(
        DaysSinceFirstTestCalculation(gameResultListStorage.get(), Calendar.getInstance().timeInMillis)
    )

    val favoriteLanguageStatistics = FavoriteLanguageStatistics(
        FavoriteLanguageCalculation(gameResultListStorage.get(), LanguageList().getList())
    )

    val favoriteLanguageGamesCount =
        gameResultListStorage.get().getResultsByLanguage(favoriteLanguageStatistics.provide()).size

    val favoriteTimeModeStatistics = FavoriteTimeModeStatistics(
        FavoriteTimeModeCalculation(gameResultListStorage.get())
    )

    val favoriteTimeModeGamesCount =
        gameResultListStorage.get().getResultsByTimeMode(favoriteTimeModeStatistics.provide()).size

    val doneAchievementsCountStatistics = DoneAchievementCountStatistics(
        DoneAchievementsCountCalculation(achievementsProgressStorage.getAll())
    )

    val doneAchievementsPercentageStatistics = DoneAchievementsPercentageStatistics(
        DoneAchievementsPercentageCalculation(achievementsProgressStorage.getAll())
    )

    //val lastCompletedAchievementStatistics = LastCompletedAchievementStatistics(
    //    LastCompletedAchievementCalculation(achievementsProgressStorage.getAll(), AchievementList())

    val gamesCount = gameResultListStorage.get().size

    val achievementsCount = achievementsProgressStorage.getAll().size

}