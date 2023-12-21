package com.tetsoft.typego.utils

import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation

interface AnimationsPreset {
    fun get(): Animation

    class FadeIn(private val duration: Long) : AnimationsPreset {
        override fun get(): Animation {
            val fadeIn = AlphaAnimation(0f, 1f)
            fadeIn.interpolator = DecelerateInterpolator()
            fadeIn.duration = duration
            fadeIn.fillAfter = true
            return fadeIn
        }
    }

    class FadeOut(private val duration: Long) : AnimationsPreset {
        override fun get(): Animation {
            val fadeOut = AlphaAnimation(1f, 0f)
            fadeOut.interpolator = AccelerateInterpolator()
            fadeOut.duration = duration
            fadeOut.fillAfter = true
            return fadeOut
        }
    }

    class SlideIn(private val duration: Long, private val fromX: Float, private val fromY: Float) :
        AnimationsPreset {
        override fun get(): Animation {
            val slideIn = TranslateAnimation(fromX, 0f, fromY, 0f)
            slideIn.interpolator = DecelerateInterpolator()
            slideIn.duration = duration
            slideIn.fillAfter = true
            return slideIn
        }
    }

    class SlideOut(private val duration: Long, private val toX: Float, private val toY: Float) :
        AnimationsPreset {
        override fun get(): Animation {
            val slideOut = TranslateAnimation(0f, toX, 0f, toY)
            slideOut.interpolator = AccelerateInterpolator()
            slideOut.duration = duration
            slideOut.fillAfter = true
            return slideOut
        }
    }
}