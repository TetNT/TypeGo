package com.tetsoft.typego.game.data

import com.tetsoft.typego.game.domain.TextSource

class TextSourceUserText(private val userText: String) : TextSource {
    override fun getString(): String {
        return userText
    }
}