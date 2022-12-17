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

    override fun equals(other: Any?): Boolean {
        if (other is Achievement) {
            return (other.id == id)
        }
        return false
    }

    /**
     * @return true if all requirements are complete, false otherwise
     */
    fun requirementsAreComplete(resultList : GameResultList): Boolean {
        for (requirement in requirements)
            if (!requirement.isMatching(resultList)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        return result
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