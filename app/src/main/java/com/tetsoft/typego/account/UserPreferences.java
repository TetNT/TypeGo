package com.tetsoft.typego.account;

import com.tetsoft.typego.testing.TestMetadata;
import com.tetsoft.typego.utils.Dictionary;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.ScreenOrientation;
import com.tetsoft.typego.utils.TimeMode;

public class UserPreferences implements IStorable {
    private Language preferredLanguage;
    private TimeMode preferredTimeMode;
    private Dictionary preferredDictionary;
    private boolean textSuggestionsActivated;
    private ScreenOrientation preferredScreenOrientation;

    public UserPreferences(Language language,
                           TimeMode timeMode,
                           Dictionary dictionary,
                           boolean activateSuggestions,
                           ScreenOrientation screenOrientation) {
        preferredLanguage = language;
        preferredTimeMode = timeMode;
        preferredDictionary = dictionary;
        textSuggestionsActivated = activateSuggestions;
        preferredScreenOrientation = screenOrientation;
    }

    public UserPreferences(TestMetadata testMetadata) {
        preferredLanguage = testMetadata.getLanguage();
        preferredTimeMode = testMetadata.getTimeMode();
        preferredDictionary = testMetadata.getDictionary();
        preferredScreenOrientation = testMetadata.getScreenOrientation();
        textSuggestionsActivated = testMetadata.isSuggestionsActivated();
    }

    public void store() {

    }

    public void getFromStorage() {

    }
}
