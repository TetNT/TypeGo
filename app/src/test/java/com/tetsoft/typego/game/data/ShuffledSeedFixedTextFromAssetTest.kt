package com.tetsoft.typego.game.data

import com.tetsoft.typego.game.domain.AssetReader
import org.junit.Assert.*

import org.junit.Test

/**
 * This JUnit tests the consistency of the Random class with a given seed set.
 * It also checks that when we shuffle different amount of items, the order remains the same.
 */
class ShuffledSeedFixedTextFromAssetTest {

    @Test
    fun getString_amount10_outputTheSameOrder() {
        val amount = 10
        val shuffledSeedFixedTextFromAsset = ShuffledSeedFixedTextFromAsset(MockAssetReader(), "", amount, SEED)
        val expected = "word101 word17 word103 word189 word31 word30 word108 word157 word14 word93"
        assertEquals(shuffledSeedFixedTextFromAsset.getString(), expected)
    }

    @Test
    fun getString_amount50_outputIsLongerButWithSameOrder() {
        val amount = 30
        val shuffledSeedFixedTextFromAsset = ShuffledSeedFixedTextFromAsset(MockAssetReader(), "", amount, SEED)
        val expected = "word101 word17 word103 word189 word31 word30 word108 word157 word14 word93"
        assertTrue(shuffledSeedFixedTextFromAsset.getString().startsWith(expected))
        assertNotEquals(shuffledSeedFixedTextFromAsset.getString(), expected)
    }

    @Test
    fun getString_amountMoreThanListSize_outputIsLongerButWithSameOrder() {
        val amount = 1030
        val shuffledSeedFixedTextFromAsset = ShuffledSeedFixedTextFromAsset(MockAssetReader(), "", amount, SEED)
        val expected = "word101 word17 word103 word189 word31 word30 word108 word157 word14 word93"
        assertTrue(shuffledSeedFixedTextFromAsset.getString().startsWith(expected))
        assertNotEquals(shuffledSeedFixedTextFromAsset.getString(), expected)
    }

    companion object {
        const val SEED = 50
    }

    private class MockAssetReader : AssetReader {
        override fun read(path: String): String {
            val sb = StringBuilder()
            for (i in 1..200) {
                sb.append("word${i},")
            }
            return sb.toString().replace(",", "\r\n").trim()
        }

    }
}