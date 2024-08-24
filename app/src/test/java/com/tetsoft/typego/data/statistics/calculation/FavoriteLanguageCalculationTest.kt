package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.RandomWordsLanguageCodeMock
import com.tetsoft.typego.data.game.GameResult
import com.tetsoft.typego.data.language.Language
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteLanguageCalculationTest {

    @Test
    fun provide_3english1french_equalsEnglish() {
        val results = ArrayList<GameResult.WithLanguage>()
        results.add(RandomWordsLanguageCodeMock("EN"))
        results.add(RandomWordsLanguageCodeMock("EN"))
        results.add(RandomWordsLanguageCodeMock("FR"))
        results.add(RandomWordsLanguageCodeMock("EN"))
        val calculation = FavoriteLanguageCalculation(results, Language.LANGUAGE_LIST)
        assertEquals(calculation.provide(), Language("EN"))
    }

    @Test
    fun provide_2italian1english_equalsItalian() {
        val results = ArrayList<GameResult.WithLanguage>()
        results.add(RandomWordsLanguageCodeMock("IT"))
        results.add(RandomWordsLanguageCodeMock("IT"))
        results.add(RandomWordsLanguageCodeMock("EN"))
        val calculation = FavoriteLanguageCalculation(results, Language.LANGUAGE_LIST)
        assertEquals(calculation.provide(), Language("IT"))
    }

    @Test
    fun provide_emptyResults_equalsEmpty() {
        val results = ArrayList<GameResult.WithLanguage>()
        val calculation = FavoriteLanguageCalculation(results, Language.LANGUAGE_LIST)
        assertEquals(calculation.provide(), Language(""))
    }
}