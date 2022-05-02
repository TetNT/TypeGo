package com.tetsoft.typego.data.achievement

interface AchievementsSource {
    fun provide() : List<Achievement>
}