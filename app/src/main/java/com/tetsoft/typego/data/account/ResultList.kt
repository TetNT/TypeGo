package com.tetsoft.typego.data.account

import com.tetsoft.typego.data.Language
import com.tetsoft.typego.testing.TestResult
import java.util.*

class ResultList : ArrayList<TestResult>() {

    fun getResultsByLanguage(language: Language): ResultList {
        val selectedResults = ResultList()
        for (result in this) {
            val rowLanguageId = result.language.identifier
            if (rowLanguageId.equals(language.identifier, ignoreCase = true)) {
                selectedResults.add(result)
            }
        }
        return selectedResults
    }

    val bestResult: Int
        get() {
            var currentBest = 0
            for (result in this) {
                if (result.wpm > currentBest) currentBest = result.wpm.toInt()
            }
            return currentBest
        }

    val inDescendingOrder: ResultList
        get() {
            val copy = clone() as ResultList
            Collections.reverse(copy)
            return copy
        }
}