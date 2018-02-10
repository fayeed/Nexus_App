package com.example.fayeed.nexus.Main;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.fayeed.nexus.R;

import static java.security.AccessController.getContext;

public class Notifications extends AppCompatActivity {

    Switch b1, b2, b3, n1, n2, n3;
    boolean bb1, bb2, bb3, bn1, bn2, bn3;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        bb1 = sharedPref.getBoolean("Bhhhooo_lId", true);
        bb2 = sharedPref.getBoolean("Bhhhooo_mId", true);
        bb3 = sharedPref.getBoolean("Bhhhooo_hId", true);
        bn1 = sharedPref.getBoolean("chat_lId", true);
        bn2 = sharedPref.getBoolean("chat_mId", true);
        bn3 = sharedPref.getBoolean("chat_hId", true);

        b1 = (Switch) findViewById(R.id.b_low);
        b2 = (Switch) findViewById(R.id.b_medium);
        b3 = (Switch) findViewById(R.id.b_high);
        n1 = (Switch) findViewById(R.id.n_low);
        n2 = (Switch) findViewById(R.id.n_medium);
        n3 = (Switch) findViewById(R.id.n_high);

        //bhhhooo
        if(bb1 == true){
            b1.setChecked(true);
        } else {
            b1.setChecked(false);
        }
        if(bb2 == true){
            b2.setChecked(true);
        } else {
            b2.setChecked(false);
        }
        if(bb3 == true){
            b3.setChecked(true);
        } else {
            b3.setChecked(false);
        }

        //chat
        if(bn1 == true){
            n1.setChecked(true);
        } else {
            n1.setChecked(false);
        }
        if(bn2 == true){
            n2.setChecked(true);
        } else {
            n2.setChecked(false);
        }
        if(bn3 == true){
            n3.setChecked(true);
        } else {
            n3.setChecked(false);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //bhhooo
        if(b1.isChecked() == true){
            editor.putBoolean("Bhhhooo_lId", true);
            editor.commit();
        } else {
            editor.putBoolean("Bhhhooo_lId", false);
            editor.commit();
        }
        if(b2.isChecked() == true){
            editor.putBoolean("Bhhhooo_mId", true);
            editor.commit();
        } else {
            editor.putBoolean("Bhhhooo_mId", false);
            editor.commit();
        }
        if(b3.isChecked() == true){
            editor.putBoolean("Bhhhooo_hId", true);
            editor.commit();
        } else {
            editor.putBoolean("Bhhhooo_hId", false);
            editor.commit();
        }

        //chat
        if(n1.isChecked() == true){
            editor.putBoolean("chat_lId", true);
            editor.commit();
        } else {
            editor.putBoolean("chat_lId", false);
            editor.commit();
        }
        if(n2.isChecked() == true){
            editor.putBoolean("chat_mId", true);
            editor.commit();
        } else {
            editor.putBoolean("chat_mId", false);
            editor.commit();
        }
        if(n3.isChecked() == true){
            editor.putBoolean("chat_hId", true);
            editor.commit();
        } else {
            editor.putBoolean("chat_hId", false);
            editor.commit();
        }

        Log.i("LOG", "l " + bn1);
        Log.i("LOG", "l " + bn2);
        Log.i("LOG", "l " + bn3);

    }
}
