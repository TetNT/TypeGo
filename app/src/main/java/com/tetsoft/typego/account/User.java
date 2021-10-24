package com.tetsoft.typego.account;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.tetsoft.typego.achievement.Achievement;
import com.tetsoft.typego.testing.ResultListUtils;
import com.tetsoft.typego.testing.TypingResult;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.StringKeys;
import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class User implements Serializable {
    private int lastResult;
    private int bestResult;
    private Language preferredLanguage;
    private int preferredTimeMode;
    private int preferredDictionaryType;
    private boolean preferredTextSuggestions;
    private int preferredScreenOrientation;
    private final ArrayList<TypingResult> resultList;
    private ArrayList<Achievement> achievements;

    public User() {
        resultList = new ArrayList<>();
        achievements = new ArrayList<>();
    }

    public void initAchievements(Context context) {
        achievements = Achievement.getAchievementList(context);
    }

    public void addResult(@NonNull TypingResult result) {
        resultList.add(0,result);
    }

    @NonNull
    public ArrayList<TypingResult> getResultList() {
        return resultList;
    }

    public int getLastResult() {
        return lastResult;
    }

    public void setLastResult(int lastResult) {
        this.lastResult = lastResult;
    }

    public int getBestResult() {
        return bestResult;
    }

    public void setBestResult(int bestResult) {
        this.bestResult = bestResult;
    }

    public Language getPreferredLanguage() {
        return preferredLanguage;
    }

    public int getPreferredTimeMode() {
        return preferredTimeMode;
    }

    public int getPreferredDictionaryType() {
        return preferredDictionaryType;
    }

    public boolean isPreferredTextSuggestions() {
        return preferredTextSuggestions;
    }

    public int getPreferredScreenOrientation() {
        return preferredScreenOrientation;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public static String serializeToJson(@NonNull User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static User getFromJson(String json) {
        return new Gson().fromJson(json, User.class);
    }

    public void storeData(@NonNull Context context) {
        SharedPreferences spUser = getUserSharedPreferences(context);
        SharedPreferences.Editor editor = spUser.edit();
        editor.putString(StringKeys.PREFERENCES_CURRENT_USER, serializeToJson(this));
        editor.apply();
    }

    public static User getFromStoredData(@NonNull Context context) {
        User user;
        SharedPreferences sharedPreferences = User.getUserSharedPreferences(context);
        user = User.getFromJson(sharedPreferences.getString(StringKeys.PREFERENCES_CURRENT_USER, null));
        return user;
    }

    private static SharedPreferences getUserSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(StringKeys.USER_STORAGE_FILE ,MODE_PRIVATE);
    }

    public int getLastResultByLanguage(Language language) {
        ArrayList<TypingResult> resultsByLanguage = ResultListUtils.getResultsByLanguage(resultList, language);
        if (resultsByLanguage.size()==0) return 0;
        return (int)resultsByLanguage.get(0).getWPM();
    }

    public int getBestResultByLanguage(Language language) {
        ArrayList<TypingResult> resultsByLanguage = ResultListUtils.getResultsByLanguage(resultList, language);
        if (resultsByLanguage.size()==0) return 0;
        return ResultListUtils.getBestResult(resultsByLanguage);
    }

    public void rememberUserPreferences(Context context,
                                        Language language,
                                        int timeMode,
                                        int dictionaryTypeIndex,
                                        boolean textSuggestionsIsOn,
                                        int screenOrientation) {
        preferredLanguage = language;
        preferredTimeMode = timeMode;
        preferredDictionaryType = dictionaryTypeIndex;
        preferredTextSuggestions = textSuggestionsIsOn;
        preferredScreenOrientation = screenOrientation;
        storeData(context);
    }
}