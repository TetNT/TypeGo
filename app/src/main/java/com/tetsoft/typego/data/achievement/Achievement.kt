package com.tetsoft.typego.data.achievement

import android.content.Context
import com.tetsoft.typego.R
import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.requirement.GameRequirement

interface Achievement {

    fun getId() : Int

    fun getName(context: Context) : String

    fun getDescription(context: Context) : String

    fun getAchievementImageId() : Int

    fun withProgressBar() : Boolean

    fun getRequirement() : GameRequirement

    fun isCompleted(result: ClassicGameModesHistoryList) : Boolean

    abstract class Base(private val id: Int) : Achievement {

        override fun equals(other: Any?): Boolean {
            if (other !is Achievement) return false
            return getId() == other.getId()
        }

        override fun getId(): Int {
            return id
        }

        override fun isCompleted(result: ClassicGameModesHistoryList): Boolean {
            return getRequirement().isReached(result)
        }

        override fun hashCode(): Int {
            return id
        }
    }

    abstract class Wpm(id: Int, private val requiredWpm: Int) : Base(id) {

        override fun getDescription(context: Context): String {
            return context.getString(R.string.typewriter_wpm, getRequirement().provideRequiredAmount())
        }

        override fun withProgressBar(): Boolean {
            return true
        }

        override fun getRequirement(): GameRequirement {
            return GameRequirement.WpmRequirement(requiredWpm)
        }
    }

    abstract class CompletedGamesAmount(id: Int, private val requiredCompletedGames : Int, private val stage: String) : Base(id) {

        override fun getName(context: Context): String {
            return context.getString(R.string.big_fan, stage)
        }

        override fun getDescription(context: Context): String {
            return context.getString(R.string.big_fan_desc, requiredCompletedGames)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_bigfan
        }

        override fun withProgressBar(): Boolean {
            return true
        }

        override fun getRequirement(): GameRequirement {
            return GameRequirement.CompletedGamesAmountRequirement(requiredCompletedGames)
        }
    }

    abstract class SharpEye(id: Int, private val requiredTimeInSeconds: Int, private val stage: String) : Base(id) {

        override fun getName(context: Context): String {
            return context.getString(R.string.sharp_eye, stage)
        }

        override fun withProgressBar(): Boolean {
            return false
        }

        override fun getRequirement(): GameRequirement {
            return GameRequirement.SharpEyeRequirement(requiredTimeInSeconds)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_sharpeye
        }
    }

    abstract class NoMistakesInARow(id: Int, private val requiredInARow: Int, private val stage: String) : Base(id) {
        override fun getName(context: Context): String {
            return context.getString(R.string.strike, stage)
        }

        override fun getDescription(context: Context): String {
            return context.getString(R.string.strike_desc, requiredInARow)
        }

        override fun getAchievementImageId(): Int {
            return R.drawable.ic_achievement_strike
        }

        override fun getRequirement(): GameRequirement {
            return GameRequirement.NoMistakesInARowRequirement(requiredInARow)
        }

        override fun withProgressBar(): Boolean {
            return true
        }
    }

    abstract class DifferentLanguages(id: Int, private val requiredUniqueEntries : Int) : Base(id) {
        override fun getDescription(context: Context): String {
            return context.getString(R.string.polyglot_desc, requiredUniqueEntries)
        }

        override fun withProgressBar(): Boolean {
            return true
        }

        override fun getRequirement(): GameRequirement {
            return GameRequirement.DifferentLanguagesRequirement(requiredUniqueEntries)
        }
    }

    open class Empty : Base(-1) {

        override fun getName(context: Context) = ""

        override fun getDescription(context: Context) = ""

        override fun getAchievementImageId() = 0

        override fun withProgressBar() = false

        override fun getRequirement() = GameRequirement.WpmRequirement(0)

        override fun isCompleted(result: ClassicGameModesHistoryList) = false

    }

}