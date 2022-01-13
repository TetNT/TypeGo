package com.tetsoft.typego.storage

interface FloatValueStorage {
    fun store(key: String?, value: Float)

    fun getFloat(key: String?): Float
}