package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.achievements.data.AchievementsProgressList

class DoneAchievementsCountCalculation(private val completedAchievements: AchievementsProgressList) :
    StatisticsCalculation {

    override fun provide(): Int {
        var count = 0
        for (completedAchievement in completedAchievements)
            if (completedAchievement.isCompleted()) count++
        return count
    }

}