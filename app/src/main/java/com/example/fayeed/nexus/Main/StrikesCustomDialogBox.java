package com.example.fayeed.nexus.Main;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.fayeed.nexus.R;

public class StrikesCustomDialogBox extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Button yes, no;
    public TextView t;
    public long s;

    public StrikesCustomDialogBox(Activity a, long strikes) {
        super(a);
        this.c = a;
        this.s = strikes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdailogbox_strikes);
        t = (TextView) findViewById(R.id.cont);
        yes = (Button) findViewById(R.id.ok);
        yes.setOnClickListener(this);
        t.setText("You have received " + s + "/3 strikes due to inappropiate comments or spamming the network multiple strikes " +
                "may result in getting banned");
        //graphEventTrigger = new GraphEventTrigger(institudeId, getApplicationContext());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                    dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void ok(){

    }
}
