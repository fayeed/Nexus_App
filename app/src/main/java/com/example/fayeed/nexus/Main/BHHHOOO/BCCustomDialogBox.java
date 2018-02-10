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
import com.example.fayeed.nexus.Tables.Firebase.BHHHOOO_C_T;
import com.example.fayeed.nexus.Tables.Firebase.Discussion;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BCCustomDialogBox extends Dialog implements android.view.View.OnClickListener{

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public String institudeId;
    public String key;
    public EditText editText;
    GraphEventTrigger graphEventTrigger;

    public BCCustomDialogBox(Activity a, String inst, String key){
        super(a);
        this.c = a;
        this.institudeId = inst;
        this.key = key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdialogbox_b_c);
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
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("BHHHOOO").child("All").child("comments").child(key);
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("BHHHOOO").child("User").child(Profile.getCurrentProfile().getId()).child("comments").child(key);
                String s = Profile.getCurrentProfile().getId().toString();
                String imageLocation = "Profile/P"+ s + ".jpg";
                String key1 = ref.push().getKey();
                BHHHOOO_C_T discussion_r = new BHHHOOO_C_T(Profile.getCurrentProfile().getFirstName(), s,
                        editText.getText().toString(), imageLocation);
                ref.push().setValue(discussion_r);
                ref1.push().setValue(discussion_r);
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("BHHHOOO").child("All").child("meta").child(key);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        BHHHOOO discussion = dataSnapshot.getValue(BHHHOOO.class);
                        //Log.i("LOG", Double.toString(discussion.getNoOfThreads()));
                        discussion.setComments(discussion.getComments() + 1);
                        reference.setValue(discussion);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                graphEventTrigger.dBhhhoooCommentsposted();
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
