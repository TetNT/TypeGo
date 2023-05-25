package com.tetsoft.typego.game.result

import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.PrebuiltTextGameMode
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.game.mode.GameOnTime
import java.util.*

@Deprecated("Replaced with GameOnTimeHistoryList")
class GameResultList : ArrayList<GameResult> {
    constructor()
    constructor(list: List<GameResult>) {
        this.addAll(list)
    }

    fun getResultsByLanguage(language: Language): GameResultList {
        if (language.identifier == Language.ALL) return this
        val selectedResults = GameResultList()
        for (result in this) {
            val gameMode = result.gameMode
            if (gameMode is PrebuiltTextGameMode) {
                val rowLanguageId = (gameMode as PrebuiltTextGameMode).getLanguage().identifier
                if (rowLanguageId.equals(
                        language.identifier,
                        ignoreCase = true
                    )
                ) selectedResults.add(result)
            }
        }
        return selectedResults
    }

    fun getResultsByTimeMode(timeMode: TimeMode): GameResultList {
        if (timeMode.timeInSeconds == 0) return this
        val selectedResults = GameResultList()
        for (result in this) {
            val gameMode = result.gameMode
            if (gameMode is GameOnTime) {
                val rowTimeModeSeconds = gameMode.timeMode.timeInSeconds
                if (rowTimeModeSeconds == timeMode.timeInSeconds)
                    selectedResults.add(result)
            }
        }
        return selectedResults
    }

    // TODO: Check if we even need this
    fun getLastResultByGameType(gameTypeClass : Class<*>) : GameResult {
        if (this.isEmpty()) return GameResult.Empty()
        this.asReversed().forEach {
            if (gameTypeClass.isInstance(it.gameMode)) {
                return it
            }
        }
        return GameResult.Empty()
    }

    fun getLastWpm() : Double {
        if (this.isNotEmpty()) return this[size - 1].wpm
        return 0.0
    }

    fun getPreviousResultByLanguage(language: Language) : Int {
        val resultsByLanguage = getResultsByLanguage(language)
        if (resultsByLanguage.isNotEmpty())
            return resultsByLanguage[resultsByLanguage.size - 1].wpm.toInt()
        return 0
    }

    fun getBestResultByLanguage(language: Language) : Int {
        val resultsByLanguage = getResultsByLanguage(language)
        if (resultsByLanguage.isEmpty()) {
            return 0
        }
        var best = 0.0
        for (result in resultsByLanguage) {
            if (result.wpm > best) best = result.wpm
        }
        return best.toInt()
    }

    fun getAverageWpm() : Double {
        if (this.isEmpty()) return 0.0
        var sum = 0.0
        for (result in this) {
            sum += result?.wpm ?: 0.0
        }
        return (sum / size)
    }

    val bestResultWpm: Int
        get() {
            var currentBest = 0
            for (result in this) {
                if (result.wpm > currentBest) currentBest = result.wpm.toInt()
            }
            return currentBest
        }

    val bestResult: GameResult
        get() {
            if (this.isEmpty()) return GameResult.Empty()
            var currentBest = this[0]
            for (result in this) {
                if (result.wpm > currentBest.wpm) currentBest = result
            }
            return currentBest
        }

    val inDescendingOrder: GameResultList
        get() {
            val copy = clone() as GameResultList
            Collections.reverse(copy)
            return copy
        }
}