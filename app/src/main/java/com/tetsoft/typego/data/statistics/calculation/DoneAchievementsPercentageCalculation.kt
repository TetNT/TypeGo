package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.achievement.completion.AchievementsCompletionPair
import com.tetsoft.typego.data.statistics.VisibilityProvider

class DoneAchievementsPercentageCalculation(private val completedAchievements: List<AchievementsCompletionPair>) :
    StatisticsCalculation {

    override fun provide(): Int {
        var completedCount = 0
        val achievementsSize: Int = completedAchievements.size
        for (completedAchievement in completedAchievements) {
            if (completedAchievement.isCompleted()) completedCount++
        }
        return if (completedCount == 0 || achievementsSize == 0)
            0
        else
            (completedCount * 100 / achievementsSize)
    }
}