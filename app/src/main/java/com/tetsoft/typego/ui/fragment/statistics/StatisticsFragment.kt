package com.tetsoft.typego.ui.fragment.statistics

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.tetsoft.typego.R
import com.tetsoft.typego.databinding.FragmentStatisticsBinding
import com.tetsoft.typego.ui.custom.BaseFragment
import com.tetsoft.typego.utils.AnimationManager
import com.tetsoft.typego.utils.Translation

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {

    private val viewModel: StatisticsViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.statsCardLocked.visibility = View.GONE
        binding.statsCardNotEnoughData.visibility = View.GONE
        val translation = Translation(requireContext())
        with(binding) {
            viewModel.toggleStatisticsCard(statsCardProgression, viewModel.progressionStatistics)
            viewModel.toggleStatisticsCard(statsCardAccuracy, viewModel.totalWordsWrittenStatistics)
            viewModel.toggleStatisticsCard(statsCardBestResult, viewModel.bestResultStatistics)
            viewModel.toggleStatisticsCard(statsCardTimeSpent, viewModel.timeSpentStatistics)
            viewModel.toggleStatisticsCard(statsCardPreferences, viewModel.favoriteLanguageStatistics)
            viewModel.toggleStatisticsCard(statsCardAchievement, viewModel.doneAchievementsCountStatistics)
            progression.text = getString(R.string.stats_progression_pl, viewModel.progressionStatistics.provide())
            pastWpm.text = getString(R.string.average_wpm_pl, viewModel.averagePastWpmStatistics.provide())
            currentWpm.text = getString(R.string.average_wpm_pl, viewModel.averageCurrentWpmStatistics.provide())
            tvWordsWritten.text = getString(R.string.stats_words_written_total_pl, viewModel.totalWordsWrittenStatistics.provide())
            tvAccuracy.text = getString(R.string.stats_accuracy_pl, viewModel.accuracyStatistics.provide())
            tvAccuracy.setTextColor(viewModel.getAccuracyTextColor())
            tvStatsBestResult.text = getString(R.string.stats_best_result, viewModel.bestResultStatistics.provide())
            tvStatsRecordSetTime.text = getString(R.string.stats_best_result_set_time, viewModel.daysSinceNewRecordStatistics.provide())
            tvStatsTimeSpentTotal.text = getString(R.string.stats_total_minutes_of_testing_pl, viewModel.timeSpentStatistics.provide())
            tvDaysWithUs.text = getString(R.string.stats_days_since_first_test_pl, viewModel.daysSinceFirstTestStatistics.provide())
            tvFavoriteLanguage.text = getString(R.string.stats_language_pl, translation.get(viewModel.favoriteLanguageStatistics.provide()))
            statsFavoriteLanguageDescription.text =
                getString(R.string.favorite_language_description_pl,
                    viewModel.favoriteLanguageGamesCount,
                    viewModel.gamesCount)
            statsFavoriteTimeMode.text = getString(R.string.stats_time_mode_pl, translation.get(viewModel.favoriteTimeModeStatistics.provide()))
            statsFavoriteTimemodeDescription.text =
                getString(R.string.favorite_timemode_description_pl,
                    viewModel.favoriteTimeModeGamesCount,
                    viewModel.gamesCount)
            statsDoneAchievementsAmount.text = getString(R.string.stats_achievements_done_pl, viewModel.doneAchievementsCountStatistics.provide(), viewModel.achievementsCount)
            statsAchievementProgressBar.progress = viewModel.doneAchievementsPercentageStatistics.provide()
            statsLastEarnedAchievement.text = getString(
                R.string.stats_last_earned_achievement_pl, viewModel.lastCompletedAchievementStatistics.provide().name)
        }

        val hiddenCards = viewModel.getHiddenCardsCount(binding.statsLinearLayout)
        if (hiddenCards == viewModel.getTotalCardsCount(binding.statsLinearLayout)) {
            binding.statsCardNotEnoughData.visibility = View.VISIBLE
        } else if (hiddenCards > 0) {
            binding.tvStatsUnlockedCards.text = getString(R.string.stats_locked_cards, hiddenCards)
            binding.statsCardLocked.visibility = View.VISIBLE
        }
        val animationManager = AnimationManager()
        val pastWpmAnim = animationManager.getCountAnimation(0, viewModel.averagePastWpmStatistics.provide(), 1000)
        val currentWpmAnim = animationManager.getCountAnimation(0, viewModel.averageCurrentWpmStatistics.provide(), 1000)
        val progressionAnim = animationManager.getCountAnimation(0, viewModel.progressionStatistics.provide(), 1000)
        val totalWordsAnim = animationManager.getCountAnimation(0, viewModel.totalWordsWrittenStatistics.provide(), 1000)
        applyCountWPM(pastWpmAnim, binding.pastWpm)
        applyCountWPM(currentWpmAnim, binding.currentWpm)
        applyCountProgression(progressionAnim, binding.progression)
        applyCountWrittenTotal(totalWordsAnim, binding.tvWordsWritten)
        pastWpmAnim.start()
        currentWpmAnim.start()
        progressionAnim.start()
        totalWordsAnim.start()
    }

    private fun applyCountWPM(animator: ValueAnimator, view: TextView) {
        animator.addUpdateListener { animation: ValueAnimator ->
            if (context == null) return@addUpdateListener
            view.text = getString(
                R.string.stats_wpm_pl,
                animation.animatedValue as Int
            )
        }
    }

    private fun applyCountWrittenTotal(animator: ValueAnimator, view: TextView) {
        animator.addUpdateListener { animation: ValueAnimator ->
            if (context == null) return@addUpdateListener
            view.text = getString(
                R.string.stats_words_written_total_pl,
                animation.animatedValue as Int
            )
        }
    }

    private fun applyCountProgression(animator: ValueAnimator, view: TextView) {
        animator.addUpdateListener { animation: ValueAnimator ->
            if (context == null) return@addUpdateListener
            view.text = getString(
                R.string.stats_progression_pl,
                animation.animatedValue as Int
            )
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStatisticsBinding {
        return FragmentStatisticsBinding.inflate(layoutInflater, container, false)
    }

}