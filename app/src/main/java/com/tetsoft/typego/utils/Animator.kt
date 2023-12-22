package com.tetsoft.typego.utils

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import kotlin.math.roundToInt

interface Animator {
    fun get(): ValueAnimator

    class CountUp(
        private val duration: Long,
        private val endValue: Int
    ) : Animator {
        override fun get(): ValueAnimator {
            val valueAnimator = ValueAnimator()
            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.duration = duration
            valueAnimator.setObjectValues(0, endValue)
            valueAnimator.setEvaluator(object : TypeEvaluator<Int> {
                override fun evaluate(fraction: Float, start: Int?, end: Int?): Int {
                    return (start!! + (end!! - start) * fraction).roundToInt()
                }

            })
            return valueAnimator
        }

    }
}