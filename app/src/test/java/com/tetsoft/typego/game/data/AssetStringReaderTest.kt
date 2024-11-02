package com.tetsoft.typego.game.data

import android.content.res.AssetManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.io.ByteArrayInputStream
import java.io.IOException

class AssetStringReaderTest {

    @Test
    fun read_correctPath_fileContentReadProperly() {
        val fileContent = "word1\r\nword2\r\nword3\r\nword4\r\nword5"
        val mockAssetManager = mock(AssetManager::class.java)
        `when`(mockAssetManager.open(anyString())).thenReturn(ByteArrayInputStream(fileContent.toByteArray()))
        val assetReader = AssetStringReader(mockAssetManager)
        val result = assetReader.read("test_dictionary.txt")
        assertTrue(result.isNotEmpty())
        assertEquals(result, fileContent)
    }

    @Test
    fun read_ioException_resultIsEmpty() {
        val mockAssetManager = mock(AssetManager::class.java)
        `when`(mockAssetManager.open(anyString())).thenThrow(IOException::class.java)
        val assetReader = AssetStringReader(mockAssetManager)
        val result = assetReader.read("test_dictionary.txt")
        assertTrue(result.isEmpty())
    }
}