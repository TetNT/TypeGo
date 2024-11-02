package com.tetsoft.typego.history.data

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.history.domain.DataSelector
import com.tetsoft.typego.history.domain.GameHistoryList

// TODO: JUnits
class DataSelectorImpl<T : GameResult>(private val historyList: GameHistoryList<T>) : DataSelector<T> {
    override fun getBestResult() : T? {
        return historyList.maxByOrNull { result -> result.getWpm() }
    }

    override fun getLastResult() : T? {
        if (historyList.size < 2) return null
        return historyList[historyList.size - 2]
    }

    override fun getMostRecentResult() : T? {
        return historyList.maxByOrNull { result -> result.getCompletionDateTime() }
    }
}