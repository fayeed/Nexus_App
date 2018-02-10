package com.example.fayeed.nexus.Main.Discussion_Thread;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fayeed.nexus.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Discussion_Main_Holder extends RecyclerView.ViewHolder{

    View view;
    FirebaseStorage storage;
    CircularImageView dMainImageView;

    public Discussion_Main_Holder(View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setTpoic (String topic){
        TextView field = (TextView) view.findViewById(R.id.topic);
        field.setText(topic);
    }

    public void setName(String name){
        TextView field = (TextView) view.findViewById(R.id.username);
        field.setText(name);
    }

    public void setView (int v){
        TextView field = (TextView) view.findViewById(R.id.view);
        field.setText(Integer.toString(v));
    }

    public void setThread(String t){
        TextView field = (TextView) view.findViewById(R.id.thread);
        field.setText(t);
    }

    public void setSmallDesc (String smallDesc){
        TextView field = (TextView) view.findViewById(R.id.smalldesc);
        field.setText(smallDesc);
    }

    public void setTime (String time){
        TextView field = (TextView) view.findViewById(R.id.time_D);
        field.setText(time);
    }

    public void setNoOfThreads (int noOfThreads){
        TextView field =(TextView) view.findViewById(R.id.thread);
        field.setText(Integer.toString(noOfThreads));
    }

    public void setDImage (String image){
        dMainImageView = (CircularImageView) view.findViewById(R.id.discussion);

        storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com/" + image );

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(getApplicationContext())
                        .load(uri)
                        .transform(new CropCircleTransformation())
                        .resize(400, 400)
                        .into(dMainImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }
}
