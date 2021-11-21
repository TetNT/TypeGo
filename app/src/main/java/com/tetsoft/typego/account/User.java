package com.tetsoft.typego.account;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.tetsoft.typego.achievement.Achievement;
import com.tetsoft.typego.achievement.AchievementMigration;
import com.tetsoft.typego.testing.ResultListUtils;
import com.tetsoft.typego.testing.TypingResult;
import com.tetsoft.typego.utils.Language;
import com.tetsoft.typego.utils.StringKeys;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class User implements Serializable {
    private int bestResult;
    private final ArrayList<TypingResult> resultList;
    private ArrayList<Achievement> achievements;

    /**
     * This HashMap key is achievement ID and value is completion date.
     * It will be used in the future to handle achievements progress independently,
     * instead of list comparison.
     */
    private HashMap<Integer, Date> achievementsCompletionDateMap;

    public User() {
        resultList = new ArrayList<>();
        achievements = new ArrayList<>();
    }

    public void initAchievements(Context context) {
        ArrayList<Achievement> actualAchievementList = Achievement.getAchievementList(context);
        if (achievements.isEmpty()) {
            achievements = actualAchievementList;
            return;
        }
        AchievementMigration migration = new AchievementMigration(achievements, actualAchievementList);
        achievements = migration.getMergedAchievementList();
    }

    public void addResult(@NonNull TypingResult result) {
        resultList.add(0,result);
    }

    @NonNull
    public ArrayList<TypingResult> getResultList() {
        return resultList;
    }

    public int getBestResult() {
        return bestResult;
    }

    public void setBestResult(int bestResult) {
        this.bestResult = bestResult;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    private static String serializeToJson(@NonNull User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    private static User getFromJson(String json) {
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
        return context.getSharedPreferences(StringKeys.USER_STORAGE, Context.MODE_PRIVATE);
    }

    public int getLastResultByLanguage(@NonNull Language language) {
        ArrayList<TypingResult> resultsByLanguage = ResultListUtils.getResultsByLanguage(resultList, language);
        if (resultsByLanguage.size()==0) return 0;
        return (int)resultsByLanguage.get(0).getWPM();
    }

    public int getBestResultByLanguage(@NonNull Language language) {
        ArrayList<TypingResult> resultsByLanguage = ResultListUtils.getResultsByLanguage(resultList, language);
        if (resultsByLanguage.size()==0) return 0;
        return ResultListUtils.getBestResultWPM(resultsByLanguage);
    }

}
