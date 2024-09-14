package com.tetsoft.typego.gamesetup.data

import com.tetsoft.typego.core.domain.GameSettings

interface GameSetupInformation {
    fun getGameSettings() : GameSettings
}