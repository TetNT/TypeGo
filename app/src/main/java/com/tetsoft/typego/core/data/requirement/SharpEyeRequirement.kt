package com.tetsoft.typego.core.data.requirement

import com.tetsoft.typego.core.domain.GameRequirement
import com.tetsoft.typego.history.domain.GameHistory

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