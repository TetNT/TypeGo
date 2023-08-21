package com.tetsoft.typego.data.requirement

import com.tetsoft.typego.data.history.ClassicGameHistoryDataSelector
import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.game.GameOnTime
import kotlin.math.roundToInt

abstract class GameRequirement(protected val requiredAmount: Int) {

    abstract fun isReached(list: ClassicGameModesHistoryList): Boolean

    fun provideRequiredAmount(): Int {
        return requiredAmount
    }

    abstract class WithProgress(requiredAmount: Int) : GameRequirement(requiredAmount) {

        abstract fun getCurrentProgress(list: ClassicGameModesHistoryList): Int
    }

    class WpmRequirement(requiredAmount: Int) : GameRequirement.WithProgress(requiredAmount) {

        override fun getCurrentProgress(list: ClassicGameModesHistoryList): Int {
            if (list.isEmpty()) return 0
            return list[list.size - 1].getWpm().roundToInt()
        }

        override fun isReached(list: ClassicGameModesHistoryList): Boolean {
            if (list.isEmpty()) return false
            return list[list.size - 1].getWpm().roundToInt() >= requiredAmount
        }

    }

    class CompletedGamesAmountRequirement(requiredAmount: Int) :
        GameRequirement.WithProgress(requiredAmount) {
        override fun getCurrentProgress(list: ClassicGameModesHistoryList): Int {
            return list.size
        }

        override fun isReached(list: ClassicGameModesHistoryList): Boolean {
            return list.size >= requiredAmount
        }

    }

    class SharpEyeRequirement(timeInSeconds: Int) : GameRequirement(timeInSeconds) {

        override fun isReached(list: ClassicGameModesHistoryList): Boolean {
            if (list.isEmpty()) return false
            val lastResult = ClassicGameHistoryDataSelector(list).getMostRecentResult()
            if (lastResult.getWpm() < MINIMAL_WPM) return false
            if (lastResult is GameOnTime) {
                return lastResult.getTimeSpent() == requiredAmount && lastResult.getIncorrectWords() == 0
            }
            return false
        }

        companion object {
            const val MINIMAL_WPM = 30.0
        }
    }

    class NoMistakesInARowRequirement(private val inARow: Int) :
        GameRequirement.WithProgress(inARow) {

        @Deprecated("This should check first if we completed the achievement. \n" +
                "         If yes, return inARow value instead of calculating again.\n" +
                "         Do not use until fixed")
        override fun getCurrentProgress(list: ClassicGameModesHistoryList): Int {
            if (list.isEmpty() || list.size < inARow) return 0
            var progress = 0
            for (i in 1..inARow) {
                if (list[list.size - i].getIncorrectWords() != 0) progress = progress.inc()
            }
            return progress
        }

        override fun isReached(list: ClassicGameModesHistoryList): Boolean {
            if (list.isEmpty() || list.size < inARow) return false
            for (i in 1..inARow) {
                val currElem = list[list.size - i]
                if (currElem.getIncorrectWords() != 0) return false
            }
            return true
        }
    }

    class DifferentLanguagesRequirement(private val uniqueLanguageEntries: Int) :
        GameRequirement.WithProgress(uniqueLanguageEntries) {

        override fun getCurrentProgress(list: ClassicGameModesHistoryList): Int {
            var entries = 0
            for (language in LanguageList().getList()) {
                if (ClassicGameHistoryDataSelector(list).getResultsByLanguage(language.identifier).isNotEmpty())
                    entries = entries.inc()
            }
            return entries
        }

        override fun isReached(list: ClassicGameModesHistoryList): Boolean {
            var entries = 0
            if (list.size < uniqueLanguageEntries) return false
            for (language in LanguageList().getList()) {
                if (ClassicGameHistoryDataSelector(list).getResultsByLanguage(language.identifier)
                        .isNotEmpty()
                )
                    entries = entries.inc()
                if (entries >= uniqueLanguageEntries) return true
            }
            return false
        }

    }
}