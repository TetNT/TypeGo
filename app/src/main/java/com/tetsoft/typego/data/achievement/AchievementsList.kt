package com.tetsoft.typego.data.achievement

import android.content.Context
import com.tetsoft.typego.R
import com.tetsoft.typego.data.requirement.GameRequirement

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
            SharpEyeInSeconds(12, 5, "V"),
            BigFan(13, 10, "I"),
            BigFan(14, 50, "II"),
            BigFan(15, 100, "III"),
            BigFan(16, 200, "IV"),
            BigFan(17, 500, "V")
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
            return R.drawable.ic_achievement_star
        }

        override fun withProgressBar(): Boolean {
            return false
        }

        override fun getRequirement(): GameRequirement {
            return GameRequirement.CompletedGamesAmountRequirement(1)
        }
    }

    private class Beginner(id: Int) : Achievement.Wpm(id, 20) {

        override fun getName(context: Context): String {
            return context.getString(R.string.beginner)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_child
        }
    }

    private class Promising(id: Int) : Achievement.Wpm(id, 40) {
        override fun getName(context: Context): String {
            return context.getString(R.string.promising)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_star
        }
    }

    private class Typewriter(id: Int) : Achievement.Wpm(id, 50) {
        override fun getName(context: Context): String {
            return context.getString(R.string.typewriter)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_keyboard
        }
    }

    private class Bachelor(id: Int) : Achievement.Wpm(id, 60) {
        override fun getName(context: Context): String {
            return context.getString(R.string.type_bachelor)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_bachelor
        }

    }

    private class Mastermind(id: Int) : Achievement.Wpm(id, 70) {
        override fun getName(context: Context): String {
            return context.getString(R.string.mastermind)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_brain
        }
    }

    private class Alien(id: Int) : Achievement.Wpm(id, 80) {
        override fun getName(context: Context): String {
            return context.getString(R.string.alien)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_alien
        }
    }

    private class SharpEyeInSeconds(id: Int, private val timeInSeconds: Int, stage: String) :
        Achievement.SharpEye(id, timeInSeconds, stage) {
        override fun getDescription(context: Context): String {
            return context.getString(R.string.unmistakable_desc_with_seconds, timeInSeconds, 30)
        }
    }

    private class SharpEyeInMinutes(id: Int, private val timeInMinutes: Int, stage: String) :
        Achievement.SharpEye(id, timeInMinutes, stage) {
        override fun getDescription(context: Context): String {
            return context.getString(R.string.unmistakable_desc_with_minutes, timeInMinutes, 30)
        }
    }

    private class BigFan(id: Int, gamesAmount: Int, stage: String) : Achievement.CompletedGamesAmount(id, gamesAmount, stage)
}

