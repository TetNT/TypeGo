package com.tetsoft.typego.data.cache

interface Cache {
    fun put(value : Int)

    fun get() : Int

    fun isCached() : Boolean

}