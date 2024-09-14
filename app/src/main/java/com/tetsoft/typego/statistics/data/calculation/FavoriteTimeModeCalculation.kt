package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.core.domain.TimeMode
import com.tetsoft.typego.core.domain.TimeModeList

class FavoriteTimeModeCalculation(private val resultsList: List<GameResult.TimeLimited>) :
    StatisticsCalculation {

    override fun provide(): TimeMode {
        var mostFrequentTimeMode = TimeMode(0)
        var mostFrequency = 0
        for (timeMode in TimeModeList()) {
            val occurrences: Int =
                resultsList.filter { it.getChosenTimeInSeconds() == timeMode.timeInSeconds }.size
            if (occurrences > mostFrequency) {
                mostFrequency = occurrences
                mostFrequentTimeMode = timeMode
            }
        }
        return mostFrequentTimeMode
    }

}