package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.mock.GameOnTimeResultMock
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteLanguageCalculationTest {

    @Test
    fun provide_3english1french_equalsEnglish() {
        val results = GameResultList(listOf(
            GameOnTimeResultMock().getResultGameOnTime(Language("EN"), 1, 1, 1),
            GameOnTimeResultMock().getResultGameOnTime(Language("EN"), 1, 1, 1),
            GameOnTimeResultMock().getResultGameOnTime(Language("FR"), 1, 1, 1),
            GameOnTimeResultMock().getResultGameOnTime(Language("EN"), 1, 1, 1),
        ))
        val calculation = FavoriteLanguageCalculation(results, LanguageList().getList())
        assertEquals(calculation.provide(), Language("EN"))
    }

    @Test
    fun provide_3italian1english_equalsItalian() {
        val results = GameResultList(listOf(
            GameOnTimeResultMock().getResultGameOnTime(Language("IT"), 1, 1, 1),
            GameOnTimeResultMock().getResultGameOnTime(Language("IT"), 1, 1, 1),
            GameOnTimeResultMock().getResultGameOnTime(Language("EN"), 1, 1, 1),
        ))
        val calculation = FavoriteLanguageCalculation(results, LanguageList().getList())
        assertEquals(calculation.provide(), Language("IT"))
    }

    @Test
    fun provide_emptyResults_equalsEmpty() {
        val results = GameResultList()
        val calculation = FavoriteLanguageCalculation(results, LanguageList().getList())
        assertEquals(calculation.provide(), Language(""))
    }
}