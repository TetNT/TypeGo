package com.tetsoft.typego.achievements.domain

import android.content.Context
import com.tetsoft.typego.R
import com.tetsoft.typego.achievements.data.Achievement
import com.tetsoft.typego.core.data.requirement.CompletedGamesAmountRequirement
import com.tetsoft.typego.core.domain.GameRequirement

object AchievementsList {
    fun get(): List<Achievement> {
        return listOf(
            FirstStep(1),
            Beginner(2),
            Promising(3),
            Typewriter(4),
            Bachelor(5),
            Mastermind(6),
            Alien(7),
            SharpEyeInSeconds(8, 30, "I"),
            SharpEyeInMinutes(9, 1, "II"),
            SharpEyeInMinutes(10, 2, "III"),
            SharpEyeInMinutes(11, 3, "IV"),
            SharpEyeInMinutes(12, 5, "V"),
            BigFan(13, 10, "I"),
            BigFan(14, 50, "II"),
            BigFan(15, 100, "III"),
            BigFan(16, 200, "IV"),
            BigFan(17, 500, "V"),
            BigFan(18, 1000, "VI"),
            Strike(100, 5, "I"),
            Strike(101, 10, "II"),
            Strike(102, 15, "III"),
            DoubleAgent(200),
            Linguist(201),
            MisterUniverse(202)
        )
    }

    private class FirstStep(id: Int) : Achievement.Base(id) {
        override fun getName(context: Context): String {
            return context.getString(R.string.the_first_step)
        }

        override fun getDescription(context: Context): String {
            return context.getString(R.string.the_first_step_desc)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_firststep
        }

        override fun withProgressBar(): Boolean {
            return false
        }

        override fun getRequirement(): GameRequirement {
            return CompletedGamesAmountRequirement(1)
        }
    }

    private class Beginner(id: Int) : Achievement.Wpm(id, 20) {

        override fun getName(context: Context): String {
            return context.getString(R.string.beginner)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_beginner
        }
    }

    private class Promising(id: Int) : Achievement.Wpm(id, 40) {
        override fun getName(context: Context): String {
            return context.getString(R.string.promising)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_promising
        }
    }

    private class Typewriter(id: Int) : Achievement.Wpm(id, 50) {
        override fun getName(context: Context): String {
            return context.getString(R.string.typewriter)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_typewriter
        }
    }

    private class Bachelor(id: Int) : Achievement.Wpm(id, 60) {
        override fun getName(context: Context): String {
            return context.getString(R.string.type_bachelor)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_bachelor
        }

    }

    private class Mastermind(id: Int) : Achievement.Wpm(id, 70) {
        override fun getName(context: Context): String {
            return context.getString(R.string.mastermind)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_mastermind
        }
    }

    private class Alien(id: Int) : Achievement.Wpm(id, 80) {
        override fun getName(context: Context): String {
            return context.getString(R.string.alien)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_alien
        }
    }

    private class SharpEyeInSeconds(id: Int, private val timeInSeconds: Int, stage: String) :
        Achievement.SharpEye(id, timeInSeconds, stage) {
        override fun getDescription(context: Context): String {
            return context.getString(R.string.sharp_eye_desc_with_seconds, timeInSeconds, 30)
        }
    }

    private class SharpEyeInMinutes(id: Int, private val timeInMinutes: Int, stage: String) :
        Achievement.SharpEye(id, timeInMinutes, stage) {
        override fun getDescription(context: Context): String {
            return context.getString(R.string.sharp_eye_desc_with_minutes, timeInMinutes, 30)
        }
    }

    private class BigFan(id: Int, gamesAmount: Int, stage: String) : Achievement.CompletedGamesAmount(id, gamesAmount, stage)

    private class Strike(id: Int, inARow: Int, stage: String) : Achievement.NoMistakesInARow(id, inARow, stage)

    private class DoubleAgent(id: Int) : Achievement.DifferentLanguages(id, 2) {
        override fun getName(context: Context): String {
            return context.getString(R.string.double_agent)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_doubleagent
        }
    }

    private class Linguist(id: Int) : Achievement.DifferentLanguages(id, 4) {
        override fun getName(context: Context): String {
            return context.getString(R.string.linguist)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_linguist
        }
    }

    private class MisterUniverse(id: Int) : Achievement.DifferentLanguages(id, 6) {
        override fun getName(context: Context): String {
            return context.getString(R.string.mister_universe)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_misteruniverse
        }
    }
}

