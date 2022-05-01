package com.tetsoft.typego.game.result

import com.tetsoft.typego.data.Language
import com.tetsoft.typego.data.language.LanguageSelectable
import com.tetsoft.typego.utils.StringKeys
import java.util.*

class GameResultList : ArrayList<GameResult?> {
    constructor()
    constructor(list: List<GameResult?>?) {
        this.addAll(list!!)
    }

    fun getResultsByLanguage(language: Language): GameResultList {
        if (language.identifier == StringKeys.LANGUAGE_ALL) return this
        val selectedResults = GameResultList()
        for (result in this) {
            val gameMode = result?.gameMode
            if (gameMode is LanguageSelectable) {
                val rowLanguageId = (gameMode as LanguageSelectable).getLanguage().identifier
                if (rowLanguageId.equals(
                        language.identifier,
                        ignoreCase = true
                    )
                ) selectedResults.add(result)
            }
        }
        return selectedResults
    }

    fun getLastWpm() : Double {
        if (this.isNotEmpty()) return this[size - 1]!!.wpm
        return 0.0
    }

    fun getPreviousResultByLanguage(language: Language) : Int {
        val resultsByLanguage = getResultsByLanguage(language)
        if (resultsByLanguage.isNotEmpty())
            return resultsByLanguage[resultsByLanguage.size - 1]!!.wpm.toInt()
        return 0
    }

    fun getBestResultByLanguage(language: Language) : Int {
        val resultsByLanguage = getResultsByLanguage(language)
        if (resultsByLanguage.isEmpty()) {
            return 0
        }
        var best = 0.0
        for (result in resultsByLanguage) {
            if (result!!.wpm > best) best = result.wpm
        }
        return best.toInt()

    }

    val bestResultWpm: Int
        get() {
            var currentBest = 0
            for (result in this) {
                if (result?.wpm!! > currentBest) currentBest = result.wpm.toInt()
            }
            return currentBest
        }

    val bestResult: GameResult?
        get() {
            if (this.isEmpty()) return null
            var currentBest = this[0]
            for (result in this) {
                if (result?.wpm!! > currentBest!!.wpm) currentBest = result
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