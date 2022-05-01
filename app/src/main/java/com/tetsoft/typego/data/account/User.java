package com.tetsoft.typego.data.account;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.tetsoft.typego.data.achievement.Achievement;
import com.tetsoft.typego.testing.TypingResult;
import com.tetsoft.typego.utils.StringKeys;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Deprecated
public class User implements Serializable {
    private int bestResult;
    private final ArrayList<TypingResult> resultList;
    @Deprecated
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

    @NonNull
    public ArrayList<TypingResult> getResultList() {
        return resultList;
    }

    @Deprecated
    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    private static String serializeToJson(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    private static User getFromJson(String json) {
        return new Gson().fromJson(json, User.class);
    }

    public void storeData(Context context) {
        SharedPreferences spUser = getUserSharedPreferences(context);
        SharedPreferences.Editor editor = spUser.edit();
        editor.putString(StringKeys.PREFERENCES_CURRENT_USER, serializeToJson(this));
        editor.apply();
    }

    public static User getFromStoredData(Context context) {
        User user;
        SharedPreferences sharedPreferences = User.getUserSharedPreferences(context);
        user = User.getFromJson(sharedPreferences.getString(StringKeys.PREFERENCES_CURRENT_USER, null));
        return user;
    }

    private static SharedPreferences getUserSharedPreferences(Context context) {
        return context.getSharedPreferences(StringKeys.USER_STORAGE, Context.MODE_PRIVATE);
    }

}
