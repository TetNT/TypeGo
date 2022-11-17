package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.DoneAchievementsCountCalculation

class DoneAchievementCountStatistics(doneAchievementsCountCalculation: DoneAchievementsCountCalculation) : Statistics {

    private val count = doneAchievementsCountCalculation.provide()

    override fun provide(): Int {
        return count
    }

    override fun getVisibility(): VisibilityProvider {
        return VisibilityProvider.Visible()
    }
}