package com.tetsoft.typego.statistics.domain

interface Statistics {

    fun provide() : Any

    fun getVisibility() : VisibilityProvider

}