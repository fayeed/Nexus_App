package com.example.fayeed.nexus.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.fayeed.nexus.Main.Login.Login;
import com.example.fayeed.nexus.Main.Profile.Edit_Profile;
import com.example.fayeed.nexus.R;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {

    String institudeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        institudeId = getIntent().getStringExtra("id");

        View editpro = findViewById(R.id.editpro);
        View logout = findViewById(R.id.logout);
        View filter = findViewById(R.id.filter);
        View report = findViewById(R.id.reportp);
        View about = findViewById(R.id.about);
        View tandc = findViewById(R.id.tandc);
        View delete = findViewById(R.id.delete);

        editpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this, Edit_Profile.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent i = new Intent(Settings.this, Login.class);
                startActivity(i);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this, Notifications.class);
                startActivity(i);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tandc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usertable = FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("Admin").child("Usertable").child(Profile.getCurrentProfile().getId());
                DatabaseReference report = FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("Admin").child("Report").child(Profile.getCurrentProfile().getId());
                DatabaseReference bhhoo = FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("BHHHOOO").child("User").child(Profile.getCurrentProfile().getId());
                DatabaseReference chat_list =FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("Chat").child("List").child(Profile.getCurrentProfile().getId());
                DatabaseReference chat_user =FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("Chat").child("User").child(Profile.getCurrentProfile().getId());
                DatabaseReference user_detail =FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("User").child("Detail").child(Profile.getCurrentProfile().getId());
                DatabaseReference user_meta_first =FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("User").child("meta").child(Profile.getCurrentProfile().getFirstName());
                DatabaseReference user_meta_full =FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("User").child("meta").child(Profile.getCurrentProfile().getFirstName() + " " +
                        Profile.getCurrentProfile().getLastName());
                DatabaseReference user_main =FirebaseDatabase.getInstance().getReference().child("Admin")
                        .child("UserTable").child(Profile.getCurrentProfile().getId());
                usertable.removeValue();
                report.removeValue();
                bhhoo.removeValue();
                chat_list.removeValue();
                chat_user.removeValue();
                user_detail.removeValue();
                user_meta_first.removeValue();
                user_meta_full.removeValue();
                user_main.removeValue();
                LoginManager.getInstance().logOut();
                Intent i = new Intent(Settings.this, Login.class);
                startActivity(i);
            }
        });
    }
}
