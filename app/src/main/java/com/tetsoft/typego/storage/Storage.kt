package com.tetsoft.typego.storage

interface Storage {
    fun store(key: String?, value: String?)

    fun getString(key: String?): Any
}