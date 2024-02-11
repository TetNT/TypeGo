package com.tetsoft.typego.data.textsource

interface TextSource {
    fun getString() : String

    interface Factory {
        fun create() : TextSource

        class FromAsset(private val assetReader: AssetReader, private val path: String, private val amount: Int, private val seed: String) : Factory {
            override fun create(): TextSource {
                return if (seed.isNotEmpty()) {
                    ShuffledSeedFixedTextFromAsset(assetReader, path, amount, seed.hashCode())
                }
                else {
                    ShuffledTextFromAsset(assetReader, path, amount)
                }
            }
        }
    }
}