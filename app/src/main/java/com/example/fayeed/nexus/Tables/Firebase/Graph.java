package com.example.fayeed.nexus.Tables.Firebase;

import android.text.format.Time;

import com.google.firebase.database.ServerValue;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class Graph {
    long date;
    int dailyActiveUsers;
    int dailyBhhoooUsers;
    int dailyNewsUsers;
    int dailyDiscussionUsers;
    int dailyChatUsers;
    int dailyProfileUsers;
    int dailyBhhhoooPosted;
    int dailyNewsposted;
    int dailyDiscussionPosted;
    int dailyProfileSearched;
    int dailyBhhhoooCommentsPosted;
    int dailyDiscussionCommentsPosted;

    public Graph(){
    }

    public Graph(int dailyActiveUsers, int dailyBhhoooUsers, int dailyNewsUsers, int dailyDiscussionUsers, int dailyChatUsers
                , int dailyProfileUsers, int dailyBhhhoooPosted, int dailyNewsposted, int dailyDiscussionPosted
                , int dailyProfileSearched, int dailyBhhhoooCommentsPosted, int dailyDiscussionCommentsPosted
                , long date){
        this.dailyActiveUsers = dailyActiveUsers;
        this.dailyBhhoooUsers = dailyBhhoooUsers;
        this.dailyNewsUsers = dailyNewsUsers;
        this.dailyDiscussionUsers = dailyDiscussionUsers;
        this.dailyChatUsers = dailyChatUsers;
        this.dailyProfileUsers = dailyProfileUsers;
        this.dailyBhhhoooPosted = dailyBhhhoooPosted;
        this.dailyNewsposted = dailyNewsposted;
        this.dailyDiscussionPosted = dailyDiscussionPosted;
        this.dailyProfileSearched = dailyProfileSearched;
        this.dailyBhhhoooCommentsPosted = dailyBhhhoooCommentsPosted;
        this.dailyDiscussionCommentsPosted = dailyDiscussionCommentsPosted;
        this.date = date;
        /*this.date = new HashMap<>();
        this.date.put("date", ServerValue.TIMESTAMP);*/
        //this.date = timestampNow;
    }

    public void setDailyActiveUsers(int dailyActiveUsers) {
        this.dailyActiveUsers = dailyActiveUsers;
    }
    public int getDailyActiveUsers() {
        return dailyActiveUsers;
    }

    public void setDailyBhhhoooCommentsPosted(int dailyBhhhoooCommentsPosted) {
        this.dailyBhhhoooCommentsPosted = dailyBhhhoooCommentsPosted;
    }
    public int getDailyBhhhoooCommentsPosted() {
        return dailyBhhhoooCommentsPosted;
    }

    public void setDailyBhhhoooPosted(int dailyBhhhoooPosted) {
        this.dailyBhhhoooPosted = dailyBhhhoooPosted;
    }
    public int getDailyBhhhoooPosted() {
        return dailyBhhhoooPosted;
    }

    public void setDailyBhhoooUsers(int dailyBhhoooUsers) {
        this.dailyBhhoooUsers = dailyBhhoooUsers;
    }
    public int getDailyBhhoooUsers() {
        return dailyBhhoooUsers;
    }

    public void setDailyChatUsers(int dailyChatUsers) {
        this.dailyChatUsers = dailyChatUsers;
    }
    public int getDailyChatUsers() {
        return dailyChatUsers;
    }

    public void setDailyDiscussionCommentsPosted(int dailyDiscussionCommentsPosted) {
        this.dailyDiscussionCommentsPosted = dailyDiscussionCommentsPosted;
    }
    public int getDailyDiscussionCommentsPosted() {
        return dailyDiscussionCommentsPosted;
    }

    public void setDailyDiscussionPosted(int dailyDiscussionPosted) {
        this.dailyDiscussionPosted = dailyDiscussionPosted;
    }
    public int getDailyDiscussionPosted() {
        return dailyDiscussionPosted;
    }

    public void setDailyDiscussionUsers(int dailyDiscussionUsers) {
        this.dailyDiscussionUsers = dailyDiscussionUsers;
    }
    public int getDailyDiscussionUsers() {
        return dailyDiscussionUsers;
    }

    public void setDailyNewsposted(int dailyNewsposted) {
        this.dailyNewsposted = dailyNewsposted;
    }
    public int getDailyNewsposted() {
        return dailyNewsposted;
    }

    public void setDailyNewsUsers(int dailyNewsUsers) {
        this.dailyNewsUsers = dailyNewsUsers;
    }
    public int getDailyNewsUsers() {
        return dailyNewsUsers;
    }

    public void setDailyProfileSearched(int dailyProfileSearched) {
        this.dailyProfileSearched = dailyProfileSearched;
    }
    public int getDailyProfileSearched() {
        return dailyProfileSearched;
    }

    public void setDailyProfileUsers(int dailyProfileUsers) {
        this.dailyProfileUsers = dailyProfileUsers;
    }
    public int getDailyProfileUsers() {
        return dailyProfileUsers;
    }
}
