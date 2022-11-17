package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.DaysSinceNewRecordCalculation
import com.tetsoft.typego.data.statistics.calculation.StatisticsCalculation

class DaysSinceNewRecordStatistics(daysSinceNewRecordCalculation: DaysSinceNewRecordCalculation) : Statistics {

    private val days = daysSinceNewRecordCalculation.provide()

    override fun provide(): Int {
        return days
    }

    override fun getVisibility(): VisibilityProvider {
        return if (days == 0)
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }
}