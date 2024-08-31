package com.tetsoft.typego.ui

import android.view.View
import com.tetsoft.typego.data.statistics.VisibilityProvider

interface VisibilityMapper : VisibilityProvider {

    open class VisibleGone(private val statement: Boolean) : VisibilityMapper {
        override fun get(): Int {
            if (statement == true) return View.VISIBLE
            return View.GONE
        }
    }
    // TODO: JUnits
    open class VisibleInvisible(private val statement: Boolean) : VisibilityMapper {
        override fun get(): Int {
            if (statement == true) return View.VISIBLE
            return View.INVISIBLE
        }
    }

    class FromBoolean(boolean: Boolean) : VisibleGone(boolean)

}