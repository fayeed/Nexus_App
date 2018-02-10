package com.example.fayeed.nexus.Main.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.Main.Settings;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.User;
import com.facebook.AccessToken;
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

public class UserProfile extends Fragment {

    Profile userProfile;
    String userId;
    TextView tv;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    ImageView profilePic;
    String userName;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPref = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        institudeID = sharedPref.getString("id", null);

        graphEventTrigger = new GraphEventTrigger(institudeID, getActivity().getApplicationContext());
        graphEventTrigger.profileIn();

        ImageButton imageButton1 = (ImageButton) getActivity().findViewById(R.id.logout);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccessToken.getCurrentAccessToken() == null){
                    Log.i("LOG", "Alredy logged out");
                } else {
                    //LoginManager.getInstance().logOut();
                    Intent intent = new Intent(getActivity(), Settings.class);
                    intent.putExtra("id", institudeID);
                    getActivity().startActivity(intent);

                }
            }
        });

        ImageButton imageButton2 = (ImageButton) getActivity().findViewById(R.id.search);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SearchProfile.class);
                i.putExtra("id", institudeID);
                startActivity(i);
            }
        });


        Log.i("LOG_TAG", "Worked");

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com");

        /*userProfile = userProfile.getCurrentProfile();
        userName = userProfile.getName();
        userId = userProfile.getId();*/

        tv = (TextView) getActivity().findViewById(R.id.name1);
        tv1 = (TextView) getActivity().findViewById(R.id.job);
        tv2 = (TextView) getActivity().findViewById(R.id.bio);
        tv5 = (TextView) getActivity().findViewById(R.id.linked);
        tv3 = (TextView) getActivity().findViewById(R.id.email);
        tv4 = (TextView) getActivity().findViewById(R.id.phone);
        //profilePic = (ImageView) getActivity().findViewById(R.id.imageView);
        circularImageView = (CircularImageView)getActivity().findViewById(R.id.yourCircularImageView);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("User")
                .child("Detail").child(Profile.getCurrentProfile().getId().toString());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userName = user.getName();
                job = user.getJob();
                bio = user.getBio();
                email = user.getEmail();
                phone = user.getPhoneNo();

                tv.setText(Profile.getCurrentProfile().getName());
                tv1.setText(job);
                tv2.setText(bio);
                tv5.setText(user.getLinkedIn());
                tv3.setText(email);
                tv4.setText(phone);
                downloadImage("gs://nexus-f4ba4.appspot.com/Profile/P" + Profile.getCurrentProfile().getId().toString() + ".jpg");
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
                Picasso.with(getContext())
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
