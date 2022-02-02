package com.tetsoft.typego.data.achievement

import com.tetsoft.typego.testing.TypingResult
import com.tetsoft.typego.data.LanguageList
import com.tetsoft.typego.data.account.User
import com.tetsoft.typego.testing.ResultListUtils
import java.util.ArrayList

class Requirement(
    private val achievementSection: AchievementSection,
    private val compareType: CompareType,
    val requiredAmount: Int
) {

    enum class CompareType {
        MORE_OR_EQUALS, LESS_OR_EQUALS, EQUALS
    }

    enum class AchievementSection {
        WPM, PASSED_TESTS_AMOUNT, MISTAKES, TIME_MODE, MISTAKES_IN_A_ROW, DIFFERENT_LANGUAGES_COUNT
    }

    private fun getComparingResultBySection(user: User, result: TypingResult): Int {
        return when (achievementSection) {
            AchievementSection.WPM -> result.WPM.toInt()
            AchievementSection.MISTAKES -> result.totalWords - result.correctWords
            AchievementSection.MISTAKES_IN_A_ROW -> getMistakesAmountInARow(user)
            AchievementSection.TIME_MODE -> result.timeInSeconds
            AchievementSection.PASSED_TESTS_AMOUNT -> user.resultList.size
            AchievementSection.DIFFERENT_LANGUAGES_COUNT -> countUniqueLanguageEntries(user.resultList)
        }
    }

    private fun getMistakesAmountInARow(user: User?): Int {
        val IN_A_ROW = 5
        if (user == null) return -1
        val results = user.resultList
        var mistakesAmount = 0
        if (results.size < IN_A_ROW) return -1
        for (i in 0 until IN_A_ROW) {
            mistakesAmount += results[i].incorrectWords
        }
        return mistakesAmount
    }

    fun getCurrentProgress(user: User): Int {
        return when (achievementSection) {
            AchievementSection.WPM -> user.bestResult
            AchievementSection.PASSED_TESTS_AMOUNT -> user.resultList.size
            else -> 0
        }
    }

    // returns the amount of different languages in a results list
    private fun countUniqueLanguageEntries(results: ArrayList<TypingResult?>?): Int {
        var entries = 0
        for (language in LanguageList().getList()) {
            if (ResultListUtils.getResultsByLanguage(results, language).isNotEmpty()) entries++
        }
        return entries
    }

    fun isMatching(user: User, result: TypingResult): Boolean {
        val comparingResult = getComparingResultBySection(user, result)
        val matching = when (compareType) {
            CompareType.EQUALS -> comparingResult == requiredAmount
            CompareType.LESS_OR_EQUALS -> comparingResult <= requiredAmount
            CompareType.MORE_OR_EQUALS -> comparingResult >= requiredAmount
        }
        return matching
    }
}