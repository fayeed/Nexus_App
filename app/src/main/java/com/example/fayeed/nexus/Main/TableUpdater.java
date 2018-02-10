package com.example.fayeed.nexus.Main;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Toast;

import com.example.fayeed.nexus.Tables.Firebase.AdminUser;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by fayeed on 2/7/2017.
 */

public class TableUpdater {

    DatabaseReference reference;
    AdminUser adminUser;
    String institudeId;

    public TableUpdater(String institudeId){
        this.institudeId = institudeId;
        reference = FirebaseDatabase.getInstance().getReference().child(institudeId).child("Admin").child("Usertable")
                .child(Profile.getCurrentProfile().getId());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adminUser = dataSnapshot.getValue(AdminUser.class);
                if(adminUser == null) {
                    Log.i("LOG", "No data was acquired");
                } else {
                    Log.i("LOG", "strikes are : " + dataSnapshot.getValue());
                    if(adminUser.getStrikes() > 0){
                        Toast.makeText(getApplicationContext(), "You have received " + adminUser.getStrikes(), Toast.LENGTH_SHORT).show();
                    } else if(adminUser.isBanned()){
                        Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();
                    }
                    Log.i("LOG", "strikes" + adminUser.getStrikes());
                    //reference.setValue(adminUser);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
