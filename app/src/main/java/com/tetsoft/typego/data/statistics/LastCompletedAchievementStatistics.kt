package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.achievement.deprecated.Achievement
import com.tetsoft.typego.data.statistics.calculation.LastCompletedAchievementCalculation

class LastCompletedAchievementStatistics(lastCompletedAchievementCalculation: LastCompletedAchievementCalculation) :
    Statistics {

    private val lastAchievement = lastCompletedAchievementCalculation.provide()

    override fun provide(): Achievement {
        return lastAchievement
    }

    override fun getVisibility(): VisibilityProvider {
        return if (lastAchievement.id == Achievement.Empty().id)
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }
}