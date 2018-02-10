package com.example.fayeed.nexus.Main.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.Graph;
import com.example.fayeed.nexus.Tables.Firebase.User;
import com.example.fayeed.nexus.Tables.Firebase.UserMeta;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class SearchProfile extends AppCompatActivity {

    Profile userProfile;
    String userId;
    TextView tv;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    EditText editText;
    ImageView profilePic;
    String userName;
    String id;
    String job = null;
    String bio = null;
    String email = null;
    String phone = null;
    FirebaseStorage storage;
    StorageReference storageRef;
    String institudeID;
    CircularImageView circularImageView;
    GraphEventTrigger graphEventTrigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile);
    }

    @Override
    public void onStart() {
        super.onStart();

        FacebookSdk.sdkInitialize(getApplicationContext());
        SharedPreferences sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        institudeID = sharedPref.getString("id", null);

        graphEventTrigger = new GraphEventTrigger(institudeID, getApplicationContext());

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://thread-4cc4e.appspot.com");

        userProfile = userProfile.getCurrentProfile();
        userName = userProfile.getName();
        userId = userProfile.getId();

        tv = (TextView) findViewById(R.id.name1);
        tv1 = (TextView) findViewById(R.id.job);
        tv2 = (TextView) findViewById(R.id.bio);
        tv3 = (TextView) findViewById(R.id.email);
        tv4 = (TextView) findViewById(R.id.phone);
        tv5 = (TextView) findViewById(R.id.linked);
        editText = (EditText) findViewById(R.id.searchtext);
        //profilePic = (ImageView) findViewById(R.id.imageView);
        circularImageView = (CircularImageView) findViewById(R.id.yourCircularImageView);

        ImageButton searchButton = (ImageButton) findViewById(R.id.searchbutton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                search(s);
            }
        });
    }

    public void search(String name){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("User")
                .child("meta").child(name);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserMeta user = dataSnapshot.getValue(UserMeta.class);
                if(user != null){
                    id = user.getId();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("User")
                            .child("Detail").child(id);
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            ScrollView main = (ScrollView) findViewById(R.id.search_content);
                            main.setVisibility(View.VISIBLE);
                            userName = user.getName();
                            job = user.getJob();
                            bio = user.getBio();
                            email = user.getEmail();
                            phone = user.getPhoneNo();

                            tv.setText(userName);
                            tv1.setText(job);
                            tv2.setText(bio);
                            tv3.setText(email);
                            tv4.setText(phone);
                            tv5.setText(user.getLinkedIn());
                            downloadImage("gs://nexus-f4ba4.appspot.com/Profile/P" + id + ".jpg");
                            graphEventTrigger.pSearched();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    ref.addListenerForSingleValueEvent(valueEventListener);
                } else {
                    Log.i("LOG", "no name exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    void downloadImage(String location){
        StorageReference gsReference = storage.getReferenceFromUrl(location);

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getApplicationContext())
                        .load(uri)
                        .transform(new CropCircleTransformation())
                        .resize(400, 400)
                        .into(circularImageView);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }
}
