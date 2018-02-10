package com.example.fayeed.nexus.Main.Discussion_Thread;

import android.content.res.Resources;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.auth.policy.Resource;
import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.BHHHOOO;
import com.example.fayeed.nexus.Tables.Firebase.BHHHOOO_C_T;
import com.example.fayeed.nexus.Tables.Firebase.Discussion;
import com.example.fayeed.nexus.Tables.Firebase.Discussion_R;
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

import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class Discussion_Detail extends AppCompatActivity {

    FirebaseRecyclerAdapter rAdapter;
    FirebaseStorage storage;
    TextView topic;
    TextView desc;
    TextView time;
    TextView user;
    ImageView pic;
    TextView noView;
    TextView noThreads;
    String title;
    String smallDesc;
    String timeStamp;
    int noOfThread;
    int view;
    String imageLocation;
    boolean closed;
    String creator;
    String key;
    String institudeID;
    Bundle bundle;
    CircularImageView dDetailImageView;
    GraphEventTrigger graphEventTrigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion__d);
    }

    @Override
    public void onStart() {
        super.onStart();

        Initailze();
    }

    public void Initailze(){
        topic = (TextView) findViewById(R.id.topic);
        desc = (TextView) findViewById(R.id.smalldesc);
        time = (TextView) findViewById(R.id.time_D);
        user = (TextView) findViewById(R.id.parent);
        pic = (ImageView) findViewById(R.id.discussion);
        dDetailImageView = (CircularImageView) findViewById(R.id.discussion);
        noView = (TextView) findViewById(R.id.view);
        noThreads = (TextView) findViewById(R.id.threads);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

       ImageButton imageButton = (ImageButton) findViewById(R.id.discussiondback);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack();
                finish();
            }
        });

        /*TextView textView = (TextView) findViewById(R.id.discussioncomment);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postThread();
            }
        });*/

        //bundle = this.getArguments();
        title = getIntent().getStringExtra("topic");
        smallDesc = getIntent().getStringExtra("smallDesc");
        timeStamp = getIntent().getStringExtra("timestamp");
        noOfThread = getIntent().getIntExtra("noOfThreads", 0);
        imageLocation = getIntent().getStringExtra("imageLocation");
        creator = getIntent().getStringExtra("creator");
        key = getIntent().getStringExtra("key");
        closed = getIntent().getBooleanExtra("closed", false);
        institudeID = getIntent().getStringExtra("id");
        view = getIntent().getIntExtra("view", 0);

        topic.setText(title);
        desc.setText(smallDesc);
        time.setText(timeStamp);
        user.setText(creator);
        //pic.setImageURI(Uri.parse(imageLocation));
        setDImage(imageLocation);
        noView.setText(Integer.toString(view));
        noThreads.setText(Integer.toString(noOfThread));

        graphEventTrigger = new GraphEventTrigger(institudeID, this);

        ImageButton button1 = (ImageButton) findViewById(R.id.post);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.comments);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID)
                        .child("discussion").child("All").child("detail").child(key);
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child(institudeID)
                        .child("discussion").child("User").child(Profile.getCurrentProfile().getId()).child("detail").child(key);
                String s = Profile.getCurrentProfile().getId().toString();
                String imageLocation = "Profile/P"+ s + ".jpg";
                String key1 = ref.push().getKey();
                Discussion_R discussion_r = new Discussion_R(Profile.getCurrentProfile().getFirstName()
                        , s, text.getText().toString(), key1);
                ref.push().setValue(discussion_r);
                ref1.push().setValue(discussion_r);
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(institudeID)
                        .child("discussion").child("All").child("meta").child(key);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Discussion discussion = dataSnapshot.getValue(Discussion.class);
                        //Log.i("LOG", Double.toString(discussion.getNoOfThreads()));
                        discussion.setNoOfThreads(discussion.getNoOfThreads() + 1);
                        reference.setValue(discussion);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                graphEventTrigger.dDiscussionCommentsPosted();
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID)
                .child("discussion").child("All").child("detail").child(key);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.discussionrecy_d);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        rAdapter = new FirebaseRecyclerAdapter<Discussion_R, Discussion_Detail_Holder>(Discussion_R.class, R.layout.discussionrecy_d,
                Discussion_Detail_Holder.class, ref) {

            @Override
            protected void populateViewHolder(Discussion_Detail_Holder viewHolder, Discussion_R model, int position) {
                viewHolder.setUserName(model.getName());
                viewHolder.setDetailMsg(model.getContent());
                viewHolder.setDate(model.getTimestamp());
            }
        };
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getApplicationContext()).color(R.color.app_b)
               .sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        recyclerView.setAdapter(rAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rAdapter.cleanup();
    }

    public void setDImage (String image){

        storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com/" + image );

        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getApplicationContext())
                        .load(uri)
                        .transform(new CropSquareTransformation())
                        .resize(400, 400)
                        .into(dDetailImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }

    public void postThread(){
        DCCustomDialogBox dialogBox = new DCCustomDialogBox(this, institudeID, key);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBox.show();
    }
}
