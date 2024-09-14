package com.tetsoft.typego.achievements.data

interface Cache {
    fun put(value : Int)

    fun get() : Int

    fun isCached() : Boolean

}