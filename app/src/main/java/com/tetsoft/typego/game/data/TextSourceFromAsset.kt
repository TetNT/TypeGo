package com.tetsoft.typego.game.data

import com.tetsoft.typego.game.domain.AssetReader
import com.tetsoft.typego.game.domain.TextSource

class TextSourceFromAsset(private val assetReader: AssetReader, private val path: String, private val amount: Int, private val seed: String) :
    TextSource {
    override fun getString(): String {
        return if (seed.isNotEmpty()) {
            ShuffledSeedFixedTextFromAsset(assetReader, path, amount, seed.hashCode()).getString()
        } else {
            ShuffledTextFromAsset(assetReader, path, amount).getString()
        }
    }
}