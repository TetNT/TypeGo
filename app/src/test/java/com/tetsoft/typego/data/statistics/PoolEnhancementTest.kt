package com.tetsoft.typego.data.statistics

import com.tetsoft.typego.statistics.data.PoolEnhancement
import org.junit.Assert.*

import org.junit.Test

class PoolEnhancementTest {

    @Test
    fun provide_resultsCount10PoolSize5_equals5() {
        val poolEnhancement = PoolEnhancement.Base(10, 5).provide()
        assertEquals(5, poolEnhancement)
    }

    @Test
    fun provide_resultsCount11PoolSize5_equals6() {
        val poolEnhancement = PoolEnhancement.Base(11, 5).provide()
        assertEquals(6, poolEnhancement)
    }

    @Test
    fun provide_resultsCount0PoolSize5_equals0() {
        val poolEnhancement = PoolEnhancement.Base(0, 5).provide()
        assertEquals(0, poolEnhancement)
    }

    @Test
    fun provide_resultsCount11PoolSize0_equals0() {
        val poolEnhancement = PoolEnhancement.Base(11, 0).provide()
        assertEquals(0, poolEnhancement)
    }

    @Test
    fun provide_resultsCount100PoolSize5_equals14() {
        val poolEnhancement = PoolEnhancement.Base(100, 5).provide()
        assertEquals(14, poolEnhancement)
    }

    @Test
    fun provide_resultsCount4PoolSize5_equals5() {
        val poolEnhancement = PoolEnhancement.Base(4, 5).provide()
        assertEquals(5, poolEnhancement)
    }
}