package com.tetsoft.typego.core.data.requirement

import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.RandomWordsMock
import com.tetsoft.typego.history.data.GameHistoryImpl
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SharpEyeRequirementTest {

    @Test
    fun isReached_lastResultTimeSpentEqualsRequiredWpmMoreThan30_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                MockRandomWords(31.0, 60)
            ), emptyList()
        )
        assertTrue(SharpEyeRequirement(60).isReached(list))
    }

    @Test
    fun isReached_lastResultTimeSpent120WpmMoreThan30_assertTrue() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                MockRandomWords(31.0, 120)
            ), emptyList()
        )
        assertTrue(SharpEyeRequirement(120).isReached(list))
    }

    @Test
    fun isReached_lastResultTimeSpentNotEquals_assertFalse() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                MockRandomWords(31.0, 60)
            ), emptyList()
        )
        assertFalse(SharpEyeRequirement(30).isReached(list))
    }

    @Test
    fun isReached_wpmLessThan30_assertFalse() {
        val list = GameHistoryImpl(
            listOf<RandomWords>(
                MockRandomWords(25.0, 60)
            ), emptyList()
        )
        assertFalse(SharpEyeRequirement(60).isReached(list))
    }

    @Test
    fun isReached_resultListEmpty_assertFalse() {
        val list = GameHistoryImpl(emptyList(), emptyList())
        assertFalse(SharpEyeRequirement(60).isReached(list))
    }


    private class MockRandomWords(private val wpm: Double, private val chosenTime: Int) : RandomWordsMock() {
        override fun getWpm() = wpm
        override fun getTimeSpent() = chosenTime
    }
}