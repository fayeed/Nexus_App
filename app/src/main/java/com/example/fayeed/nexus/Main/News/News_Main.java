package com.example.fayeed.nexus.Main.News;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.fayeed.nexus.Tables.Firebase.News;
import com.facebook.Profile;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


public class News_Main extends Fragment {

    FirebaseListAdapter mAdapter;
    FirebaseRecyclerAdapter rAdapter;
    String institudeID;
    String role;
    GraphEventTrigger graphEventTrigger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_news, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final SharedPreferences sharedPref = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        institudeID = sharedPref.getString("id", null);
        role = sharedPref.getString("Role", null);
        Log.i("LOG", "ID "+ institudeID);
        Log.i("LOG", "Role "+role);

        if(role.equals("Low")){
            View view = getActivity().findViewById(R.id.newspost);
            view.setVisibility(View.INVISIBLE);
            Log.i("LOG", "Complete");
        }

        graphEventTrigger = new GraphEventTrigger(institudeID, getActivity().getApplicationContext());
        graphEventTrigger.newsIn();
        graphEventTrigger.loggedIn();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("news");

        final RecyclerView recycler = (RecyclerView) getActivity().findViewById(R.id.news);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(linearLayoutManager);

        rAdapter = new FirebaseRecyclerAdapter<News, News_M_Holder>(News.class, R.layout.newsrecy, News_M_Holder.class, ref) {
            @Override
            public void populateViewHolder(News_M_Holder newsViewHolder, final News newsMessage, int position) {

                String s = newsMessage.getDetail();
                //Log.i("LOG", "xc" + s);
                    if (s.length() < 190) {
                        newsViewHolder.setContent(s);
                    } else {
                        s = s.substring(0, 180);
                        s += "...";
                        newsViewHolder.setContent(s);
                    }
                newsViewHolder.setName(newsMessage.getUser());
                newsViewHolder.setTopic(newsMessage.getTopic());
                newsViewHolder.setProPic(newsMessage.getId());
                newsViewHolder.setView(newsMessage.getView());
                //newsViewHolder.setContent(s);
                newsViewHolder.setTime(newsMessage.getTimestamp());
                newsViewHolder.setPrioirity(newsMessage.getPriority());
                //Log.i("LOG", "xc" +newsMessage.getPriority());
                if(!newsMessage.isTargetRoleL() && role.equals("Low") || !newsMessage.isTargetRoleM() && role.equals("Medium")
                        || !newsMessage.isTargetRoleH() && role.equals("High")){
                    if(!newsMessage.getId().equals(Profile.getCurrentProfile().getId())) {
                        Log.i("LOG", "dis");
                        newsViewHolder.hide();
                    }
                }

                newsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newsMessage.setView(newsMessage.getView() + 1);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("news")
                                .child(newsMessage.getKey());
                        ref.setValue(newsMessage);

                        Intent i = new Intent(getActivity(), News_Detail.class);
                        i.putExtra("id", institudeID);
                        i.putExtra("topic", newsMessage.getTopic());
                        i.putExtra("user", newsMessage.getUser());
                        i.putExtra("detail", newsMessage.getDetail());
                        i.putExtra("timestamp", newsMessage.getTimestamp());
                        i.putExtra("priority", newsMessage.getPriority());
                        startActivity(i);

                    }
                });
            }
        };
        //recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).color(Color.BLACK)
        //        .sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        recycler.setAdapter(rAdapter);

        ImageButton imageButton = (ImageButton) getActivity().findViewById(R.id.newspost);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getActivity(), News_Post.class);
                i.putExtra("id", institudeID);
                startActivity(i);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rAdapter.cleanup();
    }
}
