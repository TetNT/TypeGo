package com.tetsoft.typego.statistics.data

import com.tetsoft.typego.statistics.data.calculation.DoneAchievementsPercentageCalculation

class DoneAchievementsPercentageStatistics(doneAchievementsPercentageCalculation: DoneAchievementsPercentageCalculation) :
    Statistics {

    private val percents = doneAchievementsPercentageCalculation.provide()

    override fun provide(): Int {
        return percents
    }

    override fun getVisibility(): VisibilityProvider {
        return if (percents == 0)
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }
}