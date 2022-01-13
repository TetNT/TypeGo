package com.tetsoft.typego.storage

interface StringValueStorage {
    fun store(key: String?, value: String?)

    fun getString(key: String?): String
}