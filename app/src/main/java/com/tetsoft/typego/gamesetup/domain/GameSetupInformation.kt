package com.tetsoft.typego.gamesetup.domain

import com.tetsoft.typego.core.domain.GameSettings

interface GameSetupInformation {
    fun getGameSettings() : GameSettings
}