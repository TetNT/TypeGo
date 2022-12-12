package com.tetsoft.typego.data.achievement

import com.tetsoft.typego.data.achievement.requirement.Requirement
import com.tetsoft.typego.game.result.GameResultList

open class Achievement(
    var id: Int,
    val name: String,
    val description: String,
    val assignedImageId: Int,
    val isProgressAttached: Boolean,
    val requirements: List<Requirement> // Progress will be shown based on the very first requirement
) {

    /**
     * @return true if all requirements are complete, false otherwise
     */
    fun requirementsAreComplete(resultList : GameResultList): Boolean {
        for (requirement in requirements)
            if (!requirement.isMatching(resultList)) return false
        return true
    }

    class Empty : Achievement(
        -1,
        "",
        "",
        0,
        false,
        ArrayList()
    )
}