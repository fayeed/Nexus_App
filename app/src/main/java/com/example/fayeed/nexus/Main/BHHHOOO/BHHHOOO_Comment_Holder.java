package com.example.fayeed.nexus.Main.BHHHOOO;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
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

public class BHHHOOO_Comment_Holder extends RecyclerView.ViewHolder{

    View v;
    FirebaseStorage storage;
    CircularImageView imageView;

    public BHHHOOO_Comment_Holder(View itemView) {
        super(itemView);
        v = itemView;
    }

    public void setUserName(String userName, String comment){
        TextView text = (TextView) v.findViewById(R.id.name1);
        text.setText(Html.fromHtml("<b>" + userName + "</b>" + " " + comment));
    }

    public void setDetailMsg (String detailMsg){
        TextView text = (TextView) v.findViewById(R.id.content2);
        text.setText(detailMsg);
    }

    public void setDImage (final String image){
        imageView = (CircularImageView) v.findViewById(R.id.chat_main_all_image);
        storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com/" + image );

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(getApplicationContext())
                        .load(uri)
                        .transform(new CropCircleTransformation())
                        .resize(400, 400)
                        .into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }
}
