package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.game.GameResult
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.data.timemode.TimeModeList

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