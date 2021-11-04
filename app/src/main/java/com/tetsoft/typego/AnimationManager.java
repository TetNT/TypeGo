package com.tetsoft.typego;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Collection;

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

    public void applyAnimationToEachView(@NonNull Collection<View> views,
                                         AnimationSet animationSet,
                                         long offset,
                                         boolean offsetSequentially) {
        int i = 0;
        for (View view: views) {
            if (offsetSequentially)
                animationSet.setStartOffset(offset * i);
            else animationSet.setStartOffset(offset);
            view.setAnimation(animationSet);
            i++;
        }
    }

}
