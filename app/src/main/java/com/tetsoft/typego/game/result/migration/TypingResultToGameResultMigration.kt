package com.tetsoft.typego.game.result.migration

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.testing.TypingResult
import kotlin.collections.ArrayList

class TypingResultToGameResultMigration(private val oldResults: ArrayList<TypingResult>) {
    fun migrate(): GameResultList {
        val gameResultList = GameResultList()
        for (result in oldResults) {
            // since a list of TypingResult adds elements at the 0 index, the method does migrations accordingly.
            gameResultList.add(0,
                GameResult(
                    oldResultToNew(result),
                    result.correctChars,
                    result.timeInSeconds,
                    result.totalWords,
                    result.correctWords,
                    result.completionDateTime.time
                )
            )
        }
        return gameResultList
    }

    private fun oldResultToNew(result: TypingResult): GameMode {
        val timeMode = TimeMode(result.timeInSeconds)

        val dictionary: DictionaryType =
            if (result.dictionaryType == 0) DictionaryType.BASIC
            else DictionaryType.ENHANCED

        val orientation: ScreenOrientation =
            if (result.screenOrientation == 0) ScreenOrientation.PORTRAIT
            else ScreenOrientation.LANDSCAPE

        return GameOnTime(
            result.language,
            timeMode,
            dictionary,
            result.isTextSuggestions,
            orientation
        )
    }
}