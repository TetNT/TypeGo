package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.game.result.GameResult
import java.util.concurrent.TimeUnit

// TODO: Replace the gameResultList parameter with "firstCompleted"
class DaysSinceFirstTestCalculation(private val gameResultList: List<GameResult>, private val currentDate : Long) :
    StatisticsCalculation {
    override fun provide(): Int {
        if (gameResultList.isEmpty()) return 0
        val dateDiff : Long = currentDate - gameResultList[0].completionDateTime
        if (dateDiff < 0) return 0
        return TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS).toInt()
    }
}