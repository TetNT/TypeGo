package com.tetsoft.typego

import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.tetsoft.typego.data.AdsCounter
import com.tetsoft.typego.data.account.UserPreferences
import com.tetsoft.typego.storage.*
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class TypeGoApp : Application() {

    open val preferences by lazy {
        UserPreferences(UserPreferencesStorage(this))
    }

    open val adsCounter by lazy {
        AdsCounter(AdsCounterStorage(this))
    }

    open val achievementsProgress by lazy {
        AchievementsProgressStorage(this)
    }

    open val resultListStorage by lazy {
        GameResultListStorage(this)
    }

    open val adRequest: AdRequest by lazy {
        AdRequest.Builder().build()
    }
}