package com.tetsoft.typego.core.data

import android.content.pm.ActivityInfo
import com.tetsoft.typego.core.domain.OrientationProvider

enum class ScreenOrientation(val id: Int) : OrientationProvider {

    PORTRAIT(0) {
        override fun get() = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    },
    LANDSCAPE(1) {
        override fun get() = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}