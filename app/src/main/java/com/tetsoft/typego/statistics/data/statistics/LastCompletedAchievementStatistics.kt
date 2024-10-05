package com.tetsoft.typego.statistics.data.statistics

import com.tetsoft.typego.achievements.data.Achievement
import com.tetsoft.typego.statistics.domain.Statistics
import com.tetsoft.typego.statistics.domain.VisibilityProvider
import com.tetsoft.typego.statistics.data.calculation.LastCompletedAchievementCalculation

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