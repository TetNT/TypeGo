package com.tetsoft.typego.data.textsource

import org.junit.Assert.*
import org.junit.Test

class TextSourceFactoryFromAssetTest {
    @Test
    fun create_seedNotEquals0_returnedShuffledSeedFixedTextFromAsset() {
        val seed = "Seed"
        val textSource : TextSource = TextSource.Factory.FromAsset(MockAssetReader(), "", 0, seed).create()
        assertTrue(textSource is ShuffledSeedFixedTextFromAsset)
    }

    @Test
    fun create_seedEquals0_returnedShuffledTextFromAsset() {
        val seed = ""
        val textSource : TextSource = TextSource.Factory.FromAsset(MockAssetReader(), "", 0, seed).create()
        assertTrue(textSource is ShuffledTextFromAsset)
    }

    private class MockAssetReader : AssetReader {
        override fun read(path: String): String {
            return ""
        }
    }
}