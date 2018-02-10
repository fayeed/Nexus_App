package com.example.fayeed.nexus.Main.Discussion_Thread;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.Discussion;
import com.example.fayeed.nexus.Tables.Firebase.Discussion_R;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DCCustomDialogBox extends Dialog implements android.view.View.OnClickListener{

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public String institudeId;
    public String key;
    public EditText editText;
    GraphEventTrigger graphEventTrigger;

    public DCCustomDialogBox(Activity a, String inst, String key){
        super(a);
        this.c = a;
        this.institudeId = inst;
        this.key = key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdialogbox_d_c);
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
                        .child("discussion").child("All").child("detail").child(key);
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("discussion").child("User").child(Profile.getCurrentProfile().getId()).child("detail").child(key);
                String s = Profile.getCurrentProfile().getId().toString();
                String imageLocation = "Profile/P"+ s + ".jpg";
                String key1 = ref.push().getKey();
                Discussion_R discussion_r = new Discussion_R(Profile.getCurrentProfile().getFirstName()
                        , s, editText.getText().toString(), key1);
                ref.push().setValue(discussion_r);
                ref1.push().setValue(discussion_r);
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("discussion").child("All").child("meta").child(key);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Discussion discussion = dataSnapshot.getValue(Discussion.class);
                        //Log.i("LOG", Double.toString(discussion.getNoOfThreads()));
                        discussion.setNoOfThreads(discussion.getNoOfThreads() + 1);
                        reference.setValue(discussion);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                graphEventTrigger.dDiscussionCommentsPosted();
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
