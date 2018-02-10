package com.example.fayeed.nexus.Main.BHHHOOO;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.BHHHOOO;
import com.example.fayeed.nexus.Tables.Firebase.User;
import com.facebook.Profile;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

public class BHHOOO_Main extends Fragment {

    FirebaseRecyclerAdapter Adapter;
    DatabaseReference ref;
    DatabaseReference ref1;
    FirebaseStorage storage;
    StorageReference storageRef;
    User user;
    String key;
    String institudeID;
    String role;
    GraphEventTrigger graphEventTrigger;
    boolean bhhhooo_lId;
    boolean bhhhooo_mId;
    boolean bhhhooo_hId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_bhhooo__m, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        SharedPreferences sharedPref = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        institudeID = sharedPref.getString("id", null);
        role = sharedPref.getString("Role", null);
        Log.i("LOG", "" +role);
        bhhhooo_lId = sharedPref.getBoolean("Bhhhooo_lId", true);
        bhhhooo_mId = sharedPref.getBoolean("Bhhhooo_mId", true);
        bhhhooo_hId = sharedPref.getBoolean("Bhhhooo_hId", true);
        Log.i("LOG", "bhhhs" +bhhhooo_lId);

        graphEventTrigger = new GraphEventTrigger(institudeID, getActivity().getApplicationContext());
        graphEventTrigger.bhhhoooIn();

        ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("BHHHOOO").child("All").child("meta");
        ref1 = FirebaseDatabase.getInstance().getReference().child(institudeID).child("User")
                .child("Detail").child(Profile.getCurrentProfile().getId().toString());

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.bhhhooo_m);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://nexus-f4ba4.appspot.com");

        Adapter =  new FirebaseRecyclerAdapter<BHHHOOO, BHHHOOO_Main_Holder>(BHHHOOO.class, R.layout.bhhhooo_mrecy,
                BHHHOOO_Main_Holder.class, ref) {
            @Override
            protected void populateViewHolder(BHHHOOO_Main_Holder viewHolder, final BHHHOOO model, int position) {
                    viewHolder.setName(model.getName());
                    viewHolder.setContent(model.getDesc());
                    viewHolder.setView(model.getView());
                    viewHolder.setComments(model.getComments());
                    viewHolder.setTimeStamp(model.getTimestamp());
                    viewHolder.setProPic(model.getId());
                    if(model.getRole().equals("Low") && bhhhooo_lId == false || model.getRole().equals("Medium")
                            && bhhhooo_mId == false || model.getRole().equals("High") && bhhhooo_hId == false){
                        viewHolder.hide();
                    }
                    viewHolder.v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            model.setView(model.getView() + 1);
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("BHHHOOO")
                                    .child("All").child("meta").child(model.getKey());
                            ref.setValue(model);
                            Intent i = new Intent(getActivity(), BHHHOOO_Comment.class);
                            i.putExtra("name", model.getName());
                            i.putExtra("desc", model.getDesc());
                            i.putExtra("timestamp", model.getTimestamp());
                            i.putExtra("bhhooId", model.getId());
                            i.putExtra("key", model.getKey());
                            i.putExtra("id", institudeID);
                            startActivity(i);
                        }
                    });
                    viewHolder.v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ReportCustomDialogBox_BHHHOOO reportCustomDialogBoxBHHHOOO = new ReportCustomDialogBox_BHHHOOO(getActivity(), institudeID,
                                    role, model);
                            reportCustomDialogBoxBHHHOOO.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            reportCustomDialogBoxBHHHOOO.show();
                            return true;
                        }
                    });
                }
        };
        //recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).color(Color.BLACK)
        //        .sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        recyclerView.setAdapter(Adapter);

        ImageButton post = (ImageButton) getActivity().findViewById(R.id.bhhoopost);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BCustomDialogClass cdd=new BCustomDialogClass(getActivity(), institudeID, role);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });
    }
}
