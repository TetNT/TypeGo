package com.tetsoft.typego.core.data.requirement

import com.tetsoft.typego.core.domain.GameRequirement
import com.tetsoft.typego.history.domain.GameHistory

class CompletedGamesAmountRequirement(requiredAmount: Int) :
    GameRequirement.WithProgress(requiredAmount) {
    override fun getCurrentProgress(history: GameHistory): Int {
        return history.getAllResults().size
    }

    override fun isReached(history: GameHistory): Boolean {
        return history.getAllResults().size >= requiredAmount
    }

}