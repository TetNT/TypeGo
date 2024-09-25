package com.tetsoft.typego.statistics.data.statistics

import com.tetsoft.typego.core.domain.TimeMode
import com.tetsoft.typego.statistics.domain.Statistics
import com.tetsoft.typego.statistics.domain.VisibilityProvider
import com.tetsoft.typego.statistics.data.calculation.FavoriteTimeModeCalculation

class FavoriteTimeModeStatistics(favoriteTimeModeCalculation: FavoriteTimeModeCalculation) :
    Statistics {

    private val favoriteTimeMode = favoriteTimeModeCalculation.provide()

    override fun provide(): TimeMode {
        return favoriteTimeMode
    }

    override fun getVisibility(): VisibilityProvider {
        return if (favoriteTimeMode.timeInSeconds == 0)
            VisibilityProvider.Gone()
        else
            VisibilityProvider.Visible()
    }

}