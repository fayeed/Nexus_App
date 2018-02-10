package com.example.fayeed.nexus.Main.Chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fayeed.nexus.Main.BHHHOOO.BHHHOOO_Comment;
import com.example.fayeed.nexus.Main.BHHHOOO.BHHHOOO_Comment_Holder;
import com.example.fayeed.nexus.Main.BHHHOOO.BHHOOO_Main;
import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.BHHHOOO_C_T;
import com.example.fayeed.nexus.Tables.Firebase.Chat_All_Table;
import com.example.fayeed.nexus.Tables.Firebase.Chat_Open_Table;
import com.example.fayeed.nexus.Tables.Firebase.Graph;
import com.facebook.Profile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Chat_Main extends Fragment {

    FirebaseRecyclerAdapter adapter;
    FirebaseRecyclerAdapter tAdapter;
    String institudeID;
    GraphEventTrigger graphEventTrigger;
    boolean chatlId, chatmId, chathId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_chat__main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        //Bundle bundle1 = this.getArguments();
        //institudeID = bundle1.getString("id");

        SharedPreferences sharedPref = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        institudeID = sharedPref.getString("id", null);
        chatlId = sharedPref.getBoolean("chat_lId", true);
        chatmId = sharedPref.getBoolean("chat_mId", true);
        chathId = sharedPref.getBoolean("chat_hId", true);

        graphEventTrigger = new GraphEventTrigger(institudeID, getActivity().getApplicationContext());

        initialize();
    }

    void initialize(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        graphEventTrigger.chatIn();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID)
                .child("Chat").child("List");

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child(institudeID)
                .child("Chat").child("User").child(Profile.getCurrentProfile().getId().toString());

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.chat_all_recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        RecyclerView recyclerViewT = (RecyclerView) getActivity().findViewById(R.id.chat_top);
        recyclerViewT.setHasFixedSize(true);
        recyclerViewT.setLayoutManager(linearLayoutManager);

        tAdapter = new FirebaseRecyclerAdapter<Chat_Open_Table, Chat_Main_Top_Holder>(Chat_Open_Table.class,
                R.layout.chatrecy_m_top, Chat_Main_Top_Holder.class, ref1) {
            @Override
            protected void populateViewHolder(Chat_Main_Top_Holder viewHolder, final Chat_Open_Table model, int position) {

                if(model.getId().equals(Profile.getCurrentProfile().getId()))
                    viewHolder.hide();
                //Log.i("MainActivity", model.getO_id());
                if (!model.getId().equals(Profile.getCurrentProfile().getId())) {
                    viewHolder.setCAImage(model.getO_id().toString());

                    viewHolder.mainTopHolder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /*Bundle bundle = new Bundle();
                            bundle.putString("chatId", model.getId().toString());
                            bundle.putString("id", institudeID);
                            Chat_Messaging newFragment = new Chat_Messaging();
                            newFragment.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.addToBackStack(null);
                            transaction.replace(R.id.fragement_container, newFragment);
                            transaction.commit();*/
                            Intent i = new Intent(getActivity(), Chat_Messaging.class);
                            i.putExtra("chatId", model.getId());
                            i.putExtra("id", institudeID);
                            startActivity(i);

                        }
                    });
                }
            }
        };
        recyclerViewT.setAdapter(tAdapter);

        adapter = new FirebaseRecyclerAdapter<Chat_All_Table, Chat_Main_Holder>(Chat_All_Table.class, R.layout.chatrecy_m,
                Chat_Main_Holder.class, ref) {

            @Override
            protected void populateViewHolder(Chat_Main_Holder viewHolder, final Chat_All_Table model, int position) {
                //if(model.getId() == Profile.getCurrentProfile().getId()) {
                    viewHolder.setCAImage(model.getId());
                    viewHolder.setName(model.getName());
                    if(model.getId().equals(Profile.getCurrentProfile().getId()))
                        viewHolder.hide();

                if(model.getRole().equals("Low") && !chatlId || model.getRole().equals("Medium")
                        && !chatmId || model.getRole().equals("High") && !chathId){
                    viewHolder.hide();
                }


                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("Chat")
                                    .child("User").child(Profile.getCurrentProfile().getId().toString());

                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Log.i("MainActivity", dataSnapshot.getValue().toString());
                                    /*if (dataSnapshot.hasChild(Profile.getCurrentProfile().getId().toString() + model.getId().toString())) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("name", model.getName().toString());
                                        bundle.putString("chatId", model.getId().toString());
                                        bundle.putBoolean("new", true);
                                        bundle.putString("id", institudeID);
                                        Chat_Messaging newFragment = new Chat_Messaging();
                                        newFragment.setArguments(bundle);
                                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                        transaction.addToBackStack(null);
                                        transaction.replace(R.id.fragement_container, newFragment);
                                        transaction.commit();
                                        Intent i = new Intent(getActivity(), Chat_Messaging.class);
                                        i.putExtra("name", model.getName());
                                        i.putExtra("chatId", model.getId());
                                        i.putExtra("new", true);
                                        i.putExtra("id", institudeID);
                                        startActivity(i);
                                    } else {*/
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("Chat")
                                                .child("User").child(Profile.getCurrentProfile().getId().toString());

                                        String s = ref.push().getKey();

                                        Chat_Open_Table c = new Chat_Open_Table(Profile.getCurrentProfile().getId().toString()
                                                + model.getId().toString(), model.getId().toString(), model.getName());
                                        //ref.push().setValue(c);
                                        ref.child(Profile.getCurrentProfile().getId().toString() + model.getId().toString()).setValue(c);
                                        ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("Chat")
                                                .child("User").child(model.getId().toString());
                                        c.setO_id(Profile.getCurrentProfile().getId());
                                        ref.child(model.getId().toString() + Profile.getCurrentProfile().getId()).setValue(c);

                                        Intent i = new Intent(getActivity(), Chat_Messaging.class);
                                        i.putExtra("name", model.getName());
                                        i.putExtra("chatId", Profile.getCurrentProfile().getId() + model.getId());
                                        i.putExtra("new", true);
                                        i.putExtra("id", institudeID);
                                        startActivity(i);
                                    }
                                //}

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });
                }
            //}
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }
}
