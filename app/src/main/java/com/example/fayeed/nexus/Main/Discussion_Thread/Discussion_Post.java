package com.example.fayeed.nexus.Main.Discussion_Thread;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.Discussion;
import com.facebook.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static android.app.Activity.RESULT_OK;

public class Discussion_Post extends AppCompatActivity{

    EditText topic;
    EditText desc;
    FirebaseStorage storage;
    int SELECT_IMAGE = 1;
    String selectedImagePath;
    ImageView discPicture;
    String imageLocation;
    String institudeID;
    GraphEventTrigger graphEventTrigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_post);
    }

    @Override
    public void onStart() {
        super.onStart();

        //Bundle bundle = this.getArguments();
        institudeID  = getIntent().getStringExtra("id");

        graphEventTrigger = new GraphEventTrigger(institudeID, getApplicationContext());

        topic = (EditText) findViewById(R.id.topic);
        desc = (EditText) findViewById(R.id.desc);
        discPicture = (ImageView) findViewById(R.id.discussion);
        Button cancel = (Button) findViewById(R.id.can);
        Button post = (Button) findViewById(R.id.ok);

        ImageButton imageButton = (ImageButton) findViewById(R.id.discussionpback);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack();
                finish();
            }
        });

        discPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = topic.getText().toString();
                String s1 = desc.getText().toString();
                DatabaseReference ref_M = FirebaseDatabase.getInstance().getReference().child(institudeID)
                        .child("discussion").child("All").child("meta");
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(institudeID)
                        .child("discussion").child("User").child(Profile.getCurrentProfile().getId()).child("meta");
                String key = ref_M.push().getKey();
                imageLocation = "Discussion/"+ key + ".jpg";
                Discussion discussion = new Discussion(s, s1, Profile.getCurrentProfile().getFirstName(), imageLocation, key
                , Profile.getCurrentProfile().getId());
                ref_M.child(key).setValue(discussion);
                reference.child(key).setValue(discussion);
                uploadImage();
                graphEventTrigger.dDiscussionPosted();
                finish();
            }
        });

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
                        .into(discPicture);
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
        StorageReference profileImagesRef = FirebaseStorage.getInstance().getReference().child(imageLocation);

        discPicture.setDrawingCacheEnabled(true);
        discPicture.buildDrawingCache();
        Bitmap bitmap = discPicture.getDrawingCache();
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
}
