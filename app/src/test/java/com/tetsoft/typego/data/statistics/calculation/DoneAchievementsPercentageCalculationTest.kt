package com.tetsoft.typego.data.statistics.calculation

import com.tetsoft.typego.data.achievement.completion.AchievementsCompletionPair
import org.junit.Assert.*

import org.junit.Test

class DoneAchievementsPercentageCalculationTest {

    @Test
    fun provide_achievementsDone10OutOf20_equals50() {
        val completedAchievements = listOf(
            AchievementsCompletionPair(1, 13949539L),
            AchievementsCompletionPair(2, 15959398L),
            AchievementsCompletionPair(3, 13958382L),
            AchievementsCompletionPair(4, 14919258L),
            AchievementsCompletionPair(5, 13959195L),
            AchievementsCompletionPair(6, 13951959L),
            AchievementsCompletionPair(7, 14959488L),
            AchievementsCompletionPair(8, 14959488L),
            AchievementsCompletionPair(9, 14959488L),
            AchievementsCompletionPair(10, 14955558L),
        )
        val achievementsCount = 20
        val calcValue = DoneAchievementsPercentageCalculation(
            completedAchievements, achievementsCount
        ).provide()
        assertEquals(calcValue, 50)
    }

    @Test
    fun provide_achievementsDone3OutOf20_equals15() {
        val completedAchievements = listOf(
            AchievementsCompletionPair(1, 13949539L),
            AchievementsCompletionPair(2, 15959398L),
            AchievementsCompletionPair(3, 13958382L),
        )
        val achievementsCount = 20
        val calcValue = DoneAchievementsPercentageCalculation(
            completedAchievements, achievementsCount
        ).provide()
        assertEquals(calcValue, 15)
    }

    @Test
    fun provide_achievementsDone0OutOf20_equals0() {
        val completedAchievements = emptyList<AchievementsCompletionPair>()
        val achievementsCount = 20
        val calcValue = DoneAchievementsPercentageCalculation(
            completedAchievements, achievementsCount
        ).provide()
        assertEquals(calcValue, 0)
    }

    @Test
    fun provide_achievementsDone3OutOf0_equals0() {
        val completedAchievements = listOf(
            AchievementsCompletionPair(1, 13949539L),
            AchievementsCompletionPair(2, 15959398L),
            AchievementsCompletionPair(3, 13958382L),
        )
        val achievementsCount = 0
        val calcValue = DoneAchievementsPercentageCalculation(
            completedAchievements, achievementsCount
        ).provide()
        assertEquals(calcValue, 0)
    }
}