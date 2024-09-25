package com.tetsoft.typego.wordslog.data

import com.tetsoft.typego.core.data.Word

class InputWord(private val word: Word) {
    fun getInput() : String {
        val lengthDifference = word.originalText.length - word.inputtedText.length
        if (lengthDifference > 0) {
            return word.inputtedText + word.originalText.substring(word.inputtedText.length, word.inputtedText.length  + lengthDifference)
        }
        return word.inputtedText
    }
}