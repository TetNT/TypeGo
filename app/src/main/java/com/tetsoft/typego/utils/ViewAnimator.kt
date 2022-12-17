package com.tetsoft.typego.utils

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.TextView
import java.lang.Math.round
import kotlin.math.roundToInt

class ViewAnimator(private val view : View) {
    fun applySlideInAnimation(fromX: Float, fromY: Float, duration: Long) {
        val slideIn = TranslateAnimation(fromX, 0f, fromY, 0f)
        slideIn.interpolator = DecelerateInterpolator()
        slideIn.duration = duration
        view.animation = slideIn
    }

    fun getFadeInAnimation(duration: Long) {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator()
        fadeIn.duration = duration
        view.animation = fadeIn
    }

    fun getCountAnimation(start: Int, end: Int, duration: Long): ValueAnimator {
        val valueAnimator = ValueAnimator()
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration
        valueAnimator.setObjectValues(start, end)
        valueAnimator.setEvaluator { fraction, startValue, endValue ->
            (startValue as Float + (endValue as Float - startValue) * fraction).roundToInt()
        }
        return valueAnimator
    }

    fun applyCountAnimation(animator: ValueAnimator, view: TextView) {
        animator.addUpdateListener { animation: ValueAnimator ->
            view.text = animation.animatedValue.toString()
        }
    }
}