package com.tetsoft.typego.core.domain

import android.content.pm.ActivityInfo

interface OrientationProvider {
    fun get() : Int
}

enum class ScreenOrientation(val id: Int) : OrientationProvider {

    PORTRAIT(0) {
        override fun get() = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    },
    LANDSCAPE(1) {
        override fun get() = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}