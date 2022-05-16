package com.tetsoft.typego.data.achievement.requirement

import java.util.ArrayList

object RequirementFactory {
    fun wpmIsMoreThan(moreOrEquals: Int): ArrayList<Requirement> {
        val requirement = Requirement(
            Requirement.AchievementSection.WPM,
            Requirement.CompareType.MORE_OR_EQUALS,
            moreOrEquals
        )
        val requirements = ArrayList<Requirement>()
        requirements.add(requirement)
        return requirements
    }

    // TODO: make it a parameter
    private const val MINIMAL_WPM = 30
    fun timeModeNoMistakes(timeInSeconds: Int): ArrayList<Requirement> {
        val requirements = ArrayList<Requirement>()
        requirements.add(
            Requirement(
                Requirement.AchievementSection.TIME_MODE,
                Requirement.CompareType.EQUALS,
                timeInSeconds
            )
        )
        requirements.add(
            Requirement(
                Requirement.AchievementSection.MISTAKES,
                Requirement.CompareType.EQUALS,
                0
            )
        )
        requirements.add(
            Requirement(
                Requirement.AchievementSection.WPM,
                Requirement.CompareType.MORE_OR_EQUALS,
                MINIMAL_WPM
            )
        )
        return requirements
    }

    fun passedTestsAmount(amount: Int): ArrayList<Requirement> {
        val requirement = Requirement(
            Requirement.AchievementSection.PASSED_TESTS_AMOUNT,
            Requirement.CompareType.MORE_OR_EQUALS,
            amount
        )
        val requirements = ArrayList<Requirement>()
        requirements.add(requirement)
        return requirements
    }

    fun differentLanguagesAmount(amount: Int): ArrayList<Requirement> {
        val requirements = ArrayList<Requirement>()
        requirements.add(
            Requirement(
                Requirement.AchievementSection.DIFFERENT_LANGUAGES_COUNT,
                Requirement.CompareType.MORE_OR_EQUALS,
                amount
            )
        )
        return requirements
    }
}