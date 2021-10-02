package com.tetsoft.typego.testing

import java.io.Serializable

data class Word(
        val inputtedText : String,
        val originalText : String,
        val incorrectCharsIndices : ArrayList<Int>) : Serializable