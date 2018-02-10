package com.example.fayeed.nexus.Main.Chat;

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

public class Chat_Main_Holder extends RecyclerView.ViewHolder{

    View mainHolder;
    FirebaseStorage storage;
    CircularImageView chatMainImageView;

    public Chat_Main_Holder(View itemView) {
        super(itemView);
        mainHolder = itemView;
    }

    public void setName(String userName){
        TextView text = (TextView) mainHolder.findViewById(R.id.chat_main_all_text);
        text.setText(userName);
    }

    public void hide(){
        View v = mainHolder.findViewById(R.id.chat_l);
        v.getLayoutParams().height = 0;
        v.getLayoutParams().width = 0;
    }

    public void setCAImage (String image){
        chatMainImageView = (CircularImageView) mainHolder.findViewById(R.id.chat_main_all_image);
        storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com/Profile/P" + image + ".jpg");

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getApplicationContext())
                        .load(uri)
                        .transform(new CropCircleTransformation())
                        .resize(400, 400)
                        .into(chatMainImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }
}
