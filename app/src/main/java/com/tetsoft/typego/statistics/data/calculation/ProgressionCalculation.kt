package com.tetsoft.typego.statistics.data.calculation

class ProgressionCalculation(private val pastWpm : Int, private val currentWpm : Int) :
    StatisticsCalculation {

    override fun provide(): Int {
        if (pastWpm == 0 || currentWpm == 0) return EMPTY_PROGRESSION
        if (pastWpm == currentWpm) return 0
        return if (currentWpm > pastWpm) {
            (100 - 100 * pastWpm / currentWpm)
        } else {
            (100 - 100 * currentWpm / pastWpm) * -1
        }
    }

    companion object {
        const val EMPTY_PROGRESSION = Integer.MIN_VALUE
    }
}