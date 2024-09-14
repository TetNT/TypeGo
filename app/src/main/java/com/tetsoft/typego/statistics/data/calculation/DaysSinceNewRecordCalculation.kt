package com.tetsoft.typego.statistics.data.calculation

import java.util.concurrent.TimeUnit

class DaysSinceNewRecordCalculation(
    private val currentDate: Long,
    private val newBestResultCompletionTime: Long
) : StatisticsCalculation {

    override fun provide(): Int {
        if (currentDate == 0L || newBestResultCompletionTime == 0L) return 0
        val dateDiff : Long = currentDate - newBestResultCompletionTime
        if (dateDiff < 0L) return 0
        return TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS).toInt()
    }
}