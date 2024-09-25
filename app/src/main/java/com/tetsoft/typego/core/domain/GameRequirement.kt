package com.tetsoft.typego.core.domain

import com.tetsoft.typego.history.domain.GameHistory
import kotlin.math.roundToInt

abstract class GameRequirement(protected val requiredAmount: Int) {

    abstract fun isReached(history: GameHistory): Boolean

    fun provideRequiredAmount(): Int {
        return requiredAmount
    }

    abstract class WithProgress(requiredAmount: Int) : GameRequirement(requiredAmount) {

        abstract fun getCurrentProgress(history: GameHistory): Int
    }

    class WpmRequirement(requiredAmount: Int) : WithProgress(requiredAmount) {

        override fun getCurrentProgress(history: GameHistory): Int {
            if (history.getAllResults().isEmpty()) return 0
            return history.getAllResults().maxBy { it.getWpm() }.getWpm().roundToInt()
        }

        override fun isReached(history: GameHistory): Boolean {
            val allResults = history.getAllResults()
            if (allResults.isEmpty()) return false
            return allResults.maxBy { it.getWpm() }.getWpm().roundToInt() >= requiredAmount
        }

    }

    class CompletedGamesAmountRequirement(requiredAmount: Int) :
        WithProgress(requiredAmount) {
        override fun getCurrentProgress(history: GameHistory): Int {
            return history.getAllResults().size
        }

        override fun isReached(history: GameHistory): Boolean {
            return history.getAllResults().size >= requiredAmount
        }

    }

    class SharpEyeRequirement(timeInSeconds: Int) : GameRequirement(timeInSeconds) {

        override fun isReached(history: GameHistory): Boolean {
            val results = history.getResultsWithWordsInformation()
            if (results.isEmpty()) return false
            val lastResult = results.maxBy { it.getCompletionDateTime() }
            if (lastResult.getWpm() < MINIMAL_WPM) return false
            return lastResult.getTimeSpent() == requiredAmount && lastResult.getIncorrectWords() == 0
        }

        companion object {
            const val MINIMAL_WPM = 30.0
        }
    }

    class NoMistakesInARowRequirement(private val inARow: Int) :
        WithProgress(inARow) {

        override fun getCurrentProgress(history: GameHistory): Int {
            val resultsWithWordsInformation = history.getResultsWithWordsInformation()
            if (resultsWithWordsInformation.isEmpty()) return 0
            var maxProgress = 0
            if (resultsWithWordsInformation.size == 1 && resultsWithWordsInformation[0].getIncorrectWords() == 0)
                return 1
            var i = 0
            while(i < resultsWithWordsInformation.size - 1) {
                if (resultsWithWordsInformation[i].getIncorrectWords() == 0) {
                    var progress = 1
                    var j = i + 1
                    while(j < resultsWithWordsInformation.size && resultsWithWordsInformation[j].getIncorrectWords() == 0) {
                        progress += 1
                        j += 1
                    }
                    if (progress > maxProgress) {
                        maxProgress = progress
                    }
                }
                i += 1
            }
            return maxProgress
        }

        override fun isReached(history: GameHistory): Boolean {
            val resultsWithWordsInformation = history.getResultsWithWordsInformation()
            if (resultsWithWordsInformation.isEmpty()) return false
            var maxProgress = 0
            var i = 0
            while(i < resultsWithWordsInformation.size - 1) {
                if (resultsWithWordsInformation[i].getIncorrectWords() == 0) {
                    var progress = 1
                    var j = i + 1
                    while(j < resultsWithWordsInformation.size && resultsWithWordsInformation[j].getIncorrectWords() == 0) {
                        progress += 1
                        j += 1
                    }
                    if (progress > maxProgress) {
                        maxProgress = progress
                    }
                    if (maxProgress >= inARow) {
                        return true
                    }
                }
                i += 1
            }
            return false
        }
    }

    class DifferentLanguagesRequirement(private val uniqueLanguageEntries: Int) :
        WithProgress(uniqueLanguageEntries) {

        override fun getCurrentProgress(history: GameHistory): Int {
            val resultsWithLanguage = history.getResultsWithLanguage()
            var entries = 0
            for (language in Language.LANGUAGE_LIST) {
                if (resultsWithLanguage.any { it.getLanguageCode() == language.identifier })
                    entries = entries.inc()
            }
            return entries
        }

        override fun isReached(history: GameHistory): Boolean {
            val resultsWithLanguage = history.getResultsWithLanguage()
            var entries = 0
            if (history.getResultsWithLanguage().size < uniqueLanguageEntries)
                return false
            for (language in Language.LANGUAGE_LIST) {
                if (resultsWithLanguage.any { it.getLanguageCode() == language.identifier })
                    entries = entries.inc()
                if (entries >= uniqueLanguageEntries)
                    return true
            }
            return false
        }

    }
}