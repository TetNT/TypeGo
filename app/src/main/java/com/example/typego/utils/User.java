package com.example.typego.utils;

import android.content.ContentValues;

import com.google.gson.Gson;

import java.io.Serializable;
import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class User implements Serializable {
    private int id;
    private String userName;
    private String password;
    private int lastResult;
    private int bestResult;
    private double averageWPM;
    private String preferredLanguageId;
    private int preferredTimeMode;
    private int preferredDictionaryType;
    private boolean preferredTextSuggestions;
    private ArrayList<TypingResult> resultList;


    public User() {

    }

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public void addResult(TypingResult result) {
        if (resultList == null) resultList = new ArrayList<>();
        resultList.add(0,result);
    }

    public ArrayList<TypingResult> getResultList() {
        return resultList;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
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

    public double getAverageWPM() {
        return averageWPM;
    }

    public void setAverageWPM(double averageWPM) {
        this.averageWPM = averageWPM;
    }

    public String getPreferredLanguage() {
        return preferredLanguageId;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguageId = preferredLanguage;
    }

    public String getPreferredLanguageId() {
        return preferredLanguageId;
    }

    public void setPreferredLanguageId(String preferredLanguageId) {
        this.preferredLanguageId = preferredLanguageId;
    }

    public int getPreferredTimeMode() {
        return preferredTimeMode;
    }

    public void setPreferredTimeMode(int preferredTimeMode) {
        this.preferredTimeMode = preferredTimeMode;
    }

    public int getPreferredDictionaryType() {
        return preferredDictionaryType;
    }

    public void setPreferredDictionaryType(int preferredDictionaryType) {
        this.preferredDictionaryType = preferredDictionaryType;
    }

    public boolean isPreferredTextSuggestions() {
        return preferredTextSuggestions;
    }

    public void setPreferredTextSuggestions(boolean preferredTextSuggestions) {
        this.preferredTextSuggestions = preferredTextSuggestions;
    }


    public static String serializeToJson(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static User getFromJson(String json) {
        return new Gson().fromJson(json, User.class);
    }
}
