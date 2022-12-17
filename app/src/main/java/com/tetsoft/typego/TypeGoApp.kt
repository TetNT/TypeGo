package com.tetsoft.typego

import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.tetsoft.typego.data.AdsCounter
import com.tetsoft.typego.storage.*
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class TypeGoApp : Application() {

    open val adsCounter by lazy {
        AdsCounter(AdsCounterStorage(this))
    }

    open val achievementsProgress by lazy {
        AchievementsProgressStorage(this)
    }

    open val resultListStorage by lazy {
        GameResultListStorage(this)
    }

    val adRequest : AdRequest get() = AdRequest.Builder().build()
}