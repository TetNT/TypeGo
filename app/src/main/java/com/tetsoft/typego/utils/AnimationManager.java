package com.tetsoft.typego.utils;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;

// TODO: make a public constructor that receives a view, so class is no longer static-like.
@Deprecated()
public class AnimationManager {

    public TranslateAnimation getSlideInAnimation(float fromX, float fromY, long duration) {
        TranslateAnimation slideIn = new TranslateAnimation(fromX, 0f, fromY, 0f);
        slideIn.setInterpolator(new DecelerateInterpolator());
        slideIn.setDuration(duration);
        return slideIn;
    }

    public AlphaAnimation getFadeInAnimation(long duration) {
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(duration);
        return fadeIn;
    }

    public ValueAnimator getCountAnimation(int start, int end, long duration) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.setObjectValues(start, end);
        valueAnimator.setEvaluator(new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        return valueAnimator;
    }

    public void applyCountAnimation(@NonNull ValueAnimator animator, @NonNull TextView view) {
        animator.addUpdateListener(animation -> view.setText(String.valueOf(animation.getAnimatedValue())));
    }

}
