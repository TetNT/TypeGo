package com.tetsoft.typego.data.account;

import com.google.gson.Gson;
import com.tetsoft.typego.storage.UserPreferencesStorage;
import com.tetsoft.typego.testing.TestMetadata;
import com.tetsoft.typego.data.DictionaryType;
import com.tetsoft.typego.data.Language;
import com.tetsoft.typego.data.ScreenOrientation;
import com.tetsoft.typego.utils.StringKeys;
import com.tetsoft.typego.data.TimeMode;

/**
 *  UserPreferences is used to store and obtain last selected test metadata by user.
 */

public class UserPreferences extends TestMetadata {

    private final UserPreferencesStorage storage;

    public UserPreferences(UserPreferencesStorage storage) {
        this.storage = storage;
        language = getLanguage();
        timeMode = getTimeMode();
        dictionaryType = getDictionaryType();
        suggestionsActivated = isSuggestionsActivated();
        screenOrientation = getScreenOrientation();
    }

    public Language getLanguage() {
        return new Gson().fromJson(
                storage.getString(StringKeys.PREFERENCE_LANGUAGE), Language.class);
    }

    public TimeMode getTimeMode() {
        return new Gson().fromJson(
                storage.getString(StringKeys.PREFERENCE_TIMEMODE), TimeMode.class);
    }

    public DictionaryType getDictionaryType() {
        return new Gson().fromJson(
                storage.getString(StringKeys.PREFERENCE_DICTIONARY_TYPE), DictionaryType.class);
    }

    public boolean isSuggestionsActivated() {
        String suggestionsJson = storage.getString(StringKeys.PREFERENCE_SUGGESTIONS);
        if (suggestionsJson.isEmpty()) return true;
        return new Gson().fromJson(suggestionsJson, boolean.class);
    }

    public ScreenOrientation getScreenOrientation() {
        return new Gson().fromJson(
                storage.getString(StringKeys.PREFERENCE_SCREEN_ORIENTATION), ScreenOrientation.class);
    }

    private void setLanguage(Language language) {
        storage.store(StringKeys.PREFERENCE_LANGUAGE, new Gson().toJson(language));
    }

    private void setTimeMode(TimeMode timeMode) {
        storage.store(StringKeys.PREFERENCE_TIMEMODE, new Gson().toJson(timeMode));
    }

    private void setDictionaryType(DictionaryType dictionaryType) {
        storage.store(StringKeys.PREFERENCE_DICTIONARY_TYPE, new Gson().toJson(dictionaryType));
    }

    private void setSuggestionsActivated(boolean suggestionsActivated) {
        storage.store(StringKeys.PREFERENCE_SUGGESTIONS, new Gson().toJson(suggestionsActivated));
    }

    private void setScreenOrientation(ScreenOrientation screenOrientation) {
        storage.store(StringKeys.PREFERENCE_SCREEN_ORIENTATION, new Gson().toJson(screenOrientation));
    }

    public void update(TestMetadata testMetadata) {
        setLanguage(testMetadata.getLanguage());
        setTimeMode(testMetadata.getTimeMode());
        setDictionaryType(testMetadata.getDictionaryType());
        setSuggestionsActivated(testMetadata.isSuggestionsActivated());
        setScreenOrientation(testMetadata.getScreenOrientation());
    }

}
