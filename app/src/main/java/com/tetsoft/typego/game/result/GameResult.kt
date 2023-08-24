package com.tetsoft.typego.game.result

import com.tetsoft.typego.game.mode.GameMode
import kotlin.math.roundToInt

@Deprecated("Replaced with a new GameResult interface")
open class GameResult(
    val gameMode: GameMode,
    val score: Int,
    val timeSpentInSeconds: Int,
    val wordsWritten: Int,
    val correctWords: Int,
    val completionDateTime: Long
) {
    // Words per minute
    val wpm: Double = calculateWpm()

    private fun calculateWpm(): Double {
        if (timeSpentInSeconds == 0 || score == 0) return 0.0
        return try {
            (60.0 / timeSpentInSeconds.toDouble()) * (score.toDouble() / WPM_GENERAL_DIVIDER)
        } catch (ae: ArithmeticException) {
            0.0
        }
    }

    // Chars per minute
    val cpm: Int = calculateCpm().roundToInt()

    private fun calculateCpm(): Double {
        if (timeSpentInSeconds == 0 || score == 0) return 0.0
        return try {
            (60.0 / timeSpentInSeconds.toDouble()) * score.toDouble()
        } catch (ae: ArithmeticException) {
            0.0
        }
    }

    companion object {
        /**
         * The divider to not rely on words length when calculating WPM.
         */
        const val WPM_GENERAL_DIVIDER : Double = 4.0
    }

    class Empty : GameResult(
        GameMode.Empty(),
        0,
        0,
        0,
        0,
        0L
    )
}