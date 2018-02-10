package com.example.fayeed.nexus.Tables.Firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Chat_Open_Table {
    boolean replied;
    String id;
    String o_id;
    String name;

    public Chat_Open_Table (){

    }

    public Chat_Open_Table(String id, String o_id, String name){
        this.replied = false;
        this.id = id;
        this.o_id = o_id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public boolean isReplied() {
        return replied;
    }

    public String getO_id() {
        return o_id;
    }

    public void setO_id(String o_id) {
        this.o_id = o_id;
    }

    public String getName() {
        return name;
    }
}
