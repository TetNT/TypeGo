package com.tetsoft.typego.data.textsource

import com.tetsoft.typego.game.data.AssetReader
import com.tetsoft.typego.game.data.ShuffledTextFromAsset
import org.junit.Assert.*

import org.junit.Test

class ShuffledTextFromAssetTest {

    @Test
    fun getString_amount0_emptyList() {
        val amount = 0
        val shuffledTextFromAsset = ShuffledTextFromAsset(MockAssetReader(), "", amount)
        assertTrue(shuffledTextFromAsset.getString().isEmpty())
    }

    @Test
    fun getString_amount50_notEmptyList() {
        val amount = 50
        val shuffledTextFromAsset = ShuffledTextFromAsset(MockAssetReader(), "", amount)
        assertTrue(shuffledTextFromAsset.getString().isNotEmpty())
    }

    private class MockAssetReader : AssetReader {
        override fun read(path: String): String {
            val words = ArrayList<String>()
            for (i in 1..200) {
                words.add("word$i")
            }
            return words.joinToString("\r\n")
        }

    }
}