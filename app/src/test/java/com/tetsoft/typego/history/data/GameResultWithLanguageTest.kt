package com.tetsoft.typego.history.data

import com.tetsoft.typego.core.data.ScreenOrientation
import com.tetsoft.typego.core.domain.DictionaryType
import com.tetsoft.typego.core.domain.Language
import com.tetsoft.typego.core.domain.RandomWords
import org.junit.Assert.assertEquals
import org.junit.Test

class GameResultWithLanguageTest {
    @Test
    fun constructor_randomWordsResult_mappedProperly() {
        val wpm = 25.0
        val cpm = 100
        val charsWritten = 120
        val timeSpent = 60
        val languageCode = Language.EN
        val dictionaryType = DictionaryType.BASIC.name
        val screenOrientation = ScreenOrientation.PORTRAIT.name
        val suggestionsActivated = true
        val ignoreCase = true
        val wordsWritten = 30
        val correctWords = 29
        val completionDateTime = 100L
        val seed = "123f"
        val randomWords = RandomWords(wpm,
            cpm,
            charsWritten,
            timeSpent,
            languageCode,
            dictionaryType,
            screenOrientation,
            suggestionsActivated,
            ignoreCase,
            wordsWritten,
            correctWords,
            completionDateTime,
            seed)
        val gameResultWithLanguage = GameResultWithLanguage(randomWords)
        assertEquals(wpm, gameResultWithLanguage.getWpm(), 0.01)
        assertEquals(cpm, gameResultWithLanguage.getCpm())
        assertEquals(charsWritten, gameResultWithLanguage.getCharsWritten())
        assertEquals(timeSpent, gameResultWithLanguage.getTimeSpent())
        assertEquals(screenOrientation, gameResultWithLanguage.getScreenOrientation().name)
        assertEquals(suggestionsActivated, gameResultWithLanguage.areSuggestionsActivated())
        assertEquals(ignoreCase, gameResultWithLanguage.ignoreCase())
        assertEquals(languageCode, gameResultWithLanguage.getLanguageCode())
        assertEquals(dictionaryType, gameResultWithLanguage.getDictionaryType().name)
        assertEquals(completionDateTime, gameResultWithLanguage.getCompletionDateTime())
    }
}