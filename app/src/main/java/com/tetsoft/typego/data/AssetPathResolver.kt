package com.tetsoft.typego.data

interface AssetPathResolver {
    fun getPath() : String

    class Dictionary(private val dictionaryType: DictionaryType, private val languageCode: String) : AssetPathResolver {
        override fun getPath(): String {
            if (languageCode.isEmpty()) {
                throw IllegalArgumentException("Cannot get the path. The language code was empty.")
            }
            val folderPath = PARENT_FOLDER + dictionaryType.name.lowercase() + "/"
            val fileName = "$languageCode.txt"
            return folderPath + fileName
        }

        companion object {
            const val PARENT_FOLDER = "words/"
        }
    }
}
