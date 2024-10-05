package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.RandomWordsLanguageCodeMock
import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.core.domain.Language
import com.tetsoft.typego.core.domain.LanguageList
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
        val calculation = FavoriteLanguageCalculation(results, LanguageList().getPlayableLanguages())
        assertEquals(calculation.provide(), Language("EN"))
    }

    @Test
    fun provide_2italian1english_equalsItalian() {
        val results = ArrayList<GameResult.WithLanguage>()
        results.add(RandomWordsLanguageCodeMock("IT"))
        results.add(RandomWordsLanguageCodeMock("IT"))
        results.add(RandomWordsLanguageCodeMock("EN"))
        val calculation = FavoriteLanguageCalculation(results, LanguageList().getPlayableLanguages())
        assertEquals(calculation.provide(), Language("IT"))
    }

    @Test
    fun provide_emptyResults_equalsEmpty() {
        val results = ArrayList<GameResult.WithLanguage>()
        val calculation = FavoriteLanguageCalculation(results, LanguageList().getPlayableLanguages())
        assertEquals(calculation.provide(), Language(""))
    }
}