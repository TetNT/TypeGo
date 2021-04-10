package com.example.typego;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String userName;
    private String password;
    private int lastResult;
    private int bestResult;
    private double averageWPM;
    private String preferredLanguage;

    public User(String id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public String getId() {
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

    // Locale.getDefault().getDisplayLanguage();
}
