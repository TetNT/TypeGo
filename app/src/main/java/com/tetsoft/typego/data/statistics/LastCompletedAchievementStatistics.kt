package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.achievement.Achievement
import com.tetsoft.typego.data.statistics.calculation.LastCompletedAchievementCalculation

class LastCompletedAchievementStatistics(lastCompletedAchievementCalculation: LastCompletedAchievementCalculation) :
    Statistics {

    private val lastAchievement = lastCompletedAchievementCalculation.provide()

    override fun provide(): Achievement {
        return lastAchievement
    }

    override fun getVisibility(): VisibilityProvider {
        return if (lastAchievement.getId() == Achievement.Empty().getId())
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }
}