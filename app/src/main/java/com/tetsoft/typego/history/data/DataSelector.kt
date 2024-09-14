package com.tetsoft.typego.history.data

import com.tetsoft.typego.core.domain.GameResult
// TODO: JUnits
interface DataSelector<T : GameResult> {
    fun getBestResult() : T?

    fun getLastResult() : T?

    fun getMostRecentResult() : T?

    abstract class Abstract<T : GameResult>(private val historyList: GameHistoryList<T>) : DataSelector<T> {
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


    class Standard<T : GameResult>(historyList: GameHistoryList<T>) : Abstract<T>(historyList)
}