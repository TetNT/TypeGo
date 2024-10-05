package com.tetsoft.typego.achievements.domain

abstract class AchievementProgressCache : Cache {
    private var rememberedValue : Int = 0
    private var isCached = false

    override fun put(value: Int) {
        rememberedValue = value
        isCached = true
    }

    override fun get(): Int = rememberedValue

    override fun isCached(): Boolean = isCached

    class Standard : AchievementProgressCache()
}

