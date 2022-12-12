package com.tetsoft.typego.adapter.selector

interface DataSelector<T> {
    fun getNextData(upTo: Int) : List<T>

    class FiniteSelector<T: Any>(private val data: List<T>) : DataSelector<T> {

        var lastPosition = 0

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
}