package com.tetsoft.typego.data.achievement.requirement

import com.tetsoft.typego.data.history.ClassicGameHistoryDataSelector
import com.tetsoft.typego.data.history.ClassicGameModesHistoryList
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.game.result.GameResultList

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

    private fun getComparingResultBySection(resultList: GameResultList): Int {
        require(!resultList.isEmpty()) { "The result list should not be empty!" }
        val result = resultList[resultList.size - 1]
        return when (achievementSection) {
            AchievementSection.WPM -> resultList.getLastWpm().toInt()
            AchievementSection.MISTAKES -> result!!.wordsWritten - result.correctWords
            AchievementSection.MISTAKES_IN_A_ROW -> getMistakesAmountInARow(resultList)
            AchievementSection.TIME_MODE -> {
                if (result!!.gameMode is GameOnTime) (result.gameMode as GameOnTime).timeMode.timeInSeconds else 0
            }
            AchievementSection.PASSED_TESTS_AMOUNT -> resultList.size
            AchievementSection.DIFFERENT_LANGUAGES_COUNT -> countUniqueLanguageEntries(resultList)
        }
    }

    // finished, needs to be tested
    private fun getComparingResultBySection(resultList: List<com.tetsoft.typego.game.GameOnTime>): Int {
        require(!resultList.isEmpty()) { "The result list should not be empty!" }
        val result = resultList[resultList.size - 1]
        return when (achievementSection) {
            AchievementSection.WPM -> resultList[resultList.size].getWpm().toInt()
            AchievementSection.MISTAKES -> result.getWordsWritten() - result.getCorrectWords()
            AchievementSection.MISTAKES_IN_A_ROW -> 0 // remove this enum option completely
            AchievementSection.TIME_MODE -> result.getTimeSpent()
            AchievementSection.PASSED_TESTS_AMOUNT -> resultList.size
            AchievementSection.DIFFERENT_LANGUAGES_COUNT -> 0
        }
    }

    private fun getMistakesAmountInARow(resultList: GameResultList): Int {
        val IN_A_ROW = 5
        var mistakesAmount = 0
        if (resultList.size < IN_A_ROW) return -1
        for (i in 0 until IN_A_ROW) {
            val result = resultList[i]
            mistakesAmount += result!!.wordsWritten - result.correctWords
        }
        return mistakesAmount
    }

    fun getCurrentProgress(resultList: GameResultList): Int {
        return when (achievementSection) {
            AchievementSection.WPM -> resultList.bestResultWpm
            AchievementSection.PASSED_TESTS_AMOUNT -> resultList.size
            else -> 0
        }
    }

    fun getCurrentProgress(classicGameHistory: ClassicGameModesHistoryList): Int {
        return when (achievementSection) {
            AchievementSection.WPM -> ClassicGameHistoryDataSelector(classicGameHistory).getBestResult().getWpm().toInt()
            AchievementSection.PASSED_TESTS_AMOUNT -> classicGameHistory.size
            else -> 0
        }
    }

    // returns the amount of different languages in a results list
    private fun countUniqueLanguageEntries(resultList: GameResultList): Int {
        var entries = 0
        for (language in LanguageList().getList()) {
            if (!resultList.getResultsByLanguage(language).isEmpty()) entries++
        }
        return entries
    }

    @Deprecated("Use the new GameOnTime list instead")
    fun isMatching(resultList: GameResultList): Boolean {
        val comparingResult = getComparingResultBySection(resultList)
        val matching: Boolean = when (compareType) {
            CompareType.EQUALS -> comparingResult == requiredAmount
            CompareType.LESS_OR_EQUALS -> comparingResult <= requiredAmount
            CompareType.MORE_OR_EQUALS -> comparingResult >= requiredAmount
        }
        return matching
    }
    // TODO: REFACTOR THIS CODE AND DELETE THE OLD IMPLEMENTATION
    fun isMatching(resultList: List<com.tetsoft.typego.game.GameOnTime>): Boolean {
        val comparingResult = getComparingResultBySection(resultList)
        val matching: Boolean = when (compareType) {
            CompareType.EQUALS -> comparingResult == requiredAmount
            CompareType.LESS_OR_EQUALS -> comparingResult <= requiredAmount
            CompareType.MORE_OR_EQUALS -> comparingResult >= requiredAmount
        }
        return matching
    }
}