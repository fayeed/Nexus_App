package com.example.fayeed.nexus.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.LongSparseArray;

import com.example.fayeed.nexus.Tables.Firebase.Graph;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//**Initialize preference and editor in all methods**

public class GraphEventTrigger {

    DatabaseReference databaseReference;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    String string;
    long date;
    DateFormat formatter;

    public GraphEventTrigger(String institudeId, final Context applicationContext){
        string = institudeId;
        context = applicationContext;
        databaseReference = FirebaseDatabase.getInstance().getReference().child(institudeId).child("Admin")
                .child("Graph").child(DateFormat.getDateInstance().format(new Date()));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                if(graph == null){
                    Date d = new Date();
                    date = d.getTime();
                    Graph graph1 = new Graph(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, date);
                    databaseReference.setValue(graph1);

                    preferences = applicationContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString("Date", DateFormat.getDateInstance().format(new Date()));
                    editor.putBoolean("dailyActiveUser", false);
                    editor.putBoolean("dailyBhhhoooUser", false);
                    editor.putBoolean("dailyNewsUser", false);
                    editor.putBoolean("dailyDiscussionUser", false);
                    editor.putBoolean("dailyChatUser", false);
                    editor.putBoolean("dailyProfileUser", false);
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void loggedIn(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child(string).child("Admin")
                .child("Graph").child(DateFormat.getDateInstance().format(new Date()));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                if(graph != null){
                    preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    if(!preferences.getBoolean("dailyActiveUser", false)){
                        graph.setDailyActiveUsers(graph.getDailyActiveUsers()+1);
                        databaseReference.setValue(graph);
                        editor.putBoolean("dailyActiveUser", true);
                        editor.commit();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void bhhhoooIn(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                if(graph != null) {
                    preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    if (!preferences.getBoolean("dailyBhhhoooUser", false)) {
                        graph.setDailyBhhoooUsers(graph.getDailyBhhoooUsers() + 1);
                        databaseReference.setValue(graph);
                        editor.putBoolean("dailyBhhhoooUser", true);
                        editor.commit();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void newsIn(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                if(graph != null) {
                    preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    if (!preferences.getBoolean("dailyNewsUser", false)) {
                        graph.setDailyNewsUsers(graph.getDailyNewsUsers() + 1);
                        databaseReference.setValue(graph);
                        editor.putBoolean("dailyNewsUser", true);
                        editor.commit();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void discussionIn(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                if(graph != null){
                    preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    if(!preferences.getBoolean("dailyDiscussionUser", false)){
                        graph.setDailyDiscussionUsers(graph.getDailyDiscussionUsers()+1);
                        databaseReference.setValue(graph);
                        editor.putBoolean("dailyDiscussionUser", true);
                        editor.commit();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void profileIn(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                if(graph != null) {
                    preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    if (!preferences.getBoolean("dailyProfileUser", false)) {
                        graph.setDailyProfileUsers(graph.getDailyProfileUsers() + 1);
                        databaseReference.setValue(graph);
                        editor.putBoolean("dailyProfileUser", true);
                        editor.commit();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void chatIn(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                if(graph != null) {
                    preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    if (!preferences.getBoolean("dailyChatUser", false)) {
                        graph.setDailyChatUsers(graph.getDailyChatUsers() + 1);
                        databaseReference.setValue(graph);
                        editor.putBoolean("dailyChatUser", true);
                        editor.commit();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void dBhhhoooPosted(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                graph.setDailyBhhhoooPosted(graph.getDailyBhhhoooPosted()+1);
                databaseReference.setValue(graph);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void dDiscussionPosted(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                graph.setDailyDiscussionPosted(graph.getDailyDiscussionPosted()+1);
                databaseReference.setValue(graph);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void dNewsPosted(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                graph.setDailyNewsposted(graph.getDailyNewsposted()+1);
                databaseReference.setValue(graph);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void dBhhhoooCommentsposted(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                graph.setDailyBhhhoooCommentsPosted(graph.getDailyBhhhoooCommentsPosted()+1);
                databaseReference.setValue(graph);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void dDiscussionCommentsPosted(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                graph.setDailyDiscussionCommentsPosted(graph.getDailyDiscussionCommentsPosted()+1);
                databaseReference.setValue(graph);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void pSearched(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Graph graph = dataSnapshot.getValue(Graph.class);
                graph.setDailyProfileSearched(graph.getDailyProfileSearched()+1);
                databaseReference.setValue(graph);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
