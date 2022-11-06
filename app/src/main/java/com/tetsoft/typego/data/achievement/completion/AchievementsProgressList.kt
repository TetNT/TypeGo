package com.tetsoft.typego.data.achievement.completion

class AchievementsProgressList : ArrayList<AchievementsCompletionPair>() {
    override fun get(index: Int): AchievementsCompletionPair {
        forEach {
            if (it.achievementId == index) {
                return it
            }
        }
        return AchievementsCompletionPair(index, 0L)
    }
}