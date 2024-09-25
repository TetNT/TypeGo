package com.tetsoft.typego.wordslog.data

import com.tetsoft.typego.wordslog.domain.DataSelector

// TODO: JUnits
class FiniteSelector<T: Any>(private val data: List<T>) : DataSelector<T> {

    private var lastPosition = 0

    override fun getNextData(upTo: Int) : ArrayList<T> {
        val selection = ArrayList<T>()

        val limit = Integer.min(upTo + lastPosition, data.size)
        for (i in lastPosition until limit) {
            selection.add(data[i])
        }
        lastPosition = limit
        return selection
    }
}