package com.tetsoft.typego.statistics.data.calculation


import com.tetsoft.typego.achievements.data.Achievement
import com.tetsoft.typego.achievements.domain.AchievementsCompletionPair
import com.tetsoft.typego.statistics.domain.StatisticsCalculation

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
                    it.getId() == achievement.achievementId
                } ?: Achievement.Empty()
                lastCompletionDate = completionTimeMillis
            }
        }
        return lastCompletedAchievement
    }

}