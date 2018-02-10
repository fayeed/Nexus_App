package com.example.fayeed.nexus.Main.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3;
import com.example.fayeed.nexus.Main.Login.Login;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.AWS.IdTableDO;
import com.example.fayeed.nexus.Tables.AWS.UserProfileDO;
import com.example.fayeed.nexus.Tables.Firebase.User;
import com.facebook.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static android.app.Activity.RESULT_OK;

public class Edit_Profile extends AppCompatActivity {

    com.facebook.Profile userProfile;
    EditText editName;
    EditText editJob;
    EditText editBio;
    EditText editEmail;
    EditText editPhone;
    EditText linkedIn;
    int SELECT_IMAGE = 1;
    ImageView profilePicture;
    String selectedImagePath;
    FirebaseStorage storage;
    StorageReference storageRef;
    Spinner spinner;
    String gender;

    String userName;
    String job = null;
    String bio = null;
    String email = null;
    String phone = null;
    String institudeID;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        SharedPreferences sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        institudeID = sharedPref.getString("id", null);
        initialize();
        downloadImage();
    }



    @Override
    public void onStart() {
        super.onStart();
        ImageButton imageButton = (ImageButton) findViewById(R.id.editback);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack();
                finish();
            }
        });

        ImageButton imageButton1 = (ImageButton) findViewById(R.id.editaccept);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("User")
                        .child("Detail").child(userProfile.getId().toString());
                User user = new User(editName.getText().toString(), editJob.getText().toString(), editBio.getText().toString()
                        , "Profile/P" + userProfile.getId().toString() + ".jpg", editEmail.getText().toString()
                        , editPhone.getText().toString(), linkedIn.getText().toString(), gender);
                ref.setValue(user);
                uploadImage();
                finish();
            }
        });

        ImageView pic = (ImageView) findViewById(R.id.profilePic);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
            }
        });

    }

    public void initialize(){
        userProfile = com.facebook.Profile.getCurrentProfile();

        //selectedImagePath = "https://graph.facebook.com/" + userProfile.getId() + "/picture?type=large";

        profilePicture = (ImageView) findViewById(R.id.profilePic);
        editName = (EditText) findViewById(R.id.editName);
        editJob = (EditText) findViewById(R.id.editJob);
        editBio = (EditText) findViewById(R.id.editBio);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPhone = (EditText) findViewById(R.id.editPhone);
        linkedIn = (EditText) findViewById(R.id.linkedIn);
        spinner = (Spinner) findViewById(R.id.gender);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com");

        final List<String> gender_list = new ArrayList<>();
        gender_list.add("Not Specified");
        gender_list.add("Male");
        gender_list.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_textview, gender_list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                gender = item;
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(getIntent().getBooleanExtra("register", false)){
            Picasso.with(getApplicationContext())
                    .load("https://graph.facebook.com/" + userProfile.getId() + "/picture?type=large")
                    .transform(new CropCircleTransformation())
                    .into(profilePicture);
        }else{
            retrieveProfile();
        }
    }

    public void retrieveProfile(){
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
                editName.setText(userName);
                editJob.setText(job);
                editBio.setText(bio);
                editEmail.setText(email);
                editPhone.setText(phone);
                gender = user.getGender();
                if(gender == "Not Specified")
                    spinner.setSelection(0);
                else if(gender.equals("Male"))
                    spinner.setSelection(1);
                else
                    spinner.setSelection(2);
                linkedIn.setText(user.getLinkedIn());
                //downloadImage();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                Picasso.with(getApplicationContext())
                        .load(selectedImageUri)
                        .transform(new CropCircleTransformation())
                        .into(profilePicture);
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public void uploadImage(){
        StorageReference profileImagesRef = storageRef.child("Profile/P" + userProfile.getId().toString() + ".jpg");

        profilePicture.setDrawingCacheEnabled(true);
        profilePicture.buildDrawingCache();
        Bitmap bitmap = profilePicture.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profileImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    void downloadImage(){
        StorageReference gsReference = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com/Profile/P"
                + userProfile.getId().toString() + ".jpg");

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getApplicationContext())
                        .load(uri)
                        .transform(new CropCircleTransformation())
                        .resize(400, 400)
                        .into(profilePicture);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }
}
