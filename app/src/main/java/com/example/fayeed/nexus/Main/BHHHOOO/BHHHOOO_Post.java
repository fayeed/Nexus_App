package com.example.fayeed.nexus.Main.BHHHOOO;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.BHHHOOO;
import com.example.fayeed.nexus.Tables.Firebase.User;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class BHHHOOO_Post extends AppCompatActivity {

    EditText content;
    ImageView pic;
    DatabaseReference ref1;
    DatabaseReference ref;
    User user;
    FirebaseStorage storage;
    StorageReference storageRef;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhhhooo__post);

        content =(EditText) findViewById(R.id.bhhhooopost);
        pic = (ImageView) findViewById(R.id.bhhhooopostimage);

        ref = FirebaseDatabase.getInstance().getReference().child("abc").child("BHHHOOO").child("meta");
        ref1 = FirebaseDatabase.getInstance().getReference().child("abc").child("User")
                .child(Profile.getCurrentProfile().getId().toString());

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://thread-4cc4e.appspot.com");



    }

    /*public void getImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                //pic.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
                /*Picasso.with(getApplicationContext())
                        .load(selectedImageUri)
                        //.resize(1280, 720)
                        //.centerInside()
                        //.onlyScaleDown()
                        .fit()
                        .centerInside()
                        .transform(new CropSquareTransformation())
                        .into(pic);
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

        StorageReference profileImagesRef = storageRef.child("BHHHOOO/B"+ key + ".jpg");
        pic.setDrawingCacheEnabled(true);
        pic.buildDrawingCache();
        Bitmap bitmap = pic.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profileImagesRef.putBytes(data);

        /*Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        //StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = profileImagesRef.putFile(file);

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

   public void post(View v){

       ValueEventListener valueEventListener = new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               user = dataSnapshot.getValue(User.class);
               key = ref.push().getKey();
               if(pic.getDrawable() != null){
                   BHHHOOO bhhhooo = new BHHHOOO(user.getName().toString(), content.getText().toString()
                           , true, key, Profile.getCurrentProfile().getId().toString());
                   ref.push().setValue(bhhhooo);
                   //uploadImage();
               } else {
                   BHHHOOO bhhhooo1 = new BHHHOOO(user.getName().toString(), content.getText().toString()
                           , false, key, Profile.getCurrentProfile().getId().toString(), );
                   ref.push().setValue(bhhhooo1);
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       };
       ref1.addListenerForSingleValueEvent(valueEventListener);

   }*/

}
