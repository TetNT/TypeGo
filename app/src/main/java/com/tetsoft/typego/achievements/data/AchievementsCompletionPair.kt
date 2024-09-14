package com.tetsoft.typego.achievements.data

data class AchievementsCompletionPair(val achievementId: Int, val completionDateTimeLong: Long) {
    fun isCompleted() : Boolean = completionDateTimeLong != 0L
}
