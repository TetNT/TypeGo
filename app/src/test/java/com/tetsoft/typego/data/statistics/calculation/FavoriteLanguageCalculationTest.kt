package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.game.GameOnTime
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteLanguageCalculationTest {

    @Test
    fun provide_3english1french_equalsEnglish() {
        val results = ClassicGameModesHistoryList()
        results.add(GameOnTimeMock("EN"))
        results.add(GameOnTimeMock("EN"))
        results.add(GameOnTimeMock("FR"))
        results.add(GameOnTimeMock("EN"))
        val calculation = FavoriteLanguageCalculation(results, LanguageList().getList())
        assertEquals(calculation.provide(), Language("EN"))
    }

    @Test
    fun provide_2italian1english_equalsItalian() {
        val results = ClassicGameModesHistoryList()
        results.add(GameOnTimeMock("IT"))
        results.add(GameOnTimeMock("IT"))
        results.add(GameOnTimeMock("EN"))
        val calculation = FavoriteLanguageCalculation(results, LanguageList().getList())
        assertEquals(calculation.provide(), Language("IT"))
    }

    @Test
    fun provide_emptyResults_equalsEmpty() {
        val results = ClassicGameModesHistoryList()
        val calculation = FavoriteLanguageCalculation(results, LanguageList().getList())
        assertEquals(calculation.provide(), Language(""))
    }

    private class GameOnTimeMock(languageCode: String) :
        GameOnTime(0.0, 0, 0, 0, languageCode, "", "", false, 0, 0, 0L)

}