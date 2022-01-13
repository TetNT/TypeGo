package com.tetsoft.typego

import android.app.Application
import com.tetsoft.typego.account.User
import com.tetsoft.typego.account.UserPreferences
import com.tetsoft.typego.storage.AdsCounterStorage
import com.tetsoft.typego.storage.UserPreferencesStorage
import com.tetsoft.typego.storage.UserStorage

open class TypeGoApp : Application() {

    open val userStorage by lazy {
        UserStorage(this)
    }

    open val preferences by lazy {
        UserPreferences(UserPreferencesStorage(this))
    }

    open val adsCounter by lazy {
        AdsCounter(AdsCounterStorage(this))
    }
}