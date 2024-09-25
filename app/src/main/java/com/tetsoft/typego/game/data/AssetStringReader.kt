package com.tetsoft.typego.game.data

import android.content.res.AssetManager
import com.tetsoft.typego.game.domain.AssetReader
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class AssetStringReader(private val assetManager: AssetManager) : AssetReader {
    override fun read(path: String): String {
        return try {
            val inputStream = assetManager.open(path)
            val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
            reader.readText()
        } catch (e: IOException) {
            ""
        }
    }
}