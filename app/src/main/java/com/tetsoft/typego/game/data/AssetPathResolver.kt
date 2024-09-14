package com.tetsoft.typego.game.data

import com.tetsoft.typego.core.utils.DictionaryTypeConverter
import com.tetsoft.typego.core.domain.DictionaryType

interface AssetPathResolver {
    fun getPath() : String

    class Dictionary(private val dictionaryType: DictionaryType, private val languageCode: String) : AssetPathResolver {
        override fun getPath(): String {
            if (languageCode.isEmpty()) {
                throw IllegalArgumentException("Cannot get the path. The language code was empty.")
            }
            val folderPath = PARENT_FOLDER + DictionaryTypeConverter().convertToString(dictionaryType).lowercase() + "/"
            val fileName = "$languageCode.txt"
            return folderPath + fileName
        }

        companion object {
            const val PARENT_FOLDER = "words/"
        }
    }
}