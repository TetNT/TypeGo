package com.tetsoft.typego.data.history

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.game.OwnText
import com.tetsoft.typego.data.game.RandomWords
import com.tetsoft.typego.data.language.Language
import org.junit.Assert.assertEquals
import org.junit.Test

class GameResultWithWordsInformationTest {

    @Test
    fun constructor_randomWordsResult_mappedProperly() {
        val wpm = 25.0
        val cpm = 100
        val charsWritten = 120
        val chosenTime = 60
        val language = Language.EN
        val dictionary = DictionaryType.BASIC.name
        val screenOrientation = ScreenOrientation.PORTRAIT.name
        val suggestionsActivated = true
        val ignoreCase = true
        val wordsWritten = 30
        val correctWords = 29
        val completionDateTime = 100L
        val seed = "123f"
        val randomWords = RandomWords(
            wpm,
            cpm,
            charsWritten,
            chosenTime,
            language,
            dictionary,
            screenOrientation,
            suggestionsActivated,
            ignoreCase,
            wordsWritten,
            correctWords,
            completionDateTime,
            seed
        )
        val gameResultWithWordsInformation = GameHistory.GameResultWithWordsInformation(randomWords)
        assertEquals(wpm, gameResultWithWordsInformation.getWpm(), 0.01)
        assertEquals(cpm, gameResultWithWordsInformation.getCpm())
        assertEquals(charsWritten, gameResultWithWordsInformation.getCharsWritten())
        assertEquals(chosenTime, gameResultWithWordsInformation.getTimeSpent())
        assertEquals(screenOrientation, gameResultWithWordsInformation.getScreenOrientation().name)
        assertEquals(suggestionsActivated, gameResultWithWordsInformation.areSuggestionsActivated())
        assertEquals(ignoreCase, gameResultWithWordsInformation.ignoreCase())
        assertEquals(wordsWritten, gameResultWithWordsInformation.getWordsWritten())
        assertEquals(correctWords, gameResultWithWordsInformation.getCorrectWords())
        assertEquals(completionDateTime, gameResultWithWordsInformation.getCompletionDateTime())
    }

    @Test
    fun constructor_ownTextResult_mappedProperly() {
        val text = "a b c"
        val wpm = 25.0
        val cpm = 100
        val charsWritten = 120
        val chosenTime = 60
        val timeSpent = 60
        val screenOrientation = ScreenOrientation.PORTRAIT.name
        val suggestionsActivated = true
        val ignoreCase = true
        val wordsWritten = 30
        val correctWords = 29
        val completionDateTime = 100L
        val ownText = OwnText(
            text,
            wpm,
            cpm,
            chosenTime,
            timeSpent,
            charsWritten,
            suggestionsActivated,
            screenOrientation,
            ignoreCase,
            completionDateTime,
            wordsWritten,
            correctWords,
        )
        val gameResultWithWordsInformation = GameHistory.GameResultWithWordsInformation(ownText)
        assertEquals(wpm, gameResultWithWordsInformation.getWpm(), 0.01)
        assertEquals(cpm, gameResultWithWordsInformation.getCpm())
        assertEquals(charsWritten, gameResultWithWordsInformation.getCharsWritten())
        assertEquals(chosenTime, gameResultWithWordsInformation.getTimeSpent())
        assertEquals(screenOrientation, gameResultWithWordsInformation.getScreenOrientation().name)
        assertEquals(suggestionsActivated, gameResultWithWordsInformation.areSuggestionsActivated())
        assertEquals(ignoreCase, gameResultWithWordsInformation.ignoreCase())
        assertEquals(wordsWritten, gameResultWithWordsInformation.getWordsWritten())
        assertEquals(correctWords, gameResultWithWordsInformation.getCorrectWords())
        assertEquals(completionDateTime, gameResultWithWordsInformation.getCompletionDateTime())
    }
}