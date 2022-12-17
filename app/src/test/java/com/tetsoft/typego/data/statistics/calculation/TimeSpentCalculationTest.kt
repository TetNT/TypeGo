package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.game.mode.GameMode
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.game.result.GameResultList
import org.junit.Assert.*

import org.junit.Test

class TimeSpentCalculationTest {

    @Test
    fun provide_differentResults_equals11() {
        val results = GameResultList(listOf(
            GameResult(GameMode.Empty(), 0, 600, 0, 0, 0),
            GameResult(GameMode.Empty(), 0, 60, 0, 0, 0),
            GameResult(GameMode.Empty(), 0, 15, 0, 0, 0),
            GameResult(GameMode.Empty(), 0, 30, 0, 0, 0),
        ))
        val calculation = TimeSpentCalculation(results)
        assertEquals(calculation.provide(), 11)
    }

    @Test
    fun provide_differentResults_equals0() {
        val results = GameResultList(listOf(
            GameResult(GameMode.Empty(), 0, 15, 0, 0, 0),
            GameResult(GameMode.Empty(), 0, 30, 0, 0, 0),
        ))
        val calculation = TimeSpentCalculation(results)
        assertEquals(calculation.provide(), 0)
    }

    @Test
    fun provide_emptyResults_equals0() {
        val results = GameResultList()
        val calculation = TimeSpentCalculation(results)
        assertEquals(calculation.provide(), 0)
    }
}