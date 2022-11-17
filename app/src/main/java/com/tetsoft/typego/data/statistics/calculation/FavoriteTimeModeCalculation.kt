package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.data.timemode.TimeModeList
import com.tetsoft.typego.game.result.GameResultList

class FavoriteTimeModeCalculation(private val resultsList: GameResultList) : StatisticsCalculation {

    override fun provide(): TimeMode {
        var mostFrequentTimeMode = TimeMode(0)
        var mostFrequency = 0
        for (timeMode in TimeModeList()) {
            val occurrences: Int = resultsList.getResultsByTimeMode(timeMode).size
            if (occurrences > mostFrequency) {
                mostFrequency = occurrences
                mostFrequentTimeMode = timeMode
            }
        }
        return mostFrequentTimeMode
    }

}