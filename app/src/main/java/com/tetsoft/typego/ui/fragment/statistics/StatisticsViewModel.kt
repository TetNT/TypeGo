package com.tetsoft.typego.ui.fragment.statistics

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import com.tetsoft.typego.data.history.ClassicGameHistoryDataSelector
import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.history.GameOnTimeDataSelector
import com.tetsoft.typego.data.history.GameOnTimeHistoryList
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.statistics.AccuracyStatistics
import com.tetsoft.typego.data.statistics.AverageCurrentWpmStatistics
import com.tetsoft.typego.data.statistics.AveragePastWpmStatistics
import com.tetsoft.typego.data.statistics.BestResultStatistics
import com.tetsoft.typego.data.statistics.DaysSinceFirstTestStatistics
import com.tetsoft.typego.data.statistics.DaysSinceNewRecordStatistics
import com.tetsoft.typego.data.statistics.DoneAchievementCountStatistics
import com.tetsoft.typego.data.statistics.DoneAchievementsPercentageStatistics
import com.tetsoft.typego.data.statistics.FavoriteLanguageStatistics
import com.tetsoft.typego.data.statistics.FavoriteTimeModeStatistics
import com.tetsoft.typego.data.statistics.LastCompletedAchievementStatistics
import com.tetsoft.typego.data.statistics.PoolEnhancement
import com.tetsoft.typego.data.statistics.ProgressionStatistics
import com.tetsoft.typego.data.statistics.Statistics
import com.tetsoft.typego.data.statistics.TimeSpentStatistics
import com.tetsoft.typego.data.statistics.TotalWordsWrittenStatistics
import com.tetsoft.typego.data.statistics.calculation.AccuracyCalculation
import com.tetsoft.typego.data.statistics.calculation.AverageCurrentWpmCalculation
import com.tetsoft.typego.data.statistics.calculation.AveragePastWpmCalculation
import com.tetsoft.typego.data.statistics.calculation.BestResultCalculation
import com.tetsoft.typego.data.statistics.calculation.DaysSinceFirstTestCalculation
import com.tetsoft.typego.data.statistics.calculation.DaysSinceNewRecordCalculation
import com.tetsoft.typego.data.statistics.calculation.DoneAchievementsCountCalculation
import com.tetsoft.typego.data.statistics.calculation.DoneAchievementsPercentageCalculation
import com.tetsoft.typego.data.statistics.calculation.FavoriteLanguageCalculation
import com.tetsoft.typego.data.statistics.calculation.FavoriteTimeModeCalculation
import com.tetsoft.typego.data.statistics.calculation.LastCompletedAchievementCalculation
import com.tetsoft.typego.data.statistics.calculation.ProgressionCalculation
import com.tetsoft.typego.data.statistics.calculation.TimeSpentCalculation
import com.tetsoft.typego.data.statistics.calculation.TotalWordsWrittenCalculation
import com.tetsoft.typego.storage.AchievementsProgressStorage
import com.tetsoft.typego.storage.history.GameOnNumberOfWordsHistoryStorage
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val gameOnTimeHistoryStorage: GameOnTimeHistoryStorage,
    private val gameOnNumberOfWordsHistoryStorage: GameOnNumberOfWordsHistoryStorage,
    private val achievementsProgressStorage: AchievementsProgressStorage
) : ViewModel() {

    val classicGameModesHistoryList get() = ClassicGameModesHistoryList(gameOnTimeHistoryStorage, gameOnNumberOfWordsHistoryStorage)

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
                classicGameModesHistoryList,
                RESULTS_DEFAULT_POOL_SIZE,
                PoolEnhancement.Base(
                    classicGameModesHistoryList.size,
                    RESULTS_DEFAULT_POOL_SIZE
                )
            )
        )

    val averageCurrentWpmStatistics
        get() = AverageCurrentWpmStatistics(
            AverageCurrentWpmCalculation(classicGameModesHistoryList, RESULTS_DEFAULT_POOL_SIZE)
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
            TotalWordsWrittenCalculation(classicGameModesHistoryList)
        )

    val accuracyStatistics
        get() = AccuracyStatistics(
            AccuracyCalculation(
                classicGameModesHistoryList
            )
        )

    val timeSpentStatistics
        get() = TimeSpentStatistics(
            TimeSpentCalculation(
                classicGameModesHistoryList
            )
        )

    val bestResultStatistics
        get() = BestResultStatistics(
            BestResultCalculation(
                classicGameModesHistoryList
            )
        )

    val daysSinceNewRecordStatistics
        get() = DaysSinceNewRecordStatistics(
            DaysSinceNewRecordCalculation(
                Calendar.getInstance().timeInMillis,
                ClassicGameHistoryDataSelector(classicGameModesHistoryList).getBestResult()
                    .getCompletionDateTime()
            )
        )

    val daysSinceFirstTestStatistics
        get() = DaysSinceFirstTestStatistics(
            DaysSinceFirstTestCalculation(
                classicGameModesHistoryList,
                Calendar.getInstance().timeInMillis
            )
        )

    val favoriteLanguageStatistics
        get() = FavoriteLanguageStatistics(
            FavoriteLanguageCalculation(classicGameModesHistoryList, Language.LANGUAGE_LIST)
        )

    val favoriteLanguageGamesCount
        get() =
            ClassicGameHistoryDataSelector(classicGameModesHistoryList).getResultsByLanguage(
                favoriteLanguageStatistics.provide().identifier
            ).size

    val favoriteTimeModeStatistics
        get() = FavoriteTimeModeStatistics(
            FavoriteTimeModeCalculation(GameOnTimeHistoryList(gameOnTimeHistoryStorage.get()))
        )

    val favoriteTimeModeGamesCount
        get() =
            GameOnTimeDataSelector(gameOnTimeHistoryStorage.get()).getResultsByTimeMode(
                favoriteTimeModeStatistics.provide().timeInSeconds
            ).size

    val doneAchievementsCountStatistics
        get() = DoneAchievementCountStatistics(
            DoneAchievementsCountCalculation(achievementsProgressStorage.getAll())
        )

    val achievementsCount get() = com.tetsoft.typego.data.achievement.AchievementsList.get().size

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
                com.tetsoft.typego.data.achievement.AchievementsList.get()
            )
        )

    val gamesCount get() = classicGameModesHistoryList.size

}