package com.tetsoft.typego.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.tetsoft.typego.AnimationManager
import com.tetsoft.typego.R
import com.tetsoft.typego.account.Statistics
import com.tetsoft.typego.account.User
import com.tetsoft.typego.databinding.FragmentStatisticsBinding
import com.tetsoft.typego.testing.ResultList
import com.tetsoft.typego.testing.ResultListUtils
import com.tetsoft.typego.utils.TimeConvert


class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private var cardsHidden : Int = 0
    lateinit var visibleCards : Collection<CardView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.statsCardLocked.visibility = View.GONE
        binding.statsCardNotEnoughData.visibility = View.GONE
        val currentUser = User.getFromStoredData(requireContext())
        val stats = Statistics(currentUser)
        visibleCards = getVisibleCardsArrayList()
        val cardsCount : Int = visibleCards.size
        displayOrHideProgressionCard(stats)
        displayOrHideAccuracyCard(stats)
        displayOrHideRecordCard(stats)
        displayOrHideTimeSpentCard(stats)
        displayOrHidePreferencesCard(stats)
        displayOrHideAchievementsCard(stats, currentUser)
        if (cardsHidden == cardsCount) {
            binding.statsCardNotEnoughData.visibility = View.VISIBLE
        }
        else if (cardsHidden > 0) {
            binding.tvStatsUnlockedCards.text = getString(R.string.stats_locked_cards, cardsHidden)
            binding.statsCardLocked.visibility = View.VISIBLE
        }
        val animationManager = AnimationManager()
        val pastWpmAnim = animationManager.getCountAnimation(0,stats.averagePastWPM,1000)
        val currentWpmAnim = animationManager.getCountAnimation(0,stats.averageCurrentWPM,1000)
        val progressionAnim = animationManager.getCountAnimation(0,stats.progression,1000)
        val totalWordsAnim = animationManager.getCountAnimation(0,stats.totalWordsWritten,1000)
        applyCountWPM(pastWpmAnim, binding.startWpm)
        applyCountWPM(currentWpmAnim, binding.currentWpm)
        applyCountProgression(progressionAnim, binding.progression)
        applyCountWrittenTotal(totalWordsAnim, binding.tvStatsWordsDescription)
        pastWpmAnim.start()
        currentWpmAnim.start()
        progressionAnim.start()
        totalWordsAnim.start()
    }

    private fun getVisibleCardsArrayList() : ArrayList<CardView> {
        val visibleCards = ArrayList<CardView>()
        for (i in 0 until binding.statsLinearLayout.childCount) {
            val childView: View = binding.statsLinearLayout.getChildAt(i)
            if (childView is CardView && childView.visibility == View.VISIBLE) {
                visibleCards.add(childView)
            }
        }
        return visibleCards
    }

    private fun applyCountWPM(animator: ValueAnimator, view: TextView) {
        animator.addUpdateListener { animation: ValueAnimator ->
            if (context == null) return@addUpdateListener
            view.text = getString(
                    R.string.stats_wpm_pl,
                    animation.animatedValue as Int)
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

    @SuppressLint("StringFormatInvalid")
    private fun applyCountProgression(animator: ValueAnimator, view: TextView) {
        animator.addUpdateListener { animation: ValueAnimator ->
            if (context == null) return@addUpdateListener
            view.text = getString(
                    R.string.stats_progression_pl,
                    animation.animatedValue as Int
            )
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun displayOrHideProgressionCard(stats : Statistics) {
        val progress : Int = stats.progression
        if (progress == 0) {
            binding.statsCardProgression.visibility = View.GONE
            cardsHidden++
            return
        }
        binding.startWpm.text = getString(R.string.stats_wpm_pl, stats.averagePastWPM)
        binding.currentWpm.text = getString(R.string.stats_wpm_pl, stats.averageCurrentWPM)
        binding.progression.text = getString(R.string.stats_progression_pl, progress)
        if (progress < 0) binding.progression.setTextColor(Color.RED)
    }

    private fun displayOrHideAccuracyCard(stats: Statistics) {
        val totalWordsWritten : Int = stats.totalWordsWritten
        if (totalWordsWritten <= 0) {
            binding.statsCardAccuracy.visibility = View.GONE
            cardsHidden++
            return
        }
        val accuracy : Int = stats.accuracy
        binding.tvStatsWordsDescription.text = getString(R.string.stats_words_written_total_pl, totalWordsWritten)
        binding.tvAccuracy.text = getString(R.string.stats_accuracy_pl, accuracy)
        when {
            accuracy < 50 -> binding.tvAccuracy.setTextColor(Color.rgb(128, 0, 0))
            accuracy < 80 -> binding.tvAccuracy.setTextColor(Color.rgb(255, 255, 0))
            else -> binding.tvAccuracy.setTextColor(Color.rgb(0,192,0))
        }
    }

    private fun displayOrHideRecordCard(stats: Statistics) {
        if (stats.bestResult == null) {
            binding.statsCardBestResult.visibility = View.GONE
            cardsHidden++
            return
        }
        binding.tvStatsBestResult.text = getString(R.string.stats_best_result,
            stats.bestResult.wpm.toInt()
        )
        binding.tvStatsRecordSetTime.text = getString(R.string.stats_best_result_set_time, stats.daysSinceRecordHasBeenSet)
    }

    private fun displayOrHideTimeSpentCard(stats : Statistics) {
        val daysSinceFirstTest : Int = stats.daysSinceFirstTest
        if (daysSinceFirstTest < 1) {
            binding.statsCardTimeSpent.visibility = View.GONE
            cardsHidden++
            return
        }
        binding.tvDaysWithUs.text = getString(R.string.stats_days_since_first_test_pl, daysSinceFirstTest)
        binding.tvStatsTimeSpentTotal.text = getString(R.string.stats_total_minutes_of_testing_pl, stats.totalTimeSpentInMinutes)
    }


    private fun displayOrHidePreferencesCard(stats: Statistics) {
        val favLang = stats.getFavoriteLanguage(context)
        val favTimeMode = stats.favoriteTimeMode
        if (favLang == null || favTimeMode == null) {
            binding.statsCardPreferences.visibility = View.GONE
            cardsHidden++
            return
        }
        binding.statsFavoriteLanguage.text = getString(R.string.stats_language_pl,
            context?.let { favLang.getName(it) })
        binding.statsFavoriteTimeMode.text =
            getString(R.string.stats_time_mode_pl,
                context?.let { TimeConvert.convertSeconds(it, favTimeMode.timeInSeconds) })
        binding.statsFavoriteLanguageDescription.text =
            getString(R.string.favorite_language_description_pl,
                ResultListUtils.getResultsByLanguage(stats.results, favLang).size,
                stats.resultsCount
            )
        binding.statsFavoriteTimemodeDescription.text =
            getString(R.string.favorite_timemode_description_pl,
                ResultListUtils.getResultsByTimeMode(stats.results, favTimeMode).size,
                stats.resultsCount
            )
    }

    private fun displayOrHideAchievementsCard(stats: Statistics, currentUser : User) {
        if (stats.doneAchievementsCount == 0) {
            binding.statsCardAchievement.visibility = View.GONE
            cardsHidden++
            return
        }
        binding.statsAchievementProgressBar.progress = stats.achievementsProgressInPercents
        binding.statsDoneAchievementsAmount.text = getString(
                R.string.stats_achievements_done_pl,
                stats.doneAchievementsCount,
                currentUser.achievements.size)
        binding.statsLastEarnedAchievement.text = getString(
                R.string.stats_last_earned_achievement_pl,
                stats.lastCompletedAchievementName)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ANIMATION_DURATION : Long = 500
    }

}