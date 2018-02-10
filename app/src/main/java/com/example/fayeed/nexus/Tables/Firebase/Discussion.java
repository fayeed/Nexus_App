package com.example.fayeed.nexus.Tables.Firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Discussion {

    String topic;
    String desc;
    String timestamp;
    String imageLocation;
    String creator;
    String key;
    boolean closed;
    int noOfThreads;
    String id;
    int view;

    public Discussion(){}

    public Discussion(String topic, String desc, String creator, String imageLocation, String key, String id){
        this.topic = topic;
        this.desc = desc;
        this.creator = creator;
        this.imageLocation = imageLocation;
        this.timestamp = DateFormat.getDateTimeInstance().format(new Date());
        this.key = key;
        this.closed = false;
        this.noOfThreads = 0;
        this.view = 0;
        this.id = id;
    }

    public String getTopic(){
        return topic;
    }

    public String getDesc(){
        return desc;
    }

    public String getCreator(){
        return creator;
    }

    public String getImageLocation(){
        return  imageLocation;
    }

    public  String getTimestamp(){
        return timestamp;
    }

    public int getNoOfThreads(){
        return noOfThreads;
    }

    public boolean getClosed(){ return closed;}

    public String getKey(){ return key;}

    public String getId(){return id;}

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public void setNoOfThreads(int noOfThreads) {
        this.noOfThreads = noOfThreads;
    }
}
