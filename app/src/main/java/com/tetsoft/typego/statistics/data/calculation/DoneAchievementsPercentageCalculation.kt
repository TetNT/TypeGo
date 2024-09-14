package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.achievements.data.AchievementsCompletionPair

class DoneAchievementsPercentageCalculation(
    private val completedAchievements: List<AchievementsCompletionPair>,
    private val achievementsCount: Int
) :
    StatisticsCalculation {

    override fun provide(): Int {
        var completedCount = 0
        for (completedAchievement in completedAchievements) {
            if (completedAchievement.isCompleted()) completedCount++
        }
        return if (completedCount == 0 || achievementsCount == 0)
            0
        else
            (completedCount * 100 / achievementsCount)
    }
}