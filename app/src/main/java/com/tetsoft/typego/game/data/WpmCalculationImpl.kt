package com.tetsoft.typego.game.data

import android.util.Log
import com.tetsoft.typego.game.domain.WpmCalculation

class WpmCalculationImpl(private val timeSpentInSeconds : Int, private val writtenChars : Int) :
    WpmCalculation {
    override fun calculate(): Double {
        if (timeSpentInSeconds == 0 || writtenChars == 0) return 0.0
        return try {
            (60.0 / timeSpentInSeconds.toDouble()) * (writtenChars.toDouble() / WPM_GENERAL_DIVIDER)
        } catch (ae: ArithmeticException) {
            Log.e("WPM", "timeSpentInSeconds: " + timeSpentInSeconds + "\n" +
                    "writtenChars: " + "\n")
            0.0
        }
    }

    private companion object {
        const val WPM_GENERAL_DIVIDER = 4.0
    }
}