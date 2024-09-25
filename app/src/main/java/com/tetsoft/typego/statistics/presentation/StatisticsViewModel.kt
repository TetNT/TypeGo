package com.tetsoft.typego.statistics.presentation

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import com.tetsoft.typego.achievements.data.AchievementsProgressStorage
import com.tetsoft.typego.achievements.domain.AchievementsList
import com.tetsoft.typego.core.domain.LanguageList
import com.tetsoft.typego.history.data.GameHistoryImpl
import com.tetsoft.typego.history.data.OwnTextGameHistoryStorage
import com.tetsoft.typego.history.data.RandomWordsHistoryStorage
import com.tetsoft.typego.statistics.data.PoolEnhancementImpl
import com.tetsoft.typego.statistics.data.calculation.AccuracyCalculation
import com.tetsoft.typego.statistics.data.calculation.AverageCurrentWpmCalculation
import com.tetsoft.typego.statistics.data.calculation.AveragePastWpmCalculation
import com.tetsoft.typego.statistics.data.calculation.BestResultCalculation
import com.tetsoft.typego.statistics.data.calculation.DaysSinceFirstTestCalculation
import com.tetsoft.typego.statistics.data.calculation.DaysSinceNewRecordCalculation
import com.tetsoft.typego.statistics.data.calculation.DoneAchievementsCountCalculation
import com.tetsoft.typego.statistics.data.calculation.DoneAchievementsPercentageCalculation
import com.tetsoft.typego.statistics.data.calculation.FavoriteLanguageCalculation
import com.tetsoft.typego.statistics.data.calculation.FavoriteTimeModeCalculation
import com.tetsoft.typego.statistics.data.calculation.LastCompletedAchievementCalculation
import com.tetsoft.typego.statistics.data.calculation.ProgressionCalculation
import com.tetsoft.typego.statistics.data.calculation.TimeSpentCalculation
import com.tetsoft.typego.statistics.data.calculation.TotalWordsWrittenCalculation
import com.tetsoft.typego.statistics.data.statistics.AccuracyStatistics
import com.tetsoft.typego.statistics.data.statistics.AverageCurrentWpmStatistics
import com.tetsoft.typego.statistics.data.statistics.AveragePastWpmStatistics
import com.tetsoft.typego.statistics.data.statistics.BestResultStatistics
import com.tetsoft.typego.statistics.data.statistics.DaysSinceFirstTestStatistics
import com.tetsoft.typego.statistics.data.statistics.DaysSinceNewRecordStatistics
import com.tetsoft.typego.statistics.data.statistics.DoneAchievementCountStatistics
import com.tetsoft.typego.statistics.data.statistics.DoneAchievementsPercentageStatistics
import com.tetsoft.typego.statistics.data.statistics.FavoriteLanguageStatistics
import com.tetsoft.typego.statistics.data.statistics.FavoriteTimeModeStatistics
import com.tetsoft.typego.statistics.data.statistics.LastCompletedAchievementStatistics
import com.tetsoft.typego.statistics.data.statistics.ProgressionStatistics
import com.tetsoft.typego.statistics.data.statistics.TimeSpentStatistics
import com.tetsoft.typego.statistics.data.statistics.TotalWordsWrittenStatistics
import com.tetsoft.typego.statistics.domain.Statistics
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val randomWordsHistoryStorage: RandomWordsHistoryStorage,
    private val ownTextGameHistoryStorage: OwnTextGameHistoryStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage
) : ViewModel() {

    // FIXME: remove that and inject gameHistory properly
    private val gameHistory get() = GameHistoryImpl(randomWordsHistoryStorage.get(), ownTextGameHistoryStorage.get())

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

    val averagePastWpmStatistics
        get() = AveragePastWpmStatistics(
            AveragePastWpmCalculation(
                gameHistory.getAllResults(),
                RESULTS_DEFAULT_POOL_SIZE,
                PoolEnhancementImpl(
                    gameHistory.getAllResults().size,
                    RESULTS_DEFAULT_POOL_SIZE
                )
            )
        )

    val averageCurrentWpmStatistics
        get() = AverageCurrentWpmStatistics(
            AverageCurrentWpmCalculation(gameHistory.getAllResults(), RESULTS_DEFAULT_POOL_SIZE)
        )

    val progressionStatistics
        get() = ProgressionStatistics(
            ProgressionCalculation(
                averagePastWpmStatistics.provide(),
                averageCurrentWpmStatistics.provide()
            )
        )

    fun getProgressionStatistics() : Int {
        return ProgressionStatistics(
            ProgressionCalculation(
                averagePastWpmStatistics.provide(),
                averageCurrentWpmStatistics.provide()
            )
        ).provide()
    }

    val totalWordsWrittenStatistics
        get() = TotalWordsWrittenStatistics(
            TotalWordsWrittenCalculation(gameHistory.getResultsWithWordsInformation())
        )

    val accuracyStatistics
        get() = AccuracyStatistics(
            AccuracyCalculation(
                gameHistory.getResultsWithWordsInformation()
            )
        )

    val timeSpentStatistics
        get() = TimeSpentStatistics(
            TimeSpentCalculation(
                gameHistory.getAllResults()
            )
        )

    val bestResultStatistics
        get() = BestResultStatistics(
            BestResultCalculation(
                gameHistory.getAllResults()
            )
        )

    val daysSinceNewRecordStatistics
        get() = DaysSinceNewRecordStatistics(
            DaysSinceNewRecordCalculation(
                Calendar.getInstance().timeInMillis,
                gameHistory.getBestResult().getCompletionDateTime()
            )
        )

    val daysSinceFirstTestStatistics
        get() = DaysSinceFirstTestStatistics(
            DaysSinceFirstTestCalculation(
                gameHistory.getAllResults(),
                Calendar.getInstance().timeInMillis
            )
        )

    val favoriteLanguageStatistics
        get() = FavoriteLanguageStatistics(
            FavoriteLanguageCalculation(gameHistory.getResultsWithLanguage(), LanguageList().getPlayableLanguages())
        )

    val favoriteLanguageGamesCount
        get() = gameHistory.getResultsWithLanguage().filter {
            it.getLanguageCode() == favoriteLanguageStatistics.provide().identifier
        }.size

    val favoriteTimeModeStatistics
        get() = FavoriteTimeModeStatistics(
            FavoriteTimeModeCalculation(gameHistory.getTimeLimitedResults())
        )

    val favoriteTimeModeGamesCount
        get() =
            gameHistory.getTimeLimitedResults().filter { it.getChosenTimeInSeconds() == favoriteTimeModeStatistics.provide().timeInSeconds }.size

    val doneAchievementsCountStatistics
        get() = DoneAchievementCountStatistics(
            DoneAchievementsCountCalculation(achievementsProgressStorage.getAll())
        )

    val achievementsCount get() = AchievementsList.get().size

    val doneAchievementsPercentageStatistics
        get() = DoneAchievementsPercentageStatistics(
            DoneAchievementsPercentageCalculation(
                achievementsProgressStorage.getAll(),
                achievementsCount
            )
        )

    val lastCompletedAchievementStatistics
        get() = LastCompletedAchievementStatistics(
            LastCompletedAchievementCalculation(
                achievementsProgressStorage.getAll(),
                AchievementsList.get()
            )
        )

    val gamesCount get() = gameHistory.getAllResults().size

}