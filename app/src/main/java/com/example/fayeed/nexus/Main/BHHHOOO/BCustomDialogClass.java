package com.example.fayeed.nexus.Main.BHHHOOO;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.BHHHOOO;
import com.example.fayeed.nexus.Tables.Firebase.User;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BCustomDialogClass extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public String institudeId;
    public String key;
    public EditText editText;
    DatabaseReference ref;
    DatabaseReference ref1;
    DatabaseReference ref2;
    GraphEventTrigger graphEventTrigger;
    String role;

    public BCustomDialogClass(Activity a, String inst, String role) {
        super(a);
        this.c = a;
        this.institudeId = inst;
        this.role = role;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdialogbox_b);
        editText = (EditText) findViewById(R.id.editText);
        yes = (Button) findViewById(R.id.ok);
        no = (Button) findViewById(R.id.can);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        graphEventTrigger = new GraphEventTrigger(institudeId, getApplicationContext());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                //c.finish();
                ref = FirebaseDatabase.getInstance().getReference().child(institudeId).child("BHHHOOO")
                        .child("All").child("meta");
                ref1 = FirebaseDatabase.getInstance().getReference().child(institudeId).child("User")
                        .child("Detail").child(Profile.getCurrentProfile().getId().toString());
                ref2 = FirebaseDatabase.getInstance().getReference().child(institudeId).child("BHHHOOO")
                        .child("User").child(Profile.getCurrentProfile().getId()).child("meta");
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        key = ref.push().getKey();
                        BHHHOOO bhhhooo1 = new BHHHOOO(user.getName().toString(), editText.getText().toString()
                                , false, key, Profile.getCurrentProfile().getId().toString(), role);
                        ref.child(key).setValue(bhhhooo1);
                        ref2.child(key).setValue(bhhhooo1);
                        graphEventTrigger.dBhhhoooPosted();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                ref1.addListenerForSingleValueEvent(valueEventListener);
                break;
            case R.id.can:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
