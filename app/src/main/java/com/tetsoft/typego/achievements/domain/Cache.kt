package com.tetsoft.typego.achievements.domain

interface Cache {
    fun put(value : Int)

    fun get() : Int

    fun isCached() : Boolean

}