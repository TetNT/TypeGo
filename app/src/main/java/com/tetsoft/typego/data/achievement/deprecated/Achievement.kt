package com.tetsoft.typego.data.achievement.deprecated

import com.tetsoft.typego.data.achievement.deprecated.requirement.Requirement
import com.tetsoft.typego.game.GameOnTime
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
    @Deprecated("Use the new GameOnTime list instead")
    fun requirementsAreComplete(resultList : GameResultList): Boolean {
        for (requirement in requirements)
            if (!requirement.isMatching(resultList)) return false
        return true
    }

    // TODO: REFACTOR THIS CODE AND DELETE THE OLD IMPLEMENTATION
    fun requirementsAreComplete(resultList: List<GameOnTime>) : Boolean {
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