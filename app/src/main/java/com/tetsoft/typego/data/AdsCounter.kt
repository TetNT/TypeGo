package com.tetsoft.typego.data

import com.tetsoft.typego.storage.AdsCounterStorage

// This class manages the logic behind showing ads.
// If a user passes shorter tests, then ads will be shown to him less often.
class AdsCounter(private val adsCounterStorage: AdsCounterStorage) {

    companion object {
        const val ADS_COUNTER_VALUE = "ads_counter_value"
        const val MINIMUM_TO_SHOW_AD : Float = 1.1f
    }

    fun addValue(float: Float) {
        val storedValue = adsCounterStorage.getFloat(ADS_COUNTER_VALUE)
        adsCounterStorage.store(ADS_COUNTER_VALUE, storedValue + float)
    }

    fun enoughToShowAd() : Boolean {
        val storedValue = adsCounterStorage.getFloat(ADS_COUNTER_VALUE)
        return (storedValue >= MINIMUM_TO_SHOW_AD)
    }

    fun reset() {
        adsCounterStorage.store(ADS_COUNTER_VALUE, 0f)
    }
}