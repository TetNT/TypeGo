package com.tetsoft.typego.data.textsource

import kotlin.math.max
import kotlin.random.Random

class ShuffledSeedFixedTextFromAsset(assetReader: AssetReader, path: String, private val amount: Int, private val seed: Int) : TextSource {

    private val rawList = assetReader.read(path).split("\r\n")

    override fun getString(): String {
        val random = Random(seed)
        val outputList = ArrayList<String>()
        val listCombinesNeeded = (amount / rawList.size)
        for (i in 0..max(listCombinesNeeded , 1)) {
            outputList.addAll(rawList.shuffled(random))
        }
        return outputList.subList(0, amount).joinToString(" ")
    }
}