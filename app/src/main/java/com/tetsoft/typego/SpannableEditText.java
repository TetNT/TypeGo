package com.tetsoft.typego;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;


public class SpannableEditText extends androidx.appcompat.widget.AppCompatEditText {
    private static final int GREEN_COLOR = Color.rgb(0, 128, 0);
    private static final int RED_COLOR = Color.rgb(192, 0, 0);

    private static final ForegroundColorSpan FOREGROUND_GREEN = new ForegroundColorSpan(GREEN_COLOR);
    private static final ForegroundColorSpan FOREGROUND_RED = new ForegroundColorSpan(RED_COLOR);

    public SpannableEditText(Context context) {
        super(context);
    }

    public SpannableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpannableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void paintForeground(int charIndex, ForegroundColorSpan foregroundColor) {
        if (this.getText() == null) return;
        if (charIndex + 1 > this.getText().length()) return;
        this.getText().setSpan(
                foregroundColor,
                charIndex,
                charIndex + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void clearPaint(int startIndex, int endIndex) {
        if (this.getText() == null) return;
        final Object[] removableSpans = this.getText().getSpans(startIndex, endIndex, Object.class);
        for (final Object span : removableSpans) {
            if (span instanceof ForegroundColorSpan) {
                this.getText().removeSpan(span);
            }
        }
    }
}

