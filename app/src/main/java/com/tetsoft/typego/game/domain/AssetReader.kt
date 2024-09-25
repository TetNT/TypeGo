package com.tetsoft.typego.game.domain

interface AssetReader {
    fun read(path: String) : String
}

