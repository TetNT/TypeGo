package com.tetsoft.typego.testing;

import com.tetsoft.typego.data.DictionaryType;
import com.tetsoft.typego.data.language.Language;
import com.tetsoft.typego.data.ScreenOrientation;
import com.tetsoft.typego.data.TimeMode;

/**
 *  Handles all test presets and can be passed as an intent extra.
 *  Deprecated due to the lack of flexibility. Use GameOnTime and GameMode instead.
 *  @see com.tetsoft.typego.game.mode.GameOnTime
 */
@Deprecated
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
