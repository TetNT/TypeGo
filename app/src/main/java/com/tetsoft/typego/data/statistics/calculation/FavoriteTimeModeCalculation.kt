package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.GameOnTimeDataSelector
import com.tetsoft.typego.data.history.GameOnTimeHistoryList
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.data.timemode.TimeModeList

class FavoriteTimeModeCalculation(private val resultsList: GameOnTimeHistoryList) :
    StatisticsCalculation {

    override fun provide(): TimeMode {
        var mostFrequentTimeMode = TimeMode(0)
        var mostFrequency = 0
        for (timeMode in TimeModeList()) {
            val occurrences: Int =
                GameOnTimeDataSelector(resultsList).getResultsByTimeMode(timeMode.timeInSeconds).size
            if (occurrences > mostFrequency) {
                mostFrequency = occurrences
                mostFrequentTimeMode = timeMode
            }
        }
        return mostFrequentTimeMode
    }

}