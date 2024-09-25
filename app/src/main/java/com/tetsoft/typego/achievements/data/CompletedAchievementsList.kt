package com.tetsoft.typego.achievements.data

import com.tetsoft.typego.achievements.domain.AchievementsCompletionPair

// TODO: Remove AchievementsCompletionPair and use a map instead
class CompletedAchievementsList : ArrayList<AchievementsCompletionPair>() {
    override fun get(index: Int): AchievementsCompletionPair {
        forEach {
            if (it.achievementId == index) {
                return it
            }
        }
        return AchievementsCompletionPair(index, 0L)
    }
}