package com.tetsoft.typego.data.statistics

interface StatisticsProvider {

    fun provide() : String

    fun visibility() : VisibilityProvider
}