package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.game.result.GameResultList
import org.junit.Assert.*

import org.junit.Test

class TotalWordsWrittenCalculationTest {

    @Test
    fun provide_variousResults_equals130() {
        val results = GameResultList(listOf(
            GameResult(GameMode.Empty(), 0, 0, 40, 0, 0),
            GameResult(GameMode.Empty(), 0, 0, 50, 0, 0),
            GameResult(GameMode.Empty(), 0, 0, 30, 0, 0),
            GameResult(GameMode.Empty(), 0, 0, 10, 0, 0)
        ))
        val calculation = TotalWordsWrittenCalculation(results)
        assertEquals(calculation.provide(), 130)
    }

    @Test
    fun provide_emptyResults_equals0() {
        val results = GameResultList()
        val calculation = TotalWordsWrittenCalculation(results)
        assertEquals(calculation.provide(), 0)
    }

}