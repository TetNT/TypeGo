package com.tetsoft.typego.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tetsoft.typego.R
import com.tetsoft.typego.account.Statistics
import com.tetsoft.typego.account.User
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsFragment : Fragment() {

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = User.getFromStoredData(requireContext())
        val stats = Statistics(currentUser)
        start_wpm.text = getString(R.string.stats_wpm_pl,stats.averagePastWPM)
        current_wpm.text = getString(R.string.stats_wpm_pl, stats.averageCurrentWPM)
        progression.text = getString(R.string.stats_progression_pl, stats.progression)
        if (stats.progression < 0) progression.setTextColor(Color.RED)
        tv_days_with_us.text = getString(R.string.stats_days_since_first_test_pl, stats.daysSinceFirstTest)
        tv_stats_time_spent_total.text = getString(R.string.stats_total_minutes_of_testing_pl, stats.totalTimeSpent)
        tv_stats_words_total.text = getString(R.string.stats_words_written_total_pl, stats.totalWordsWritten)
        accuracy.text = getString(R.string.stats_accuracy_pl, stats.accuracy)
        stats_done_achievements_amount.text = getString(
                R.string.stats_achievements_done_pl,
                stats.doneAchievementsCount,
                currentUser.achievements.size)
        stats_last_earned_achievement.text = getString(
                R.string.stats_last_earned_achievement_pl,
                stats.lastCompletedAchievementName)
        stats_favorite_language.text = getString(R.string.stats_language_pl, stats.getFavoriteLanguageName(context))
        stats_favorite_time_mode.text = getString(R.string.stats_time_mode_pl, stats.getFavoriteTimeMode(context))

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

}