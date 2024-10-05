package com.tetsoft.typego.game.data

import kotlin.math.min

class WordSelection(val text: String) {

    private var startPosition = 0
    private var endPosition = 0

    fun getStartPosition() = startPosition

    fun getEndPosition() = endPosition

    fun getSelectedWord() : String = text.substring(min(text.length, startPosition), min(text.length, endPosition))

    fun selectFirstWord() {
        startPosition = 0
        endPosition = 0
        while (endPosition < text.length && !text[endPosition].isWhitespace()) {
            endPosition++
        }
    }

        fun selectNextWord() {
        if (startPosition >= text.length) {
            startPosition = text.length
            endPosition = text.length
        }
        startPosition = endPosition + 1
        while (startPosition < text.length && text[startPosition].isWhitespace()) {
            startPosition++
        }
        endPosition = startPosition
        while (endPosition < text.length && !text[endPosition].isWhitespace()) {
            endPosition++
        }
    }
}