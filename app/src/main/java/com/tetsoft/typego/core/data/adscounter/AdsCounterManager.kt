package com.tetsoft.typego.core.data.adscounter

interface AdsCounterManager {

    fun addValue(float: Float)

    fun enoughToShowAd(): Boolean

    fun reset()

    fun getMinimalValueToShowAd() : Float

}