package com.tetsoft.typego.core.domain

import com.tetsoft.typego.history.domain.GameHistory

abstract class GameRequirement(protected val requiredAmount: Int) {

    abstract fun isReached(history: GameHistory): Boolean

    fun provideRequiredAmount(): Int {
        return requiredAmount
    }

    abstract class WithProgress(requiredAmount: Int) : GameRequirement(requiredAmount) {

        abstract fun getCurrentProgress(history: GameHistory): Int
    }

}