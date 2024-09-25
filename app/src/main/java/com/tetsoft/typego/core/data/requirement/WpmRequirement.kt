package com.tetsoft.typego.core.data.requirement

import com.tetsoft.typego.core.domain.GameRequirement
import com.tetsoft.typego.history.domain.GameHistory
import kotlin.math.roundToInt

class WpmRequirement(requiredAmount: Int) : GameRequirement.WithProgress(requiredAmount) {

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