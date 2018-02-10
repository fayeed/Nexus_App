package com.example.fayeed.nexus.Main;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fayeed.nexus.Main.BHHHOOO.BHHOOO_Main;
import com.example.fayeed.nexus.Main.Chat.Chat_Main;
import com.example.fayeed.nexus.Main.Chat.Chat_Messaging;
import com.example.fayeed.nexus.Main.Discussion_Thread.Discussion_Main;
import com.example.fayeed.nexus.Main.Login.Login;
import com.example.fayeed.nexus.Main.News.News_Main;
import com.example.fayeed.nexus.Main.Profile.Edit_Profile;
import com.example.fayeed.nexus.Main.Profile.UserProfile;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.Firebase.AdminReport;
import com.example.fayeed.nexus.Tables.Firebase.AdminUser;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Main extends AppCompatActivity {
    //String institudeID;

    String institudeID;
    Bundle bundle;
    PagerAdapter adapterViewPager;
    DatabaseReference reference;
    AdminUser adminUser;
    String a;
    long start, end;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private class ScreenSlidePagerAdapter extends PagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new BHHOOO_Main();
                case 1:
                    return new Chat_Main();
                case 2:
                    return new UserProfile();
                case 3:
                    return new News_Main();
                case 4:
                    return new Discussion_Main();
                default:
                    return new BHHOOO_Main();
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adminUser = new AdminUser();

        institudeID = getIntent().getStringExtra("id");
        Log.i("LOG", "sp" + institudeID);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        institudeID = sharedPref.getString("id", null);
        //a = getIntent().getStringExtra("Role");
        a = sharedPref.getString("Role", null);
        //Log.i("LOG", "role"+a);
        //TableUpdater tableUpdater = new TableUpdater(institudeID);

        start = Calendar.getInstance().getTimeInMillis();
        reference = FirebaseDatabase.getInstance().getReference().child(institudeID).child("Admin").child("Usertable")
                .child(Profile.getCurrentProfile().getId());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adminUser = dataSnapshot.getValue(AdminUser.class);
                if(adminUser == null) {
                    Log.i("LOG", "No data was acquired");
                } else {
                    //adminUser.setStatus(AdminUser.ONLINE);
                    //adminUser.setCurrentFeature(AdminUser.NEWS);
                    //adminUser.setBanned(AdminUser.NOTBANNED);
                    //adminUser.setStrikes(0);
                    Log.i("LOG", "strikes are : " + adminUser.getName());
                    if(adminUser.getStrikes() > 0){
                        Toast.makeText(getApplicationContext(), "You have received " + adminUser.getStrikes(), Toast.LENGTH_SHORT).show();
                        StrikesCustomDialogBox strikesCustomDialogBox = new StrikesCustomDialogBox(Main.this, adminUser.getStrikes());
                        strikesCustomDialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        strikesCustomDialogBox.setCanceledOnTouchOutside(false);
                        strikesCustomDialogBox.show();
                    } else if(adminUser.isBanned()){
                        Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();
                        BannedCustomDialogBox bannedCustomDialogBox = new BannedCustomDialogBox(Main.this);
                        bannedCustomDialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        bannedCustomDialogBox.setCanceledOnTouchOutside(false);
                        bannedCustomDialogBox.show();
                    }
                    Log.i("LOG", "strikes" + adminUser.getStrikes());
                    //reference.setValue(adminUser);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        adminUser = dataSnapshot.getValue(AdminUser.class);
                        if(adminUser == null) {
                            Log.i("LOG", "No data was acquired");
                        } else {
                            if(position == 0){
                                adminUser.setStatus(AdminUser.ONLINE);
                                adminUser.setCurrentFeature(AdminUser.BHHHOOO);
                                reference.setValue(adminUser);
                            } else if(position == 1){
                                adminUser.setStatus(AdminUser.ONLINE);
                                adminUser.setCurrentFeature(AdminUser.CHAT);
                                reference.setValue(adminUser);
                            } else if(position == 2){
                                adminUser.setStatus(AdminUser.ONLINE);
                                adminUser.setCurrentFeature(AdminUser.PROFILE);
                                reference.setValue(adminUser);
                            } else if(position == 3){
                                adminUser.setStatus(AdminUser.ONLINE);
                                adminUser.setCurrentFeature(AdminUser.NEWS);

                                reference.setValue(adminUser);
                            } else if(position == 4){
                                adminUser.setStatus(AdminUser.ONLINE);
                                adminUser.setCurrentFeature(AdminUser.DISCUSSION_THREAD);
                                reference.setValue(adminUser);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPager.setAdapter(mPagerAdapter);

        if(getIntent().getBooleanExtra("register", false)){
            Intent i = new Intent(Main.this, Edit_Profile.class);
            i.putExtra("id", institudeID);
            i.putExtra("register", true);
            startActivity(i);
        } else {
            mPager.setCurrentItem(3);
        }


        ImageButton profile = (ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(2);
            }
        });

        ImageButton discussion = (ImageButton) findViewById(R.id.discussionthread);
        discussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(4);
            }
        });

        ImageButton news = (ImageButton) findViewById(R.id.news);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(3);
            }
        });

        ImageButton bhhooo = (ImageButton) findViewById(R.id.bhhhooo);
        bhhooo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });

        ImageButton chat = (ImageButton) findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(1);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();




    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        destroy();
    }

    public void destroy(){
        end = Calendar.getInstance().getTimeInMillis();
        long total = TimeUnit.MILLISECONDS.toMinutes(end - start);
        Log.i("LOG", "Total : "+total);
        adminUser.setStatus(AdminUser.OFFLINE);
        adminUser.setCurrentFeature(null);
        adminUser.setTotalTimeUsed(adminUser.getTotalTimeUsed() + total);
        reference.setValue(adminUser);
        //total = 0;
    }

    @Override
    public void onPause(){
        super.onPause();
        destroy();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        adminUser.setStatus(AdminUser.ONLINE);
        adminUser.setCurrentFeature(AdminUser.NEWS);
        //adminUser.setBanned(AdminUser.NOTBANNED);
        //adminUser.setStrikes(0);
        Log.i("LOG", "strikes" + adminUser.isBanned());
        reference.setValue(adminUser);
    }
}
