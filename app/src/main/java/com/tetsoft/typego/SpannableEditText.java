package com.tetsoft.typego;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;


public class SpannableEditText extends androidx.appcompat.widget.AppCompatEditText {
    private static final int GREEN_COLOR = Color.rgb(0, 128, 0);
    private static final int RED_COLOR = Color.rgb(192, 0, 0);
    private static final int EXCLUSIVE = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

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
        this.getText().setSpan(foregroundColor, charIndex, charIndex + 1, EXCLUSIVE);
    }

    public void paintForeground(int charIndex, int endIndex, ForegroundColorSpan foregroundColor) {
        if (this.getText() == null) return;
        if (charIndex + 1 > this.getText().length()) return;
        this.getText().setSpan(foregroundColor, charIndex, endIndex, EXCLUSIVE);
    }

    public void paintBackground(int startIndex, int endIndex, BackgroundColorSpan backgroundColor) {
        if (this.getText() == null) return;
        if (startIndex + 1 > this.getText().length() || endIndex + 1 > this.getText().length())
            return;
        this.getText().setSpan(backgroundColor, startIndex, endIndex, EXCLUSIVE);
    }

    public void clearForeground(int startIndex, int endIndex) {
        if (this.getText() == null) return;
        final Object[] removableSpans = this.getText().getSpans(startIndex, endIndex, Object.class);
        for (final Object span : removableSpans) {
            if (span instanceof ForegroundColorSpan) {
                this.getText().removeSpan(span);
            }
        }
    }

    public void clearBackground(int startIndex, int endIndex) {
        if (this.getText() == null) return;
        final Object[] removableSpans = this.getText().getSpans(startIndex, endIndex, Object.class);
        for (final Object span : removableSpans) {
            if (span instanceof BackgroundColorSpan || span instanceof StyleSpan || span instanceof ForegroundColorSpan) {
                this.getText().removeSpan(span);
            }
        }
    }

    public static ForegroundColorSpan getGreenForeground() {
        return new ForegroundColorSpan(GREEN_COLOR);
    }

    public static ForegroundColorSpan getRedForeground() {
        return new ForegroundColorSpan(RED_COLOR);
    }
}

