package com.example.fayeed.nexus.Main.BHHHOOO;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class BHHHOOO_Main_Holder extends RecyclerView.ViewHolder{

    public View v;
    FirebaseStorage storage;
    CircularImageView image;

    public BHHHOOO_Main_Holder(View itemView) {
        super(itemView);
        v = itemView;
    }

    public void setName(String name){
        TextView textView = (TextView) v.findViewById(R.id.name1);
        textView.setText(name);
    }

    public void setView(int vl){
        TextView textView = (TextView) v.findViewById(R.id.view);
        textView.setText(Integer.toString(vl));
    }

    public void setComments(int com){
        TextView textView = (TextView) v.findViewById(R.id.comments);
        textView.setText(Integer.toString(com));
    }

    public void setContent(String content){
        TextView textView = (TextView) v.findViewById(R.id.content2);
        textView.setText(content);
    }

    public void setTimeStamp(String timeStamp){
        TextView textView = (TextView) v.findViewById(R.id.timest);
        textView.setText(timeStamp);
    }

    public void setProPic(String proPic){
        image = (CircularImageView) v.findViewById(R.id.chat_main_all_image);
        storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com/Profile/P" + proPic + ".jpg" );

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

    public void hide(){
        //v.setVisibility(View.GONE);
        v.getLayoutParams().width = 0;
        v.getLayoutParams().height = 0;
    }

    /*public void setOpImage(String opImage){
        storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com/BHHHOOO/B" + opImage + ".jpg" );

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageView image = (ImageView) v.findViewById(R.id.opImage);
                //image.setLeft(1000);
                //image.setTop(5000);
                Picasso.with(getApplicationContext())
                        .load(uri)
                        .transform(new CropSquareTransformation())
                        .resize(800, 500)
                        .into(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }*/
}
