package com.example.fayeed.nexus.Main;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.fayeed.nexus.R;

public class BannedCustomDialogBox extends Dialog implements android.view.View.OnClickListener{
    public Activity c;
    public Button yes, no;

    public BannedCustomDialogBox(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdialogbox_banned);
        yes = (Button) findViewById(R.id.accept);
        no = (Button) findViewById(R.id.can);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        //graphEventTrigger = new GraphEventTrigger(institudeId, getApplicationContext());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                c.finish();
                break;
            case R.id.can:
                c.finish();
                break;
            default:
                break;
        }
        dismiss();
    }
}
