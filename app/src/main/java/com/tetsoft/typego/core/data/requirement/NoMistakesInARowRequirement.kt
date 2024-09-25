package com.tetsoft.typego.core.data.requirement

import com.tetsoft.typego.core.domain.GameRequirement
import com.tetsoft.typego.history.domain.GameHistory

class NoMistakesInARowRequirement(private val inARow: Int) :
    GameRequirement.WithProgress(inARow) {

    override fun getCurrentProgress(history: GameHistory): Int {
        val resultsWithWordsInformation = history.getResultsWithWordsInformation()
        if (resultsWithWordsInformation.isEmpty()) return 0
        var maxProgress = 0
        if (resultsWithWordsInformation.size == 1 && resultsWithWordsInformation[0].getIncorrectWords() == 0)
            return 1
        var i = 0
        while (i < resultsWithWordsInformation.size - 1) {
            if (resultsWithWordsInformation[i].getIncorrectWords() == 0) {
                var progress = 1
                var j = i + 1
                while (j < resultsWithWordsInformation.size && resultsWithWordsInformation[j].getIncorrectWords() == 0) {
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