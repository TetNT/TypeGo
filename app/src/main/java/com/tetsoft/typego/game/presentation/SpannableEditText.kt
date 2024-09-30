package com.tetsoft.typego.game.presentation

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.tetsoft.typego.core.domain.CharacterStatus
import com.tetsoft.typego.game.data.WordSelection
import kotlin.math.min


class SpannableEditText : AppCompatEditText {
    constructor(context: Context?) : super(context!!)

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    )

    private lateinit var selection: WordSelection

    var autoScrollPosition = 0

    fun selectCurrentWord() {
        val backgroundSpan = BackgroundColorSpan(Color.rgb(0, 80, 100))
        paintBackground(selection.getStartPosition(), selection.getEndPosition(), backgroundSpan)
        updateAutoScrollPosition()
    }

    private fun updateAutoScrollPosition() {
        setSelection(min(autoScrollPosition, text!!.length))
    }

    fun setNextWordCursors() {
        selection.selectNextWord()
    }

    fun getStartPosition() = selection.getStartPosition()

    fun getEndPosition() = selection.getEndPosition()

    fun getSelectedWord() = selection.getSelectedWord()

    fun initializeSelection(text: String) {
        selection = WordSelection(text)
        selection.selectFirstWord()
    }

    fun deselectCurrentWord() {
        clearBackground(selection.getStartPosition(), selection.getEndPosition())
    }

    fun selectCurrentWordAsIncorrect(characterStatuses: ArrayList<CharacterStatus>) {
        val wordLength = selection.getEndPosition() - selection.getStartPosition()
        if (characterStatuses.size > wordLength) {
            text!!.setSpan(
                grayForeground,
                selection.getStartPosition(),
                selection.getEndPosition(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text!!.setSpan(
                UnderlineSpan(),
                selection.getStartPosition(),
                selection.getEndPosition(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return
        }
        for (characterIndex in 0..characterStatuses.size) {
            if (characterIndex >= wordLength) break
            when (characterStatuses[characterIndex]) {
                CharacterStatus.WRONG -> text!!.setSpan(
                    redForeground, selection.getStartPosition() + characterIndex,
                    selection.getStartPosition() + characterIndex + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                CharacterStatus.NOT_FILLED -> text!!.setSpan(
                    grayForeground, selection.getStartPosition() + characterIndex,
                    selection.getStartPosition() + characterIndex + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                CharacterStatus.CORRECT -> {}
            }
        }
    }

    fun selectCurrentWordAsCorrect() {
        text!!.setSpan(
            greenForeground,
            selection.getStartPosition(),
            selection.getEndPosition(),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    fun reachedTheEnd(): Boolean {
        return selection.getEndPosition() >= text!!.length
    }

    fun paintForeground(charIndex: Int, foregroundColor: ForegroundColorSpan) {
        if (this.text == null) return
        if (charIndex + 1 > this.text!!.length) return
        this.text!!.setSpan(foregroundColor, charIndex, charIndex + 1, EXCLUSIVE)
    }

    private fun paintBackground(
        startIndex: Int,
        endIndex: Int,
        backgroundColor: BackgroundColorSpan
    ) {
        if (this.text == null) {
            return
        }
        if (startIndex + 1 > this.text!!.length) {
            return
        }
        if (endIndex + 1 > this.text!!.length) {
            this.text!!.setSpan(backgroundColor, startIndex, text!!.length, EXCLUSIVE)
        }
        this.text!!.setSpan(backgroundColor, startIndex, endIndex, EXCLUSIVE)
    }

    fun clearForeground(startIndex: Int, endIndex: Int) {
        if (this.text == null) return
        val removableSpans = text!!.getSpans(startIndex, endIndex, ForegroundColorSpan::class.java)
        for (span in removableSpans) {
            text!!.removeSpan(span)
        }
    }

    private fun clearBackground(startIndex: Int, endIndex: Int) {
        if (this.text == null) return
        val removableSpans = text!!.getSpans(startIndex, endIndex, BackgroundColorSpan::class.java)
        for (span in removableSpans) {
            this.text!!.removeSpan(span)
        }
    }

    companion object {
        private val GREEN_COLOR = Color.rgb(0, 128, 0)
        private val RED_COLOR = Color.rgb(192, 0, 0)
        private val GRAY_COLOR = Color.rgb(169, 169, 169)
        private const val EXCLUSIVE = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

        @JvmStatic
        val greenForeground: ForegroundColorSpan
            get() = ForegroundColorSpan(GREEN_COLOR)

        @JvmStatic
        val redForeground: ForegroundColorSpan
            get() = ForegroundColorSpan(RED_COLOR)

        @JvmStatic
        val grayForeground: ForegroundColorSpan
            get() = ForegroundColorSpan(GRAY_COLOR)
    }
}