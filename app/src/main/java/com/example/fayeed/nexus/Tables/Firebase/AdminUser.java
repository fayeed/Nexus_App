package com.example.fayeed.nexus.Tables.Firebase;

public class AdminUser {
    public static final String ONLINE = "ONLINE";
    public static final String OFFLINE = "OFFLINE";
    public static final String BHHHOOO = "BHHHOOO";
    public static final String CHAT = "CHAT";
    public static final String PROFILE = "PROFILE";
    public static final String NEWS = "News";
    public static final String DISCUSSION_THREAD = "DISCUSSION THREAD";
    public static final boolean BANNED = true;
    public static final boolean NOTBANNED = false;

    String name;
    String uniqueId;
    String registrationDate;
    String status;
    String currentFeature;
    long totalTimeUsed;
    long report;
    long strikes;
    boolean banned;


    public AdminUser(){
        report = 0;
        strikes = 0;
        banned = false;
    }

    public AdminUser(String name, String uniqueId, String registrationDate, String status, String currentFeature, int report,
                     long totalTimeUsed, long strikes, boolean banned){
        this.name = name;
        this.uniqueId = uniqueId;
        this.registrationDate = registrationDate;
        this.status = status;
        this.currentFeature = currentFeature;
        this.report = report;
        this.totalTimeUsed = totalTimeUsed;
        this.strikes = strikes;
        this.banned = banned;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public long getTotalTimeUsed() {
        return totalTimeUsed;
    }

    public String getCurrentFeature() {
        return currentFeature;
    }

    public long getStrikes() {
        return strikes;
    }

    public boolean isBanned() {
        return banned;
    }

    public long getReport() {
        return report;
    }

    public void setReport(long report) {
        this.report = report;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrentFeature(String currentFeature) {
        this.currentFeature = currentFeature;
    }

    public void setTotalTimeUsed(long totalTimeUsed) {
        this.totalTimeUsed = totalTimeUsed;
    }

    public void setStrikes(long strikes) {
        this.strikes = strikes;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }
}
