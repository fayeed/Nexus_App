package com.example.fayeed.nexus.Tables.Firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class BHHHOOO {

    public String name;
    public String desc;
    public String timestamp;
    public boolean image;
    public String id;
    public String key;
    public String role;
    int view;
    int comments;

    public BHHHOOO(){

    }

    public BHHHOOO(String name, String desc, boolean image, String key, String id, String role){
        this.name = name;
        this.desc = desc;
        this.timestamp = DateFormat.getDateTimeInstance().format(new Date());
        this.image = image;
        this.key = key;
        this.id = id;
        this.role = role;
        this.view = 0;
        this.comments = 0;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public boolean isImage() {
        return image;
    }

    public String getRole(){
        return role;
    }

    public int getView() {
        return view;
    }

    public int getComments() {
        return comments;
    }

    public void setView(int view) {
        this.view = view;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
