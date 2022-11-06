package com.tetsoft.typego.data

data class Word(
    val inputtedText: String,
    val originalText: String,
    val incorrectCharsIndices: ArrayList<Int>
) {

    fun isCorrect(): Boolean {
        return incorrectCharsIndices.isEmpty()
    }
}