package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.data.statistics.calculation.FavoriteTimeModeCalculation
import com.tetsoft.typego.data.timemode.TimeMode

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