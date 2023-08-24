package com.tetsoft.typego.data.wordscount

abstract class WordsCountOptions : ArrayList<Int>() {
    class Default : WordsCountOptions() {
        init {
            addAll(listOf(
                10,
                20,
                50,
                100,
                200,
                300)
            )
        }
    }
}