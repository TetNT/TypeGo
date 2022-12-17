package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.mock.GameResultGameOnTimeListMock
import org.junit.Assert.*

import org.junit.Test

class BestResultCalculationTest {

    @Test
    fun provide_withPreparedData_equals70() {
        val preparedData = GameResultGameOnTimeListMock().getPreparedTestSet()
        assertEquals(70, BestResultCalculation(preparedData).provide())
    }

    @Test
    fun provide_emptyList_equals0() {
        val preparedData = GameResultList()
        assertEquals(0, BestResultCalculation(preparedData).provide())
    }
}