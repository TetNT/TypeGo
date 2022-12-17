package com.tetsoft.typego.data

interface AdsCounterManager {

    fun addValue(float: Float)

    fun enoughToShowAd(): Boolean

    fun reset()

    fun getMinimalValueToShowAd() : Float

}