package com.tetsoft.typego.data.textsource

import android.util.Log
import kotlin.random.Random

class ShuffledTextFromAsset(assetReader: AssetReader, path: String, private val amount: Int) : TextSource {

    private val rawList = assetReader.read(path).split("\r\n")

    override fun getString(): String {
        val result = ArrayList<String>()
        for (i in 0 until amount) {
            result.add(rawList[Random.nextInt(0, rawList.size - 1)])
        }
        return result.shuffled().joinToString(" ")
    }
}