package com.tetsoft.typego.core.domain

import java.io.Serializable

data class TimeMode(
    val timeInSeconds: Int
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (other is TimeMode) {
            return timeInSeconds == other.timeInSeconds
        }
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}