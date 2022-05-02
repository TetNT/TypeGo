package com.tetsoft.typego.testing;

import com.tetsoft.typego.data.DictionaryType;
import com.tetsoft.typego.data.language.Language;
import com.tetsoft.typego.data.ScreenOrientation;
import com.tetsoft.typego.data.timemode.TimeMode;

import java.io.Serializable;

/**
 * Base class that describes all test data available.
 */
@Deprecated
public abstract class TestMetadata implements Serializable {
    protected Language language;
    protected TimeMode timeMode;
    protected DictionaryType dictionaryType;
    protected boolean suggestionsActivated;
    protected ScreenOrientation screenOrientation;

    public TestMetadata(Language language,
                        TimeMode timeMode,
                        DictionaryType dictionary,
                        boolean suggestionsActivated,
                        ScreenOrientation screenOrientation) {
        this.language = language;
        this.timeMode = timeMode;
        this.dictionaryType = dictionary;
        this.suggestionsActivated = suggestionsActivated;
        this.screenOrientation = screenOrientation;
    }


    protected TestMetadata() {
    }

    abstract public Language getLanguage();

    abstract public TimeMode getTimeMode();

    abstract public DictionaryType getDictionaryType();

    abstract public boolean isSuggestionsActivated();

    abstract public ScreenOrientation getScreenOrientation();
}
