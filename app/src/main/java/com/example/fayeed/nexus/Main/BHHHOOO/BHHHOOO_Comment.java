package com.example.fayeed.nexus.Main.BHHHOOO;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.BHHHOOO;
import com.example.fayeed.nexus.Tables.Firebase.BHHHOOO_C_T;
import com.facebook.Profile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class BHHHOOO_Comment extends AppCompatActivity {

    FirebaseRecyclerAdapter adapter;
    FirebaseStorage storage;
    CircularImageView propic;
    TextView name;
    TextView content;
    TextView date;
    String key;
    String institudeID;
    Bundle bundle;
    GraphEventTrigger graphEventTrigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhhhooo__c);
    }

    @Override
    public void onStart() {
        super.onStart();

        /*bundle = getArguments();
        institudeID = bundle.getString("id");*/
        institudeID = getIntent().getStringExtra("id");
        graphEventTrigger = new GraphEventTrigger(institudeID, this);
        ImageButton button = (ImageButton) findViewById(R.id.bhhooback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack();
                finish();
            }
        });

        ImageButton button1 = (ImageButton) findViewById(R.id.post);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.comments);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID)
                        .child("BHHHOOO").child("All").child("comments").child(key);
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child(institudeID)
                        .child("BHHHOOO").child("User").child(Profile.getCurrentProfile().getId()).child("comments").child(key);
                String s = Profile.getCurrentProfile().getId().toString();
                String imageLocation = "Profile/P"+ s + ".jpg";
                String key1 = ref.push().getKey();
                BHHHOOO_C_T discussion_r = new BHHHOOO_C_T(Profile.getCurrentProfile().getFirstName(), s,
                        text.getText().toString(), imageLocation);
                ref.push().setValue(discussion_r);
                ref1.push().setValue(discussion_r);
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(institudeID)
                        .child("BHHHOOO").child("All").child("meta").child(key);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        BHHHOOO discussion = dataSnapshot.getValue(BHHHOOO.class);
                        //Log.i("LOG", Double.toString(discussion.getNoOfThreads()));
                        discussion.setComments(discussion.getComments() + 1);
                        reference.setValue(discussion);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                graphEventTrigger.dBhhhoooCommentsposted();
            }
        });

        /*TextView textView = (TextView) findViewById(R.id.bhhoocomment);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postThread();
            }
        });*/

        Initialize();
    }

    void Initialize (){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        propic = (CircularImageView) findViewById(R.id.chat_main_all_image);
        name = (TextView) findViewById(R.id.name1);
        content = (TextView) findViewById(R.id.content2);
        date = (TextView) findViewById(R.id.timest);

        /*name.setText(getIntent().getStringExtra("name"));
        content.setText(getIntent().getStringExtra("desc"));
        date.setText(getIntent().getStringExtra("timestamp"));
        setDImage("Profile/P" + getIntent().getStringExtra("bhhooId") + ".jpg");*/
        key = getIntent().getStringExtra("key");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID)
                .child("BHHHOOO").child("All").child("comments").child(key);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.bhhhooocrecy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new FirebaseRecyclerAdapter<BHHHOOO_C_T, BHHHOOO_Comment_Holder>(BHHHOOO_C_T.class, R.layout.bhhhooo_crecy,
                BHHHOOO_Comment_Holder.class, ref) {

            @Override
            protected void populateViewHolder(BHHHOOO_Comment_Holder viewHolder, BHHHOOO_C_T model, int position) {
                viewHolder.setUserName(model.getName(), model.getContent());
                viewHolder.setDImage(model.getImageLocation());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }

    public void setDImage (String image){

        storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://thread-4cc4e.appspot.com/" + image );

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getApplicationContext())
                        .load(uri)
                        .transform(new CropCircleTransformation())
                        .resize(400, 400)
                        .into(propic);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }

    public void postThread(){
        BCCustomDialogBox dialogBox = new BCCustomDialogBox(this, institudeID, key);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBox.show();
    }
}
