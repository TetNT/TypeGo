package com.tetsoft.typego.ui.fragment

import com.tetsoft.typego.data.game.GameSettings

interface GameSetupInformation {
    fun getGameSettings() : GameSettings
}