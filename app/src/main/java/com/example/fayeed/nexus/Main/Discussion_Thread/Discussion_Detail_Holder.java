package com.example.fayeed.nexus.Main.Discussion_Thread;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.fayeed.nexus.R;

public class Discussion_Detail_Holder extends RecyclerView.ViewHolder{

    View view_D;

    public Discussion_Detail_Holder(View context) {
        super(context);
        view_D = context;
    }


    public void setUserName(String userName){
        TextView text = (TextView) view_D.findViewById(R.id.username);
        text.setText(userName);
    }

    public void setDetailMsg (String detailMsg){
        TextView text = (TextView) view_D.findViewById(R.id.detailmsg);
        text.setText(detailMsg);
    }

    public void setDate (String date){
        TextView text = (TextView) view_D.findViewById(R.id.date);
        text.setText(date);
    }
}
