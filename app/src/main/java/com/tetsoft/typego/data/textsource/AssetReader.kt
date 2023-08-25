package com.tetsoft.typego.data.textsource

import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

abstract class AssetReader(assetManager: AssetManager) {
    abstract fun read(path: String) : String
}

class AssetStringReader(private val assetManager: AssetManager) : AssetReader(assetManager) {
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