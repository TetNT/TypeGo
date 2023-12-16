package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteLanguageCalculationTest {

    @Test
    fun provide_3english1french_equalsEnglish() {
        val results = ClassicGameModesHistoryList()
        results.add(MockGameOnTime("EN"))
        results.add(MockGameOnTime("EN"))
        results.add(MockGameOnTime("FR"))
        results.add(MockGameOnTime("EN"))
        val calculation = FavoriteLanguageCalculation(results, Language.LANGUAGE_LIST)
        assertEquals(calculation.provide(), Language("EN"))
    }

    @Test
    fun provide_2italian1english_equalsItalian() {
        val results = ClassicGameModesHistoryList()
        results.add(MockGameOnTime("IT"))
        results.add(MockGameOnTime("IT"))
        results.add(MockGameOnTime("EN"))
        val calculation = FavoriteLanguageCalculation(results, Language.LANGUAGE_LIST)
        assertEquals(calculation.provide(), Language("IT"))
    }

    @Test
    fun provide_emptyResults_equalsEmpty() {
        val results = ClassicGameModesHistoryList()
        val calculation = FavoriteLanguageCalculation(results, Language.LANGUAGE_LIST)
        assertEquals(calculation.provide(), Language(""))
    }

    private class MockGameOnTime(languageCode: String) :
        GameOnTime(0.0, 0, 0, 0, languageCode, "", "", false, 0, 0, 0L)

}