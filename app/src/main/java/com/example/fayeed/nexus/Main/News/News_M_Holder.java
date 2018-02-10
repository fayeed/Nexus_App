package com.example.fayeed.nexus.Main.News;


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

import static com.facebook.FacebookSdk.getApplicationContext;

public class News_M_Holder extends RecyclerView.ViewHolder {

    View mView;
    CircularImageView image;
    FirebaseStorage storage;
    final String LOW = "Low";
    final String MEDIUM = "Medium";
    final String HIGH = "High";

    public News_M_Holder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setTopic(String name) {
        TextView field = (TextView) mView.findViewById(R.id.title_N);
        field.setText(name);
    }

    public void setName(String name){
        TextView field = (TextView) mView.findViewById(R.id.name);
        field.setText(name);
    }

    public void setView(int v){
        TextView field = (TextView) mView.findViewById(R.id.view);
        field.setText(Integer.toString(v));
    }

    public void setContent(String text) {
        TextView field = (TextView) mView.findViewById(R.id.content);
        field.setText(text);
    }

    public void setTime(String text) {
        TextView field = (TextView) mView.findViewById(R.id.time_N);
        field.setText(text);
    }

    public void setPrioirity(String text){
        ImageView priority = (ImageView) mView.findViewById(R.id.priority);
        if(text.equals(LOW))
            priority.setImageResource(R.drawable.green_p);
        else if(text.equals(MEDIUM))
            priority.setImageResource(R.drawable.yellow_p);
        else if(text.equals(HIGH))
            priority.setImageResource(R.drawable.red_p);
    }

    public void hide(){
        mView.getLayoutParams().width = 0;
        mView.getLayoutParams().height = 0;
    }

    public void setProPic(String proPic) {
        image = (CircularImageView) mView.findViewById(R.id.imageView4);
        storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com/Profile/P" + proPic + ".jpg");

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(getApplicationContext())
                        .load(uri)
                        .transform(new CropCircleTransformation())
                        .resize(800, 800)
                        .into(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }
}
