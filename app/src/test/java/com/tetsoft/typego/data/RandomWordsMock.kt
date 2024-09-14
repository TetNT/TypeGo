package com.tetsoft.typego.data

import com.tetsoft.typego.core.domain.DictionaryType
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.core.domain.ScreenOrientation

open class RandomWordsMock :
    RandomWords(
        0.0,
        0,
        0,
        0,
        "ALL",
        DictionaryType.BASIC.name,
        ScreenOrientation.PORTRAIT.name,
        false,
        false,
        0,
        0,
        0L
    )

class RandomWordsWpmMock(private val wpm: Double) : RandomWordsMock() {
    override fun getWpm() = wpm
}

class RandomWordsLanguageCodeMock(private val languageCode: String) : RandomWordsMock() {
    override fun getLanguageCode() = languageCode
}

class TimeGameCompletionDateTimeMock(private val completionDateTime: Long) : RandomWordsMock() {
    override fun getCompletionDateTime() = completionDateTime
}