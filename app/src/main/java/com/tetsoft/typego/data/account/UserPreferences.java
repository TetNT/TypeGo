package com.tetsoft.typego.data.account;

import com.google.gson.Gson;
import com.tetsoft.typego.game.mode.GameOnTime;
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
    private final Gson gson = new Gson();

    public UserPreferences(UserPreferencesStorage storage) {
        this.storage = storage;
        language = getLanguage();
        timeMode = getTimeMode();
        dictionaryType = getDictionaryType();
        suggestionsActivated = isSuggestionsActivated();
        screenOrientation = getScreenOrientation();
    }

    public Language getLanguage() {
        return gson.fromJson(
                storage.getString(StringKeys.PREFERENCE_LANGUAGE), Language.class);
    }

    public TimeMode getTimeMode() {
        return gson.fromJson(
                storage.getString(StringKeys.PREFERENCE_TIMEMODE), TimeMode.class);
    }

    public DictionaryType getDictionaryType() {
        return gson.fromJson(
                storage.getString(StringKeys.PREFERENCE_DICTIONARY_TYPE), DictionaryType.class);
    }

    public boolean isSuggestionsActivated() {
        String suggestionsJson = storage.getString(StringKeys.PREFERENCE_SUGGESTIONS);
        if (suggestionsJson.isEmpty()) return true;
        return gson.fromJson(suggestionsJson, boolean.class);
    }

    public ScreenOrientation getScreenOrientation() {
        return gson.fromJson(
                storage.getString(StringKeys.PREFERENCE_SCREEN_ORIENTATION), ScreenOrientation.class);
    }

    private void setLanguage(Language language) {
        storage.store(StringKeys.PREFERENCE_LANGUAGE, gson.toJson(language));
    }

    private void setTimeMode(TimeMode timeMode) {
        storage.store(StringKeys.PREFERENCE_TIMEMODE, gson.toJson(timeMode));
    }

    private void setDictionaryType(DictionaryType dictionaryType) {
        storage.store(StringKeys.PREFERENCE_DICTIONARY_TYPE, gson.toJson(dictionaryType));
    }

    private void setSuggestionsActivated(boolean suggestionsActivated) {
        storage.store(StringKeys.PREFERENCE_SUGGESTIONS, gson.toJson(suggestionsActivated));
    }

    private void setScreenOrientation(ScreenOrientation screenOrientation) {
        storage.store(StringKeys.PREFERENCE_SCREEN_ORIENTATION, gson.toJson(screenOrientation));
    }

    public void update(GameOnTime gameOnTime) {
        setLanguage(gameOnTime.getLanguage());
        setTimeMode(gameOnTime.getTimeMode());
        setDictionaryType(gameOnTime.getDictionaryType());
        setSuggestionsActivated(gameOnTime.getSuggestionsActivated());
        setScreenOrientation(gameOnTime.getScreenOrientation());
    }

}
