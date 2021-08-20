package com.tetsoft.typego

import java.io.Serializable

data class Word(
        val inputtedText : String,
        val originalText : String,
        val incorrectCharsIndices : ArrayList<Int>) : Serializable