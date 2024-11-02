package com.tetsoft.typego.achievements.domain

data class AchievementsCompletionPair(val achievementId: Int, val completionDateTimeLong: Long) {
    fun isCompleted() : Boolean = completionDateTimeLong != 0L
}
