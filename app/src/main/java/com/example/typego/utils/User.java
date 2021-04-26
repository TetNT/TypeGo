package com.example.typego.utils;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private int id;
    private String userName;
    private String password;
    private int lastResult;
    private int bestResult;
    private double averageWPM;
    private String preferredLanguage;
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
        resultList.add(result);
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
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public static String serializeToJson(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static User getFromJson(String json) {
        return new Gson().fromJson(json, User.class);
    }
}
