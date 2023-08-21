package com.tetsoft.typego.game.result

import com.tetsoft.typego.mock.GameOnTimeResultMock
import org.junit.Assert.*

import org.junit.Test

class GameResultTest {

    private val delta = 0.0001
    @Test
    fun getWpm_score0time15_equals0() {
        val gameResult = GameOnTimeResultMock().getSimpleGameResult(15, 0)
        assertEquals(gameResult.wpm, 0.0, delta)
    }

    @Test
    fun getWpm_score15time0_equals0() {
        val gameResult = GameOnTimeResultMock().getSimpleGameResult(0, 15)
        assertEquals(gameResult.wpm, 0.0, delta)
    }

    @Test
    fun getWpm_score0time0_equals0() {
        val gameResult = GameOnTimeResultMock().getSimpleGameResult(0, 0)
        assertEquals(gameResult.wpm, 0.0, delta)
    }

    @Test
    fun getWpm_score60time60_equals15() {
        val gameResult = GameOnTimeResultMock().getSimpleGameResult(60, 60)
        assertEquals(gameResult.wpm, 15.0, delta)
    }

    @Test
    fun getWpm_score240time60_equals60() {
        val gameResult = GameOnTimeResultMock().getSimpleGameResult(60, 240)
        assertEquals(gameResult.wpm, 60.0, delta)
    }

    @Test
    fun getWpm_score60time15_equals60() {
        val gameResult = GameOnTimeResultMock().getSimpleGameResult(15, 60)
        assertEquals(gameResult.wpm, 60.0, delta)
    }
}