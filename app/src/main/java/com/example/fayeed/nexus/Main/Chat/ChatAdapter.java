package com.example.fayeed.nexus.Main.Chat;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.Chat;
import com.facebook.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    private List<Chat> mDataset;
    private SparseBooleanArray selectedItems;
    private Context mContext;
    private String mId;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) itemView.findViewById(R.id.chatmsg1);

        }

        public void setImage(String id){
            FirebaseStorage storage;
            storage = FirebaseStorage.getInstance();
            StorageReference gsReference = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com/Profile/P" +
                    Profile.getCurrentProfile().getId().toString() + ".jpg" );

            gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    ImageView image = (ImageView) itemView.findViewById(R.id.chatmsgpro1);
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

    public ChatAdapter(Context context, List<Chat> myDataset, String id) {
        mContext = context;
        mDataset = myDataset;
        selectedItems = new SparseBooleanArray();
        mId = id;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if(viewType == 1) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chatrecy_m2, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chatrecy_m1, parent, false);
        }

        ViewHolder vh = new ViewHolder(v);
        Integer i = vh.getPosition();
        v.setTag(vh);

        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        if(mDataset.get(position).getId().equals(Profile.getCurrentProfile().getId().toString()))
            return 1;

        return 2;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chat chat = mDataset.get(position);
        holder.mTextView.setText(chat.getMessage());
        holder.setImage(chat.getId());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
