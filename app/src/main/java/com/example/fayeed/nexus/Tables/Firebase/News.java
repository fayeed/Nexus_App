package com.example.fayeed.nexus.Tables.Firebase;

import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class News {
    public String topic;
    public String detail;
    public String user;
    public String timestamp;
    public String id;
    public String priority;
    public boolean targetRoleL;
    public boolean targetRoleM;
    public boolean targetRoleH;
    public int view;
    public String key;

    public News(){

    }

    public News(String topic, String detail, String user, String id, String priority, boolean targetRoleL, boolean targetRoleM,
                boolean targetRoleH, String key){
        this.topic = topic;
        this.detail = detail;
        this.user = user;
        this.id = id;
        this.priority = priority;
        this.timestamp = DateFormat.getDateTimeInstance().format(new Date());
        this.targetRoleL = targetRoleL;
        this.targetRoleM = targetRoleM;
        this.targetRoleH = targetRoleH;
        this.view = 0;
        this.key = key;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getKey() {
        return key;
    }

    public String getTopic(){
        return topic;
    }

    public String getDetail(){
        return detail;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isTargetRoleM() {
        return targetRoleM;
    }

    public boolean isTargetRoleL() {
        return targetRoleL;
    }

    public boolean isTargetRoleH() {
        return targetRoleH;
    }
}
