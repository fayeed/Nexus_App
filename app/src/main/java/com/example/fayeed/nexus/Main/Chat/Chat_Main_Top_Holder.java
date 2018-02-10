package com.example.fayeed.nexus.Main.Chat;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.fayeed.nexus.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Chat_Main_Top_Holder extends RecyclerView.ViewHolder{

    View mainTopHolder;
    FirebaseStorage storage;
    CircularImageView chattop;

    public Chat_Main_Top_Holder(View itemView) {
        super(itemView);
        mainTopHolder = itemView;
    }

    public void hide(){
        View v = mainTopHolder.findViewById(R.id.chat_top);
        v.getLayoutParams().height = 0;
        v.getLayoutParams().width = 0;
    }

    public void setCAImage (String image){
        chattop = (CircularImageView) mainTopHolder.findViewById(R.id.chat_main_all_image);
        storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com/Profile/P" + image + ".jpg");

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(getApplicationContext())
                        .load(uri)
                        .transform(new CropCircleTransformation())
                        .resize(400, 400)
                        .into(chattop);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }
}
