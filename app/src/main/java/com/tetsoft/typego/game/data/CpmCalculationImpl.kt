package com.tetsoft.typego.game.data

import com.tetsoft.typego.game.domain.CpmCalculation
import kotlin.math.roundToInt

class CpmCalculationImpl(private val timeSpentInSeconds: Int, private val charsWritten: Int) : CpmCalculation {
    override fun calculate(): Int {
        if (timeSpentInSeconds == 0 || charsWritten == 0) return 0
        return try {
            ((60.0 / timeSpentInSeconds.toDouble()) * charsWritten.toDouble()).roundToInt()
        } catch (ae: ArithmeticException) {
            0
        }
    }
}