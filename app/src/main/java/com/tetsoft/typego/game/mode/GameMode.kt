package com.tetsoft.typego.game.mode

import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.LanguageSelectable
import java.io.Serializable
import java.lang.ArithmeticException

abstract class GameMode(
    open val suggestionsActivated: Boolean,
    open val screenOrientation: ScreenOrientation
) : Serializable {

    open fun calculateWpm(score: Int, timeSpentInSeconds: Int) : Double {
        return try {
            60 / timeSpentInSeconds * (score / WPM_GENERAL_DIVIDER)
        } catch (ae: ArithmeticException) {
            0.0
        }
    }

    fun isGameOnTime() : Boolean {
        return (this is GameOnTime)
    }


    fun isLanguageSelectable() : Boolean {
        return (this is LanguageSelectable)
    }

    companion object {
        /**
         * The divider to not rely on words length when calculating WPM.
         */
        const val WPM_GENERAL_DIVIDER = 4.0
    }
}