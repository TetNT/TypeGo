package com.tetsoft.typego.statistics.data.statistics

import com.tetsoft.typego.statistics.domain.Statistics
import com.tetsoft.typego.statistics.domain.VisibilityProvider
import com.tetsoft.typego.statistics.data.calculation.DoneAchievementsCountCalculation

class DoneAchievementCountStatistics(doneAchievementsCountCalculation: DoneAchievementsCountCalculation) : Statistics {

    private val count = doneAchievementsCountCalculation.provide()

    override fun provide(): Int {
        return count
    }

    override fun getVisibility(): VisibilityProvider {
        return if (count == 0)
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }
}