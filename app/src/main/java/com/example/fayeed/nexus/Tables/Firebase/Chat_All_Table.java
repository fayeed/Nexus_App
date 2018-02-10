package com.example.fayeed.nexus.Tables.Firebase;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Chat_All_Table {
    public String name;
    public String id;
    public String role;

    public Chat_All_Table(){

    }

    public Chat_All_Table(String name, String id, String role){
        this.name = name;
        this.id = id;
        this.role = role;
    }

    public String getName(){
        return name;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
