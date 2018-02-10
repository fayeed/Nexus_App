package com.example.fayeed.nexus.Main.Discussion_Thread;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.Discussion;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

public class Discussion_Main extends Fragment {

    FirebaseRecyclerAdapter rAdapter;
    String institudeID;
    GraphEventTrigger graphEventTrigger;
    String role;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_discussion__m, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        final SharedPreferences sharedPref = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        institudeID = sharedPref.getString("id", null);
        role = sharedPref.getString("Role", null);

        graphEventTrigger = new GraphEventTrigger(institudeID, getActivity().getApplicationContext());
        graphEventTrigger.discussionIn();

        ImageButton imageButton = (ImageButton) getActivity().findViewById(R.id.discussionpost);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Bundle bundle = new Bundle();
                bundle.putString("id",institudeID);
                Discussion_Post newFragment = new Discussion_Post();
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragement_container, newFragment);
                transaction.commit();*/
                Intent i = new Intent(getActivity(), Discussion_Post.class);
                i.putExtra("id", institudeID);
                startActivity(i);
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("discussion")
                .child("All").child("meta");

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.discussion);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        rAdapter =  new FirebaseRecyclerAdapter<Discussion, Discussion_Main_Holder>(Discussion.class, R.layout.discussionrecy,
                Discussion_Main_Holder.class, ref) {
            @Override
            protected void populateViewHolder(Discussion_Main_Holder discussionViewHolder, final Discussion model, final int position) {
                discussionViewHolder.setTpoic(model.getTopic());
                discussionViewHolder.setName(model.getCreator());
                discussionViewHolder.setNoOfThreads(model.getNoOfThreads());
                discussionViewHolder.setView(model.getView());
                discussionViewHolder.setSmallDesc(model.getDesc());
                discussionViewHolder.setTime(model.getTimestamp());
                discussionViewHolder.setNoOfThreads(model.getNoOfThreads());
                discussionViewHolder.setDImage(model.getImageLocation());

                discussionViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity().getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
                        /*Bundle bundle = new Bundle();
                        bundle.putString("topic", model.getTopic());
                        bundle.putString("smallDesc", model.getDesc());
                        bundle.putString("timestamp", model.getTimestamp());
                        bundle.putDouble("noOfThreads", model.getNoOfThreads());
                        bundle.putString("imageLocation", model.getImageLocation());
                        bundle.putBoolean("closed", model.getClosed());
                        bundle.putString("creator", model.getCreator());
                        bundle.putString("key", model.getKey());
                        bundle.putString("id",institudeID);
                        Discussion_Detail newFragment = new Discussion_Detail();
                        newFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.fragement_container, newFragment);
                        transaction.commit();*/

                        model.setView(model.getView() + 1);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("discussion")
                                .child("All").child("meta").child(model.getKey());
                        ref.setValue(model);
                        Intent i = new Intent(getActivity(), Discussion_Detail.class);
                        i.putExtra("topic", model.getTopic());
                        i.putExtra("view", model.getView());
                        i.putExtra("smallDesc", model.getDesc());
                        i.putExtra("timestamp", model.getTimestamp());
                        i.putExtra("noOfThreads", model.getNoOfThreads());
                        i.putExtra("imageLocation", model.getImageLocation());
                        i.putExtra("closed", model.getClosed());
                        i.putExtra("creator", model.getCreator());
                        i.putExtra("key", model.getKey());
                        i.putExtra("id", institudeID);
                        startActivity(i);

                    }
                });

                discussionViewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        ReportCustomDialogBox_Discussion reportCustomDialogBoxDiscussion = new ReportCustomDialogBox_Discussion(getActivity(), institudeID,
                                role, model);
                        reportCustomDialogBoxDiscussion.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        reportCustomDialogBoxDiscussion.show();
                        return true;
                    }
                });
            }
        };
        //recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).color(Color.BLACK)
         //       .sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        recyclerView.setAdapter(rAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rAdapter.cleanup();
    }
}
