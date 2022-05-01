package com.tetsoft.typego.game.result

import com.tetsoft.typego.game.mode.GameMode

class GameResult(
    val gameMode: GameMode,
    val score: Int,
    val timeSpentInSeconds: Int,
    val wordsWritten: Int,
    val correctWords: Int,
    val completionDateTime: Long
) {
    // Words per minute
    val wpm: Double = gameMode.calculateWpm(score, timeSpentInSeconds)

    // Chars per minute
    val cpm: Int = score
}