package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.achievements.data.Achievement
import com.tetsoft.typego.achievements.domain.AchievementsCompletionPair
import com.tetsoft.typego.achievements.data.CompletedAchievementsList
import org.junit.Assert.assertEquals
import org.junit.Test

class LastCompletedAchievementCalculationTest {

    @Test
    fun provide_completedAchievement_equalsAchievement() {
        val completedAchievements = CompletedAchievementsList()
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
        val completedAchievements = CompletedAchievementsList()
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
        val completedAchievements = CompletedAchievementsList()
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