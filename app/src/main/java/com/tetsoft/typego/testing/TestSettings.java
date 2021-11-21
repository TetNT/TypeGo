package com.tetsoft.typego.testing;

import com.tetsoft.typego.utils.DictionaryType;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.ScreenOrientation;
import com.tetsoft.typego.utils.TimeMode;

/**
 *  Handles all test presets and can be passed as an intent extra,
 *  because its base class implements the Serializable interface.
 */
public class TestSettings extends TestMetadata {
     public TestSettings(Language language,
                         TimeMode timeMode,
                         DictionaryType dictionaryType,
                         boolean suggestionsActivated,
                         ScreenOrientation screenOrientation) {
         super(language, timeMode, dictionaryType, suggestionsActivated, screenOrientation);
     }

    @Override
    public Language getLanguage() {
        return language;
    }

    @Override
    public TimeMode getTimeMode() {
        return timeMode;
    }

    @Override
    public DictionaryType getDictionaryType() {
        return dictionaryType;
    }

    @Override
    public boolean isSuggestionsActivated() {
        return suggestionsActivated;
    }

    @Override
    public ScreenOrientation getScreenOrientation() {
        return screenOrientation;
    }
}
