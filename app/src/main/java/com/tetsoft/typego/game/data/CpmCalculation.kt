package com.tetsoft.typego.game.data

import kotlin.math.roundToInt
// TODO: TimeGame data package
interface CpmCalculation {
    fun calculate() : Int

    class Standard(private val timeSpentInSeconds: Int, private val charsWritten: Int) : CpmCalculation {
        override fun calculate(): Int {
            if (timeSpentInSeconds == 0 || charsWritten == 0) return 0
            return try {
                ((60.0 / timeSpentInSeconds.toDouble()) * charsWritten.toDouble()).roundToInt()
            } catch (ae: ArithmeticException) {
                0
            }
        }

    }
}