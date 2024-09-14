package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.achievements.data.Achievement
import com.tetsoft.typego.achievements.data.AchievementsCompletionPair
import com.tetsoft.typego.achievements.data.AchievementsProgressList
import com.tetsoft.typego.statistics.data.calculation.LastCompletedAchievementCalculation
import org.junit.Assert.assertEquals
import org.junit.Test

class LastCompletedAchievementCalculationTest {

    @Test
    fun provide_completedAchievement_equalsAchievement() {
        val completedAchievements = AchievementsProgressList()
        completedAchievements.add(AchievementsCompletionPair(1, 100L))
        completedAchievements.add(AchievementsCompletionPair(2, 500L))
        completedAchievements.add(AchievementsCompletionPair(3, 300L))
        val achievements = listOf<Achievement>(
            MockAchievement(1),
            MockAchievement(2),
            MockAchievement(3),
            MockAchievement(4)
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
        val achievements = listOf<Achievement>(
            MockAchievement(1),
            MockAchievement(2),
            MockAchievement(3),
            MockAchievement(4)
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
        val achievements = listOf<Achievement>(
            MockAchievement(1),
            MockAchievement(2),
            MockAchievement(3),
            MockAchievement(4)
        )
        val calculation = LastCompletedAchievementCalculation(completedAchievements, achievements)
        assertEquals(calculation.provide(), Achievement.Empty())
    }

    private class MockAchievement(private val id: Int) :
        Achievement.Empty() {
        override fun getId(): Int {
            return id
        }
    }
}