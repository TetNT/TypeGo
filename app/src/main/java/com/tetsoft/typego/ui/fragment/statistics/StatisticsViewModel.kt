package com.tetsoft.typego.ui.fragment.statistics

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.achievement.AchievementsList
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
    private val achievementsProgressStorage: AchievementsProgressStorage,
    private val achievementsList: AchievementsList
) : ViewModel() {

    companion object {
        const val RESULTS_DEFAULT_POOL_SIZE = 5
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

    val averagePastWpmStatistics get() = AveragePastWpmStatistics(
        AveragePastWpmCalculation(
            gameResultListStorage.get(),
            RESULTS_DEFAULT_POOL_SIZE,
            PoolEnhancement.Base(gameResultListStorage.get().size,
                RESULTS_DEFAULT_POOL_SIZE)
        )
    )

    val averageCurrentWpmStatistics get() = AverageCurrentWpmStatistics(
        AverageCurrentWpmCalculation(gameResultListStorage.get(), RESULTS_DEFAULT_POOL_SIZE)
    )

    val progressionStatistics get() = ProgressionStatistics(
        ProgressionCalculation(averagePastWpmStatistics.provide(), averageCurrentWpmStatistics.provide())
    )

    val totalWordsWrittenStatistics get() = TotalWordsWrittenStatistics(
        TotalWordsWrittenCalculation(gameResultListStorage.get())
    )

    val accuracyStatistics get() = AccuracyStatistics(AccuracyCalculation(gameResultListStorage.get()))

    val timeSpentStatistics get() = TimeSpentStatistics(TimeSpentCalculation(gameResultListStorage.get()))

    val bestResultStatistics get() = BestResultStatistics(BestResultCalculation(gameResultListStorage.get()))

    val daysSinceNewRecordStatistics get() = DaysSinceNewRecordStatistics(
        DaysSinceNewRecordCalculation(Calendar.getInstance().timeInMillis,
            gameResultListStorage.get().bestResult.completionDateTime
        )
    )

    val daysSinceFirstTestStatistics get() = DaysSinceFirstTestStatistics(
        DaysSinceFirstTestCalculation(gameResultListStorage.get(), Calendar.getInstance().timeInMillis)
    )

    val favoriteLanguageStatistics get() = FavoriteLanguageStatistics(
        FavoriteLanguageCalculation(gameResultListStorage.get(), LanguageList().getList())
    )

    val favoriteLanguageGamesCount get() =
        gameResultListStorage.get().getResultsByLanguage(favoriteLanguageStatistics.provide()).size

    val favoriteTimeModeStatistics get() = FavoriteTimeModeStatistics(
        FavoriteTimeModeCalculation(gameResultListStorage.get())
    )

    val favoriteTimeModeGamesCount get() =
        gameResultListStorage.get().getResultsByTimeMode(favoriteTimeModeStatistics.provide()).size

    val doneAchievementsCountStatistics get() = DoneAchievementCountStatistics(
        DoneAchievementsCountCalculation(achievementsProgressStorage.getAll())
    )

    val achievementsCount get() = achievementsList.size

    val doneAchievementsPercentageStatistics get() = DoneAchievementsPercentageStatistics(
        DoneAchievementsPercentageCalculation(achievementsProgressStorage.getAll(), achievementsCount)
    )

    val lastCompletedAchievementStatistics get() = LastCompletedAchievementStatistics(
        LastCompletedAchievementCalculation(achievementsProgressStorage.getAll(), achievementsList))

    val gamesCount get() = gameResultListStorage.get().size

}