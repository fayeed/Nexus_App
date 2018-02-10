package com.example.fayeed.nexus.Main.Discussion_Thread;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.AdminReport;
import com.example.fayeed.nexus.Tables.Firebase.AdminUser;
import com.example.fayeed.nexus.Tables.Firebase.Discussion;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ReportCustomDialogBox_Discussion extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Button yes, no;
    public String institudeId;
    public String key;
    public String category;
    public EditText editText;
    public Discussion model;
    Spinner spinner;
    GraphEventTrigger graphEventTrigger;
    String role;

    public ReportCustomDialogBox_Discussion(Activity a, String inst, String role, Discussion bhhhooo) {
        super(a);
        this.c = a;
        this.institudeId = inst;
        this.model = bhhhooo;
        this.role = role;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdailogbox_report_b);
        editText = (EditText) findViewById(R.id.editText);
        spinner = (Spinner) findViewById(R.id.category);
        yes = (Button) findViewById(R.id.ok);
        no = (Button) findViewById(R.id.can);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        //graphEventTrigger = new GraphEventTrigger(institudeId, getApplicationContext());
        List<String> categories = new ArrayList<>();
        categories.add("It's spam");
        categories.add("Inappropriate Comment");
        categories.add("Others");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_textview, categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                category = item;
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                post();
                break;
            case R.id.can:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void post(){
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(institudeId)
                .child("Admin").child("Usertable").child(model.getId());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AdminUser adminUser = dataSnapshot.getValue(AdminUser.class);
                adminUser.setReport(adminUser.getReport() + 1);
                reference.setValue(adminUser);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(institudeId)
                        .child("Admin").child("Reports").child(model.getId());
                AdminReport adminReport = new AdminReport(Profile.getCurrentProfile().getId(),
                        model.getId(),Profile.getCurrentProfile().getName(), model.getCreator(), editText.getText().toString(), category,
                        false);
                ref.push().setValue(adminReport);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
