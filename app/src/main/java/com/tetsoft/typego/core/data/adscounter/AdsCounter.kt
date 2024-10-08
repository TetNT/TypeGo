package com.tetsoft.typego.core.data.adscounter

/**
 * This class manages the logic behind showing ads.
 * If a user passes shorter tests, then ads will be shown to him less often.
  */
class AdsCounter(private val adsCounterStorage: AdsCounterStorage) : AdsCounterManager {

    private companion object {
        const val MINIMUM_TO_SHOW_AD : Float = 1.1f
    }

    override fun addValue(float: Float) {
        val storedValue = adsCounterStorage.getFloat()
        adsCounterStorage.store(storedValue + float)
    }

    override fun enoughToShowAd() : Boolean {
        val storedValue = adsCounterStorage.getFloat()
        return (storedValue >= MINIMUM_TO_SHOW_AD)
    }

    override fun reset() {
        adsCounterStorage.store(0f)
    }

    override fun getMinimalValueToShowAd(): Float {
        return MINIMUM_TO_SHOW_AD
    }
}