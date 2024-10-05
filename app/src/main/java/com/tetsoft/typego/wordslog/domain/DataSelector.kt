package com.tetsoft.typego.wordslog.domain

interface DataSelector<T> {
    fun getNextData(upTo: Int) : List<T>
}