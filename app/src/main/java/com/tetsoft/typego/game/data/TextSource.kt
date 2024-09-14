package com.tetsoft.typego.game.data

interface TextSource {
    fun getString() : String

    class FromAsset(private val assetReader: AssetReader, private val path: String, private val amount: Int, private val seed: String) :
        TextSource {
        override fun getString(): String {
            return if (seed.isNotEmpty()) {
                ShuffledSeedFixedTextFromAsset(assetReader, path, amount, seed.hashCode()).getString()
            } else {
                ShuffledTextFromAsset(assetReader, path, amount).getString()
            }
        }
    }

    class UserText(private val userText: String) : TextSource {
        override fun getString(): String {
            return userText
        }
    }
}