package com.tetsoft.typego.data.textsource

import kotlin.random.Random

class ShuffledTextFromAsset(assetReader: AssetReader, path: String, private val amount: Int) : TextSource {
    // TODO: make it a dependency. It's not very flexible now.
    private val rawList = assetReader.read(path).split("\r\n")

    override fun getString(): String {
        val result = ArrayList<String>()
        val random = Random.Default
        for (i in 0 until amount) {
            result.add(rawList[random.nextInt(0, rawList.size - 1)])
        }
        return result.shuffled(random).joinToString(" ")
    }
}