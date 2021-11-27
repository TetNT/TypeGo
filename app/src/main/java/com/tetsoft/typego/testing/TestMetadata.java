package com.tetsoft.typego.testing;

import com.tetsoft.typego.utils.DictionaryType;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.ScreenOrientation;
import com.tetsoft.typego.utils.TimeMode;

import java.io.Serializable;

/**
 * Base class that describes all test data available.
 */
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
