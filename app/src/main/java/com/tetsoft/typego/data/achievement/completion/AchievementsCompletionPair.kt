package com.tetsoft.typego.data.achievement.completion

data class AchievementsCompletionPair(val achievementId: Int, val completionDateTimeLong: Long) {
    fun isCompleted() : Boolean = completionDateTimeLong != 0L
}
