package com.tetsoft.typego.data.achievement

import com.tetsoft.typego.game.result.GameResultList
import java.util.*

class Achievement(
    var id: Int,
    val name: String,
    val description: String,
    val assignedImageId: Int,
    val isProgressAttached: Boolean,
    // Progress will be shown based on it's very first value.
    val requirements: ArrayList<Requirement>
) {
    @Deprecated("Achievement completion is no longer a part of the Achievement class.")
    var completionDate: Date? = null

    @Deprecated("Achievement completion is no longer a part of the Achievement class.")
    val isCompleted: Boolean
        get() = completionDate != null

    // Returns true if user completed the achievement.
    fun requirementsAreComplete(resultList : GameResultList): Boolean {
        for (requirement in requirements)  // if any of the requirements isn't complete then return false
            if (!requirement.isMatching(resultList)) return false
        return true
    }
}