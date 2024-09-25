package com.tetsoft.typego.statistics.data.calculation

import com.tetsoft.typego.achievements.data.CompletedAchievementsList
import com.tetsoft.typego.achievements.domain.AchievementsCompletionPair
import org.junit.Assert.assertEquals
import org.junit.Test

class DoneAchievementsCountCalculationTest {

    @Test
    fun provide_noAchievementsAreDone_equals0() {
        val progressList = CompletedAchievementsList()
        progressList.add(AchievementsCompletionPair(1, 0L))
        progressList.add(AchievementsCompletionPair(2, 0L))
        progressList.add(AchievementsCompletionPair(3, 0L))
        progressList.add(AchievementsCompletionPair(4, 0L))
        assertEquals(0, DoneAchievementsCountCalculation(progressList).provide())
    }

    @Test
    fun provide_OneAchievementIsDone_equals1() {
        val progressList = CompletedAchievementsList()
        progressList.add(AchievementsCompletionPair(1, 0L))
        progressList.add(AchievementsCompletionPair(2, 0L))
        progressList.add(AchievementsCompletionPair(3, 3599346994L))
        progressList.add(AchievementsCompletionPair(4, 0L))
        assertEquals(1, DoneAchievementsCountCalculation(progressList).provide())
    }

    @Test
    fun provide_AllAchievementAreDone_equals4() {
        val progressList = CompletedAchievementsList()
        progressList.add(AchievementsCompletionPair(1, 3599338994L))
        progressList.add(AchievementsCompletionPair(2, 3599312444L))
        progressList.add(AchievementsCompletionPair(3, 3599346994L))
        progressList.add(AchievementsCompletionPair(4, 3599253578L))
        assertEquals(4, DoneAchievementsCountCalculation(progressList).provide())
    }

    @Test
    fun provide_emptyAchievementProgressList_equals0() {
        val progressList = CompletedAchievementsList()
        assertEquals(0, DoneAchievementsCountCalculation(progressList).provide())
    }
}