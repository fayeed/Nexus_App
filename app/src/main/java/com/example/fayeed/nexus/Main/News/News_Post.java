package com.example.fayeed.nexus.Main.News;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.News;
import com.facebook.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class News_Post extends AppCompatActivity {

    EditText title;
    EditText detail;
    String institudeID;
    Spinner spinner;
    String priority;
    GraphEventTrigger graphEventTrigger;
    Switch sl, sm, sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_post);
    }

    @Override
    public void onStart() {
        super.onStart();

        //Bundle bundle;
        //bundle = this.getArguments();
        institudeID = getIntent().getStringExtra("id");

        graphEventTrigger = new GraphEventTrigger(institudeID, getApplicationContext());

        title = (EditText) findViewById(R.id.title_NP);
        detail = (EditText) findViewById(R.id.detail_N1);
        final Button button = (Button) findViewById(R.id.ok);
        sl = (Switch) findViewById(R.id.n_p_low);
        sm = (Switch) findViewById(R.id.n_p_medium);
        sh = (Switch) findViewById(R.id.n_p_high);

        spinner = (Spinner) findViewById(R.id.spinner);

        List<String> priorities = new ArrayList<>();
        priorities.add("Low");
        priorities.add("Medium");
        priorities.add("High");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_textview, priorities);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                priority = item;
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = title.getText().toString();
                String s1 = detail.getText().toString();
                boolean l = sl.isChecked();
                boolean m = sm.isChecked();
                boolean h = sh.isChecked();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeID).child("news");
                String k = ref.push().getKey();
                News msg = new News(s, s1, Profile.getCurrentProfile().getFirstName() + " " +
                        Profile.getCurrentProfile().getLastName(), Profile.getCurrentProfile().getId(), priority
                        , l , m, h, k);

                ref.child(k).setValue(msg);
                graphEventTrigger.dNewsPosted();
                /*Bundle bundle = new Bundle();
                bundle.putString("id", institudeID);
                News_Main newFragment = new News_Main();
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //transaction.addToBackStack(null);
                transaction.add(R.id.fragement_container, newFragment);
                transaction.commit();*/
                finish();
            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.newspback);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack();
                finish();
            }
        });
    }
}
