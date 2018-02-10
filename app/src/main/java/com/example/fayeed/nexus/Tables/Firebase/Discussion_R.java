package com.example.fayeed.nexus.Tables.Firebase;


import java.text.DateFormat;
import java.util.Date;

public class Discussion_R {

    String id;
    String name;
    String content;
    String timestamp;
    String imageLocation;

    public Discussion_R(){}

    public Discussion_R(String name, String id, String content, String imageLocation){
        this.name = name;
        this.id = id;
        this.content = content;
        this.imageLocation = imageLocation;
        this.timestamp = DateFormat.getDateTimeInstance().format(new Date());
    }

    public String getId(){return id;}

    public String getName(){return name;}

    public String getContent(){return content;}

    public String getTimestamp(){return timestamp;}

    public String getImageLocation(){return imageLocation;}

}
