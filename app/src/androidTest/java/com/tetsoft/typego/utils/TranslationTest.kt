package com.tetsoft.typego.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.timemode.TimeMode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.util.*

class TranslationTest {

    private val translationEn : Translation by lazy {
        Translation(getContextWithLocale("en"))
    }
    private val translationRu : Translation by lazy {
        Translation(getContextWithLocale("ru"))
    }

    @Suppress("DEPRECATION")
    private fun getContextWithLocale(localeString: String) : Context {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val cfgEn = context.resources.configuration
        cfgEn.setLocale(Locale(localeString.lowercase()))
        context.resources.updateConfiguration(cfgEn, null)
        return context
    }

    // --------------------------------------------------------------------

    @Test
    fun getAsActiveInactive_BooleanTrue_equalsEnabled() {
        assertEquals(translationEn.getAsEnabledDisabled(true), "Enabled")
        assertEquals(translationRu.getAsEnabledDisabled(true), "Включены")
    }

    @Test
    fun getAsActiveInactive_booleanFalse_equalsDisabled() {
        assertEquals(translationEn.getAsEnabledDisabled(false), "Disabled")
        assertEquals(translationRu.getAsEnabledDisabled(false), "Отключены")
    }


    // --------------------------------------------------------------------

    @Test
    fun get_languageEn_equalsEnglish() {
        assertEquals(translationEn.get(Language("EN")), "English")
        assertEquals(translationRu.get(Language("EN")), "Английский")
    }

    @Test
    fun get_languageFr_equalsFrench() {
        assertEquals(translationEn.get(Language("FR")), "French")
        assertEquals(translationRu.get(Language("FR")), "Французский")
    }

    @Test
    fun get_languageElse_equalsUndefined() {
        assertEquals(translationEn.get(Language("MP")), "Undefined")
        assertEquals(translationRu.get(Language("MP")), "Неопределено")
    }

    @Test
    fun get_languageIt_notEqualsFrench() {
        assertNotEquals(translationEn.get(Language("IT")), "French")
        assertNotEquals(translationRu.get(Language("IT")), "French")
    }

    // --------------------------------------------------------------------

    @Test
    fun get_dictionaryTypeBasic_equalsBasic() {
        assertEquals(translationEn.get(DictionaryType.BASIC), "Basic")
        assertEquals(translationRu.get(DictionaryType.BASIC), "Базовый")
    }

    @Test
    fun get_dictionaryTypeEnhanced_equalsEnhanced() {
        assertEquals(translationEn.get(DictionaryType.ENHANCED), "Enhanced")
        assertEquals(translationRu.get(DictionaryType.ENHANCED), "Расширенный")
    }

    @Test
    fun get_dictionaryTypeBasic_notEqualsEnhanced() {
        assertNotEquals(translationEn.get(DictionaryType.BASIC), "Enhanced")
        assertNotEquals(translationRu.get(DictionaryType.BASIC), "Расширенный")
    }

    // --------------------------------------------------------------------

    @Test
    fun get_screenOrientationPortrait_equalsPortrait() {
        assertEquals(translationEn.get(ScreenOrientation.PORTRAIT), "Portrait")
        assertEquals(translationRu.get(ScreenOrientation.PORTRAIT), "Вертикальное")
    }

    @Test
    fun get_screenOrientationLandscape_equalsLandscape() {
        assertEquals(translationEn.get(ScreenOrientation.LANDSCAPE), "Landscape")
        assertEquals(translationRu.get(ScreenOrientation.LANDSCAPE), "Горизонтальное")
    }

    @Test
    fun get_screenOrientationPortrait_notEqualsLandscape() {
        assertNotEquals(translationEn.get(ScreenOrientation.PORTRAIT), "Landscape")
        assertNotEquals(translationRu.get(ScreenOrientation.PORTRAIT), "Горизонтальное")
    }

    // --------------------------------------------------------------------

    @Test
    fun get_timeMode15Seconds_equals0m15s() {
        assertEquals(translationEn.get(TimeMode(15)), "0 m. 15 s.")
        assertEquals(translationRu.get(TimeMode(15)), "0 м. 15 с.")
    }

    @Test
    fun get_timeMode60Seconds_equals1m() {
        assertEquals(translationEn.get(TimeMode(60)), "1 m.")
        assertEquals(translationRu.get(TimeMode(60)), "1 м.")
    }

    @Test
    fun get_timeMode150Seconds_equals2m30s() {
        assertEquals(translationEn.get(TimeMode(150)), "2 m. 30 s.")
        assertEquals(translationRu.get(TimeMode(150)), "2 м. 30 с.")
    }

    @Test
    fun get_timeMode0Seconds_equals0m() {
        assertEquals(translationEn.get(TimeMode(0)), "0 m.")
        assertEquals(translationRu.get(TimeMode(0)), "0 м.")
    }

    @Test
    fun get_timeMode180Seconds_notEquals0m180s() {
        assertNotEquals(translationEn.get(TimeMode(180)), "0 m. 180 s.")
        assertNotEquals(translationRu.get(TimeMode(180)), "0 м. 180 с.")
    }

    @Test
    fun get_timeMode60Seconds_notEquals1m0s() {
        assertNotEquals(translationEn.get(TimeMode(60)), "1 m. 0 s.")
        assertNotEquals(translationRu.get(TimeMode(60)), "1 м. 0 с.")
    }
}