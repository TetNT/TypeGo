package com.tetsoft.typego.testing;

import com.tetsoft.typego.utils.Dictionary;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.ScreenOrientation;
import com.tetsoft.typego.utils.TimeMode;


public class TestMetadata {
    private final Language language;
    private final TimeMode timeMode;
    private final Dictionary dictionary;
    private final boolean suggestionsActivated;
    private final ScreenOrientation screenOrientation;
    private final boolean fromMainMenu;

    public static final int DEFAULT_AMOUNT_OF_SECONDS = 60;
    public static final int DEFAULT_DICTIONARY_TYPE_INDEX = 0;
    public static final boolean DEFAULT_SUGGESTIONS_IS_ON = true;
    public static final int DEFAULT_SCREEN_ORIENTATION = 0;

    public TestMetadata(Language language,
                        TimeMode timeMode,
                        Dictionary dictionary,
                        boolean suggestionsActivated,
                        ScreenOrientation screenOrientation,
                        boolean fromMainMenu) {
        this.language = language;
        this.timeMode = timeMode;
        this.dictionary = dictionary;
        this.suggestionsActivated = suggestionsActivated;
        this.screenOrientation = screenOrientation;
        this.fromMainMenu = fromMainMenu;
    }

    public Language getLanguage() {
        return language;
    }

    public TimeMode getTimeMode() {
        return timeMode;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public boolean isSuggestionsActivated() {
        return suggestionsActivated;
    }

    public ScreenOrientation getScreenOrientation() {
        return screenOrientation;
    }

    public boolean isFromMainMenu() {
        return fromMainMenu;
    }

}
