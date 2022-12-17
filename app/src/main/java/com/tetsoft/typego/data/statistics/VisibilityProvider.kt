package com.tetsoft.typego.data.statistics

import android.view.View

interface VisibilityProvider {

    fun get() : Int

    class Visible : VisibilityProvider {
        override fun get(): Int {
            return View.VISIBLE
        }
    }

    class Gone : VisibilityProvider {
        override fun get(): Int {
            return View.GONE
        }
    }
}