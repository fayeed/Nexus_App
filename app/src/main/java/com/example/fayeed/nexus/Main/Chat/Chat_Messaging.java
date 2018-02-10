package com.example.fayeed.nexus.Main.Chat;

import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.Chat;
import com.facebook.Profile;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Chat_Messaging extends AppCompatActivity {

    private EditText metText;
    private Button mbtSent;
    private DatabaseReference mFirebaseRef;

    private List<Chat> mChats;
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private String mId;
    String institudeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__messaging);
    }

    @Override
    public void onStart() {
        super.onStart();

        ImageButton imageButton = (ImageButton) findViewById(R.id.chatback);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack();
                finish();
            }
        });

        //Bundle bundle1 = this.getArguments();
        institudeID = getIntent().getStringExtra("id");

        TextView t = (TextView) findViewById(R.id.tperson);
        t.setText(getIntent().getStringExtra("name"));

        metText = (EditText) findViewById(R.id.msg);

        mRecyclerView = (RecyclerView) findViewById(R.id.chat_msg);
        mChats = new ArrayList<>();

        mId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new ChatAdapter(getApplicationContext(), mChats, mId);
        mRecyclerView.setAdapter(mAdapter);

        if(getIntent().getBooleanExtra("new", false) == true)
            mFirebaseRef = FirebaseDatabase.getInstance().getReference().child(institudeID).child("Chat").child("Message")
                    .child(getIntent().getStringExtra("chatId"));
        else
            mFirebaseRef = FirebaseDatabase.getInstance().getReference().child(institudeID).child("Chat").child("Message")
                    .child(getIntent().getStringExtra("chatId"));

        mFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot != null && dataSnapshot.getValue() != null) {
                    Chat model = dataSnapshot.getValue(Chat.class);
                    mChats.add(model);
                    mRecyclerView.scrollToPosition(mChats.size() - 1);
                    mAdapter.notifyItemInserted(mChats.size() - 1);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ImageButton send = (ImageButton) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat chat = new Chat(metText.getText().toString(), Profile.getCurrentProfile().getId().toString());
                metText.setText("");
                mFirebaseRef.push().setValue(chat);
            }
        });
    }
}
