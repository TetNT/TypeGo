package com.tetsoft.typego.core.data

import com.tetsoft.typego.core.domain.CharacterStatus
import com.tetsoft.typego.core.utils.extensions.equalsTo

data class Word(
    val inputtedText: String,
    val originalText: String,
    val ignoreCase: Boolean
) {
    val characterStatuses: ArrayList<CharacterStatus>
    init {
        val statuses = ArrayList<CharacterStatus>()
        for (i in originalText.indices) {
            if (i >= inputtedText.length) break
            if (inputtedText[i].equalsTo(originalText[i], ignoreCase)) {
                statuses.add(CharacterStatus.CORRECT)
            }
            else {
                statuses.add(CharacterStatus.WRONG)
            }
        }
        if (inputtedText.length > originalText.length) {
            val excessiveLength = inputtedText.length - originalText.length
            for (i in 0 until excessiveLength) {
                statuses.add(CharacterStatus.WRONG)
            }
        }
        else if (originalText.length > inputtedText.length) {
            val excessiveLength = originalText.length - inputtedText.length
            for (i in 0 until excessiveLength) {
                statuses.add(CharacterStatus.NOT_FILLED)
            }
        }
        characterStatuses = statuses
    }

    fun isCorrect(): Boolean {
        return !characterStatuses.contains(CharacterStatus.WRONG) &&
                !characterStatuses.contains(CharacterStatus.NOT_FILLED)
    }
}