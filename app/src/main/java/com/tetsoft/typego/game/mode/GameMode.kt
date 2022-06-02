package com.tetsoft.typego.game.mode

import com.tetsoft.typego.data.ScreenOrientation
import java.io.Serializable
import java.lang.ArithmeticException

abstract class GameMode(
    open val suggestionsActivated: Boolean,
    open val screenOrientation: ScreenOrientation
) : Serializable {

    open fun calculateWpm(score: Int, timeSpentInSeconds: Int) : Double {
        return try {
            60.0 / timeSpentInSeconds * (score / WPM_GENERAL_DIVIDER)
        } catch (ae: ArithmeticException) {
            0.0
        }
    }

    companion object {
        /**
         * The divider to not rely on words length when calculating WPM.
         */
        const val WPM_GENERAL_DIVIDER = 4.0
    }
}