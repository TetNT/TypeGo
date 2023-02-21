package com.tetsoft.typego.ui.visibility

import android.view.View
import com.tetsoft.typego.data.statistics.VisibilityProvider

interface VisibilityMapper : VisibilityProvider {
    class FromBoolean(private val boolean: Boolean) : VisibilityMapper {
        override fun get(): Int {
            if (boolean) return View.VISIBLE
            return View.GONE
        }
    }
}