package com.tetsoft.typego.statistics.data

import com.tetsoft.typego.statistics.domain.PoolEnhancement

/**
 * In the statistics, we calculate average past WPM. If we don't use this enhancement, user will
 * always see the same value, because the first games will always stay the same.
 * With this enhancement, the pool will be extended for each N games.
 */
class PoolEnhancementImpl(private val resultsCount : Int, private val poolInitialSize : Int) : PoolEnhancement {
    override fun provide(): Int {
        if (resultsCount == 0 || poolInitialSize == 0) return 0
        return poolInitialSize + (resultsCount / (poolInitialSize * 2 + 1))
    }
}