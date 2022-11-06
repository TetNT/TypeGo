package com.tetsoft.typego.game.result

import com.tetsoft.typego.mock.GameResultMock
import org.junit.Assert.*

import org.junit.Test

class GameResultTest {

    private val delta = 0.0001
    @Test
    fun getWpm_score0time15_equals0() {
        val gameResult = GameResultMock().getSimpleGameResult(15, 0)
        assertEquals(gameResult.wpm, 0.0, delta)
    }

    @Test
    fun getWpm_score15time0_equals0() {
        val gameResult = GameResultMock().getSimpleGameResult(0, 15)
        assertEquals(gameResult.wpm, 0.0, delta)
    }

    @Test
    fun getWpm_score0time0_equals0() {
        val gameResult = GameResultMock().getSimpleGameResult(0, 0)
        assertEquals(gameResult.wpm, 0.0, delta)
    }

    @Test
    fun getWpm_score60time60_equals15() {
        val gameResult = GameResultMock().getSimpleGameResult(60, 60)
        assertEquals(gameResult.wpm, 15.0, delta)
    }
}