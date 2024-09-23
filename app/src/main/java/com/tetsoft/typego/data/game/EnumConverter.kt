package com.tetsoft.typego.data.game

import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import java.lang.IllegalArgumentException

interface EnumConverter<T> {
    fun convertToString(value: T): String

    fun convertToEnum(string: String): T

}

class DictionaryTypeConverter : EnumConverter<DictionaryType> {

    override fun convertToString(value: DictionaryType): String {
        return when (value) {
            DictionaryType.BASIC -> BASIC_DICTIONARY
            DictionaryType.ENHANCED -> ENHANCED_DICTIONARY
        }
    }

    override fun convertToEnum(string: String): DictionaryType {
        return when (string.lowercase()) {
            BASIC_DICTIONARY -> DictionaryType.BASIC
            ENHANCED_DICTIONARY -> DictionaryType.ENHANCED
            else -> throw IllegalArgumentException("The string $string didn't match.")
        }
    }

    companion object {
        const val BASIC_DICTIONARY = "basic"
        const val ENHANCED_DICTIONARY = "enhanced"
    }
}

class ScreenOrientationConverter : EnumConverter<ScreenOrientation> {

    override fun convertToString(value: ScreenOrientation): String {
        return when (value) {
            ScreenOrientation.PORTRAIT -> PORTRAIT_ORIENTATION
            ScreenOrientation.LANDSCAPE -> LANDSCAPE_ORIENTATION
        }
    }

    override fun convertToEnum(string: String): ScreenOrientation {
        return when (string.lowercase()) {
            PORTRAIT_ORIENTATION -> ScreenOrientation.PORTRAIT
            LANDSCAPE_ORIENTATION -> ScreenOrientation.LANDSCAPE
            else -> throw IllegalArgumentException("The string $string didn't match.")
        }
    }

    companion object {
        const val PORTRAIT_ORIENTATION = "portrait"
        const val LANDSCAPE_ORIENTATION = "landscape"
    }
}