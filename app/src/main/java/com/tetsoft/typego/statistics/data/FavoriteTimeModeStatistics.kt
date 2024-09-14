package com.tetsoft.typego.statistics.data

import com.tetsoft.typego.statistics.data.calculation.FavoriteTimeModeCalculation
import com.tetsoft.typego.core.domain.TimeMode

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