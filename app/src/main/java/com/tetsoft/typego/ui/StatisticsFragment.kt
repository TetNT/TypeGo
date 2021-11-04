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
import kotlinx.android.synthetic.main.fragment_statistics.*


class StatisticsFragment : Fragment() {

    private var cardsHidden : Int = 0
    lateinit var visibleCards : Collection<CardView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stats_card_locked.visibility = View.GONE
        stats_card_not_enough_data.visibility = View.GONE
        val currentUser = User.getFromStoredData(requireContext())
        val stats = Statistics(currentUser)
        visibleCards = getVisibleCardsArrayList()
        val cardsCount : Int = visibleCards.size
        displayOrHideProgressionCard(stats)
        displayOrHideTimeSpentCard(stats)
        displayOrHideAccuracyCard(stats)
        displayOrHidePreferencesCard(stats)
        displayOrHideAchievementsCard(stats, currentUser)
        if (cardsHidden == cardsCount) {
            stats_card_not_enough_data.visibility = View.VISIBLE
        }
        else if (cardsHidden > 0) {
            tv_stats_unlocked_cards.text = getString(R.string.stats_locked_cards, cardsHidden)
            stats_card_locked.visibility = View.VISIBLE
        }
        val animationManager = AnimationManager()
        val pastWpmAnim = animationManager.getCountAnimation(0,stats.averagePastWPM,2500)
        val currentWpmAnim = animationManager.getCountAnimation(0,stats.averageCurrentWPM,2500)
        val progressionAnim = animationManager.getCountAnimation(0,stats.progression,2500)
        val totalWordsAnim = animationManager.getCountAnimation(0,stats.totalWordsWritten,2500)
        applyCountWPM(pastWpmAnim, start_wpm)
        applyCountWPM(currentWpmAnim, current_wpm)
        applyCountProgression(progressionAnim, progression)
        applyCountWrittenTotal(totalWordsAnim, tv_stats_words_total)
        pastWpmAnim.start()
        currentWpmAnim.start()
        progressionAnim.start()
        totalWordsAnim.start()
    }

    private fun getVisibleCardsArrayList() : ArrayList<CardView> {
        val visibleCards = ArrayList<CardView>()
        for (i in 0 until stats_linear_layout.childCount) {
            val childView: View = stats_linear_layout.getChildAt(i)
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
            stats_card_progression.visibility = View.GONE
            cardsHidden++
            return
        }
        start_wpm.text = getString(R.string.stats_wpm_pl, stats.averagePastWPM)
        current_wpm.text = getString(R.string.stats_wpm_pl, stats.averageCurrentWPM)
        progression.text = getString(R.string.stats_progression_pl, progress)
        if (progress < 0) progression.setTextColor(Color.RED)
    }

    private fun displayOrHideTimeSpentCard(stats : Statistics) {
        val daysSinceFirstTest : Int = stats.daysSinceFirstTest
        if (daysSinceFirstTest < 1) {
            stats_card_time_spent.visibility = View.GONE
            cardsHidden++
            return
        }
        tv_days_with_us.text = getString(R.string.stats_days_since_first_test_pl, daysSinceFirstTest)
        tv_stats_time_spent_total.text = getString(R.string.stats_total_minutes_of_testing_pl, stats.totalTimeSpent)
    }

    private fun displayOrHideAccuracyCard(stats: Statistics) {
        val totalWordsWritten : Int = stats.totalWordsWritten
        if (totalWordsWritten <= 0) {
            stats_card_accuracy.visibility = View.GONE
            cardsHidden++
            return
        }
        val accuracy : Int = stats.accuracy
        tv_stats_words_total.text = getString(R.string.stats_words_written_total_pl, totalWordsWritten)
        tv_accuracy.text = getString(R.string.stats_accuracy_pl, accuracy)
        when {
            accuracy < 50 -> tv_accuracy.setTextColor(Color.rgb(128, 0, 0))
            accuracy < 80 -> tv_accuracy.setTextColor(Color.rgb(255, 255, 0))
            else -> tv_accuracy.setTextColor(Color.rgb(0,192,0))
        }
    }

    private fun displayOrHidePreferencesCard(stats: Statistics) {
        val favLang : String = stats.getFavoriteLanguageName(context)
        val favTimeMode : String = stats.getFavoriteTimeMode(context)
        if (favLang == "" || favTimeMode == "") {
            stats_card_preferences.visibility = View.GONE
            cardsHidden++
            return
        }
        stats_favorite_language.text = getString(R.string.stats_language_pl, favLang)
        stats_favorite_time_mode.text = getString(R.string.stats_time_mode_pl, favTimeMode)
    }

    private fun displayOrHideAchievementsCard(stats: Statistics, currentUser : User) {
        if (stats.doneAchievementsCount == 0) {
            stats_card_achievement.visibility = View.GONE
            cardsHidden++
            return
        }
        stats_achievement_progress_bar.progress = stats.achievementsProgressInPercents
        stats_done_achievements_amount.text = getString(
                R.string.stats_achievements_done_pl,
                stats.doneAchievementsCount,
                currentUser.achievements.size)
        stats_last_earned_achievement.text = getString(
                R.string.stats_last_earned_achievement_pl,
                stats.lastCompletedAchievementName)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) AnimationManager().applyAnimationToEachView(
                visibleCards as Collection<View>,
                getAnimationSet(),
                100,
                true)

    }

    private val ANIMATION_DURATION : Long = 500

    private fun getAnimationSet() : AnimationSet {
        val animationSet = AnimationSet(false)
        val animationManager = AnimationManager()
        animationSet.addAnimation(animationManager.getFadeInAnimation(ANIMATION_DURATION))
        animationSet.addAnimation(animationManager.getSlideInAnimation(0f, 50f, ANIMATION_DURATION))
        return animationSet
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

}