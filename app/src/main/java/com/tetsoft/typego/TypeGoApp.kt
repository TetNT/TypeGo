package com.tetsoft.typego

import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.tetsoft.typego.data.AdsCounter
import com.tetsoft.typego.storage.*
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class TypeGoApp : Application() {

    // TODO: Remove, it should be handled by a Hilt module
    open val adsCounter by lazy {
        AdsCounter(AdsCounterStorage(this))
    }

    // TODO: Check if it's possible to put it inside of a Hilt module
    val adRequest : AdRequest get() = AdRequest.Builder().build()
}