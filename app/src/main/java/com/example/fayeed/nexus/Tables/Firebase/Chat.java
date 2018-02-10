package com.example.fayeed.nexus.Tables.Firebase;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by fayeed on 12/4/2016.
 */

public class Chat {
    private String message;
    private String id;
    private String timestamp;

    public Chat() {
    }

    public Chat(String message, String id) {
        this.message = message;
        this.id = id;
        this.timestamp = DateFormat.getDateTimeInstance().format(new Date());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
