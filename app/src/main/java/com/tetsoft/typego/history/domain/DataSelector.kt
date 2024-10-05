package com.tetsoft.typego.history.domain

import com.tetsoft.typego.core.domain.GameResult

interface DataSelector<T : GameResult> {
    fun getBestResult() : T?

    fun getLastResult() : T?

    fun getMostRecentResult() : T?

}