package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.achievement.Achievement
import com.tetsoft.typego.data.achievement.completion.AchievementsCompletionPair
import com.tetsoft.typego.data.statistics.VisibilityProvider

class LastCompletedAchievementCalculation(
    private val achievementsProgressList: List<AchievementsCompletionPair>,
    private val achievementsList: List<Achievement>
) :
    StatisticsCalculation {

    override fun provide(): Achievement {
        var lastCompletionDate = 0L
        var lastCompletedAchievement: Achievement = Achievement.Empty()
        for (achievement in achievementsProgressList) {
            val completionTimeMillis: Long =
                achievement.completionDateTimeLong
            if (completionTimeMillis > lastCompletionDate) {
                lastCompletedAchievement = achievementsList.find {
                    it.id == achievement.achievementId
                } ?: Achievement.Empty()
                lastCompletionDate = completionTimeMillis
            }
        }
        return lastCompletedAchievement
    }
}