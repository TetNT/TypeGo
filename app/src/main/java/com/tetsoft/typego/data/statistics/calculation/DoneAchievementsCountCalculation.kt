package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.achievement.completion.AchievementsCompletionPair
import com.tetsoft.typego.data.statistics.VisibilityProvider

class DoneAchievementsCountCalculation(private val completedAchievements: List<AchievementsCompletionPair>) :
    StatisticsCalculation {

    override fun provide(): Int {
        var count = 0
        for (completedAchievement in completedAchievements)
            if (completedAchievement.isCompleted()) count++
        return count
    }

}