package com.tetsoft.typego.statistics.data

import com.tetsoft.typego.statistics.data.calculation.DaysSinceNewRecordCalculation

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