package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.achievements.data.CompletedAchievementsList
import com.tetsoft.typego.statistics.domain.StatisticsCalculation

class DoneAchievementsCountCalculation(private val completedAchievements: CompletedAchievementsList) :
    StatisticsCalculation {

    override fun provide(): Int {
        var count = 0
        for (completedAchievement in completedAchievements)
            if (completedAchievement.isCompleted()) count++
        return count
    }

}