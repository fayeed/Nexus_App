package com.example.fayeed.nexus.Tables.Firebase;

import java.text.DateFormat;
import java.util.Date;

public class User {
    String name;
    String job;
    String bio;
    String imageLocation;
    String timestamp;
    String email;
    String phoneNo;
    String id;
    String gender;
    String linkedIn;

    public User(){}

    public User(String name, String job, String bio, String imageLocation, String email, String phoneNo,
                String linkedIn, String gender){
        this.name = name;
        this.job = job;
        this.bio = bio;
        this.imageLocation = imageLocation;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.linkedIn = linkedIn;
        this.timestamp = DateFormat.getDateTimeInstance().format(new Date());
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getEmail() {
        return email;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public String getJob() {
        return job;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getGender() {
        return gender;
    }

    public String getLinkedIn() {
        return linkedIn;
    }
}
