package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList

class DoneAchievementsCountCalculation(private val completedAchievements: AchievementsProgressList) :
    StatisticsCalculation {

    override fun provide(): Int {
        var count = 0
        for (completedAchievement in completedAchievements)
            if (completedAchievement.isCompleted()) count++
        return count
    }

}