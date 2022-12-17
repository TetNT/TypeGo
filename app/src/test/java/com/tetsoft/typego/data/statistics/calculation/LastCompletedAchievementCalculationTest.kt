package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.achievement.Achievement
import com.tetsoft.typego.data.achievement.AchievementsList
import com.tetsoft.typego.data.achievement.completion.AchievementsCompletionPair
import com.tetsoft.typego.data.achievement.completion.AchievementsProgressList
import org.junit.Assert.*

import org.junit.Test

class LastCompletedAchievementCalculationTest {

    @Test
    fun provide_completedAchievement_equalsAchievement() {
        val completedAchievements = AchievementsProgressList()
        completedAchievements.add(AchievementsCompletionPair(1, 100L))
        completedAchievements.add(AchievementsCompletionPair(2, 500L))
        completedAchievements.add(AchievementsCompletionPair(3, 300L))
        val achievements = listOf(
            Achievement(1, "", "", 0, false, emptyList()),
            Achievement(2, "", "", 0, false, emptyList()),
            Achievement(3, "", "", 0, false, emptyList()),
            Achievement(4, "", "", 0, false, emptyList()),
        )
        val calculation = LastCompletedAchievementCalculation(completedAchievements, achievements)
        assertEquals(calculation.provide(), achievements[1])
    }

    @Test
    fun provide_achievementNotFound_equalsEmptyAchievement() {
        val completedAchievements = AchievementsProgressList()
        completedAchievements.add(AchievementsCompletionPair(1, 100L))
        completedAchievements.add(AchievementsCompletionPair(2, 500L))
        completedAchievements.add(AchievementsCompletionPair(5, 600L))
        val achievements = listOf(
            Achievement(1, "", "", 0, false, emptyList()),
            Achievement(2, "", "", 0, false, emptyList()),
            Achievement(3, "", "", 0, false, emptyList()),
            Achievement(4, "", "", 0, false, emptyList()),
        )
        val calculation = LastCompletedAchievementCalculation(completedAchievements, achievements)
        assertEquals(calculation.provide(), Achievement.Empty())
    }

    @Test
    fun provide_emptyAchievementsList_equalsEmptyAchievement() {
        val completedAchievements = AchievementsProgressList()
        completedAchievements.add(AchievementsCompletionPair(1, 100L))
        completedAchievements.add(AchievementsCompletionPair(2, 500L))
        completedAchievements.add(AchievementsCompletionPair(5, 600L))
        val achievements = emptyList<Achievement>()
        val calculation = LastCompletedAchievementCalculation(completedAchievements, achievements)
        assertEquals(calculation.provide(), Achievement.Empty())
    }

    @Test
    fun provide_emptyCompletionList_equalsEmptyAchievement() {
        val completedAchievements = emptyList<AchievementsCompletionPair>()
        val achievements = listOf(
            Achievement(1, "", "", 0, false, emptyList()),
            Achievement(2, "", "", 0, false, emptyList()),
            Achievement(3, "", "", 0, false, emptyList()),
            Achievement(4, "", "", 0, false, emptyList()),
        )
        val calculation = LastCompletedAchievementCalculation(completedAchievements, achievements)
        assertEquals(calculation.provide(), Achievement.Empty())
    }
}