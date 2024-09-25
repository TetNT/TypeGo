package com.tetsoft.typego.core.ui

import android.view.View
import com.tetsoft.typego.statistics.domain.VisibilityProvider

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