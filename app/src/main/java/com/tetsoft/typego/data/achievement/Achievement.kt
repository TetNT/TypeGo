package com.tetsoft.typego.data.achievement

import com.tetsoft.typego.data.account.User
import com.tetsoft.typego.testing.TypingResult
import java.util.*

class Achievement(
    var id: Int,
    val name: String,
    val description: String,
    val assignedImageId: Int,
    val isProgressAttached: Boolean,
    val requirements: ArrayList<Requirement>  // Progress will be shown based of it's very first value.
) {

    var completionDate: Date? = null

    val isCompleted: Boolean
        get() = completionDate != null

    /**
     * Returns true if user completed the achievement.
     */
    fun requirementsAreSatisfied(user: User?, result: TypingResult?): Boolean {
        for (requirement in requirements)  // if any of the requirements isn't complete then return false
            if (!requirement.isMatching(user!!, result!!)) return false
        return true
    }
}