package com.example.fayeed.nexus.Main.News;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fayeed.nexus.R;

public class News_Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__detail);
    }

    @Override
    public void onStart() {
        super.onStart();
        initialize();
    }

    void initialize(){
        //Bundle bundle = this.getArguments();
        TextView topic = (TextView) findViewById(R.id.news_title);
        TextView detail = (TextView) findViewById(R.id.news_detail);
        TextView name = (TextView) findViewById(R.id.news_name);
        TextView timestamp = (TextView) findViewById(R.id.news_timestamp);

        topic.setText(getIntent().getStringExtra("topic"));
        detail.setText(getIntent().getStringExtra("detail"));
        name.setText("  " + getIntent().getStringExtra("user"));
        timestamp.setText(getIntent().getStringExtra("timestamp") + "  ");

        ImageButton imageButton = (ImageButton) findViewById(R.id.newsdback);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack();
                finish();
            }
        });
    }
}
