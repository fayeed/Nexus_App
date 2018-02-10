package com.example.fayeed.nexus.Main.Login;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.fayeed.nexus.Main.GraphEventTrigger;
import com.example.fayeed.nexus.Main.Main;
import com.example.fayeed.nexus.Main.Profile.Edit_Profile;
import com.example.fayeed.nexus.R;
import com.example.fayeed.nexus.Tables.AWS.IdTableDO;
import com.example.fayeed.nexus.Tables.AWS.UserTableDO;
import com.example.fayeed.nexus.Tables.Firebase.AdminUser;
import com.example.fayeed.nexus.Tables.Firebase.Chat_All_Table;
import com.example.fayeed.nexus.Tables.Firebase.IdTable;
import com.example.fayeed.nexus.Tables.Firebase.User;
import com.example.fayeed.nexus.Tables.Firebase.UserMeta;
import com.example.fayeed.nexus.Tables.Firebase.UserTable;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.propertyName;

public class Login extends AppCompatActivity {


    CallbackManager callbackManager;
    LoginButton loginButton;
    public static CognitoCachingCredentialsProvider credentialsProvider;
    public static AmazonDynamoDBClient ddbClient;
    public static DynamoDBMapper mapper;
    public static UserTableDO user_G = null;
    UserTableDO user_P = null;
    IdTable id;
    Profile userProfile;
    AmazonClientException lastException = null;
    String rId;
    int noUser;
    public static SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static String a = al();
    UserTable userTable;
    String institudeID;
    String inst;
    boolean clicked;
    ImageButton login;
    View singup;
    View icon;
    View subIcon;
    GraphEventTrigger graphEventTrigger;
    long pressed;
    Chat_All_Table chat_all_table;
    UserTable user;
    boolean h, m, l;

    public static String al(){
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        setContentView(R.layout.activity_login);
        facebookLogin();

        clicked = false;
        singup = findViewById(R.id.signup);
        icon = findViewById(R.id.loginicon);
        subIcon = findViewById(R.id.subicon);
        login = (ImageButton) findViewById(R.id.login);

        ImageButton register = (ImageButton) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clicked){
                    loginButton.performClick();
                    moveUp();
                } else{

                    userClick();
                }
            }
        });

        ImageButton login = (ImageButton) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void moveUp(){
        ObjectAnimator signupAnimation = ObjectAnimator.ofFloat(singup, "translationY", 650, 0);
        ObjectAnimator iconAnimation = ObjectAnimator.ofFloat(icon, "translationY", 0, -600);
        ObjectAnimator subIconAnimation = ObjectAnimator.ofFloat(subIcon, "translationY", 0, -600);
        login.setVisibility(View.INVISIBLE);
        signupAnimation.setDuration(1000);
        iconAnimation.setDuration(1000);
        subIconAnimation.setDuration(1000);
        signupAnimation.start();
        iconAnimation.start();
        subIconAnimation.start();
        clicked = true;
    }

    public void initialize (){
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:59bbc61d-d110-44ad-87ce-c383310bfe6b",
                Regions.US_EAST_1
        );

        userProfile = Profile.getCurrentProfile();
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = prefs.edit();

        //Checks whether a Accesstoken is available right now
        if(userProfile != null){
            Map<String, String> logins = new HashMap<>();
            logins.put("graph.facebook.com", AccessToken.getCurrentAccessToken().getToken());
            credentialsProvider.setLogins(logins);
            if(!prefs.contains("id")){
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admin").child("UserTable")
                        .child(Profile.getCurrentProfile().getId().toString());
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userTable = dataSnapshot.getValue(UserTable.class);
                        if(userTable != null){
                            institudeID = userTable.getInstitudeId();
                            editor.putString("id",institudeID);
                            editor.putString("Role", userTable.getRole());
                            editor.commit();
                            inst = institudeID;
                            graphEventTrigger = new GraphEventTrigger(inst, getApplicationContext());
                            graphEventTrigger.loggedIn(); //triggers the graph events
                            Intent i = new Intent(Login.this, Main.class);
                            i.putExtra("id",inst);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                ref.addListenerForSingleValueEvent(valueEventListener);
                // while its checking show logo then display login
            } else {
                prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
                editor = prefs.edit();
                inst = prefs.getString("id", null);
                Log.i("LOG", "login" + inst);
                graphEventTrigger = new GraphEventTrigger(inst, this);
                graphEventTrigger.loggedIn(); //triggers the graph events
                Intent i = new Intent(Login.this, Main.class);
                i.putExtra("id",inst);
                startActivity(i);
                finish();
            }
        }
    }

    //Will not be called if the initialize method gets the user
    public void facebookLogin(){
        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Map<String, String> logins = new HashMap<>();
                        logins.put("graph.facebook.com", AccessToken.getCurrentAccessToken().getToken());
                        credentialsProvider.setLogins(logins);

                        userProfile = Profile.getCurrentProfile();

                        Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();

                        retrieveRegisteredUserData();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void retrieveRegisteredUserData(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admin")
                .child("UserTable").child(Profile.getCurrentProfile().getId().toString());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserTable user = dataSnapshot.getValue(UserTable.class);
                if(user == null){
                    Log.e("LOG_TAG", "NO USER IN");
                    moveUp();
                    Toast.makeText(getApplicationContext(),"not Registered", Toast.LENGTH_SHORT).show();
                    //User will be poped to add inst Id and role id
                } else {
                    if(!prefs.contains("id")){
                        if (user != null) {
                            institudeID = user.getInstitudeId();
                            editor.putString("id", institudeID);
                            editor.putString("Role", user.getRole());
                            editor.commit();
                            inst = institudeID;
                            graphEventTrigger = new GraphEventTrigger(inst, getApplicationContext());
                            graphEventTrigger.loggedIn(); //triggers the graph events
                            Intent i = new Intent(Login.this, Main.class);
                            i.putExtra("id", inst);
                            startActivity(i);
                            finish();
                        }
                    }
                Intent i = new Intent(Login.this, Main.class);
                institudeID = user.getInstitudeId();
                graphEventTrigger = new GraphEventTrigger(institudeID, getApplicationContext());
                graphEventTrigger.loggedIn(); //triggers the graph events
                i.putExtra("id", institudeID);
                Log.e("LOG_TAG", "USER IN");
                startActivity(i);
                finish();
            }
                    /*Intent i = new Intent(Login.this, Main.class);
                    institudeID = user.getInstitudeId();
                    graphEventTrigger = new GraphEventTrigger(institudeID, getApplicationContext());
                    graphEventTrigger.loggedIn(); //triggers the graph events
                    i.putExtra("id", institudeID);
                    Log.e("LOG_TAG", "USER IN");
                    startActivity(i);
                    finish();*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    public void registerUser(){

        EditText institudeIdBox = (EditText) findViewById(R.id.institude_Id);
        EditText roleIdBox = (EditText) findViewById(R.id.role_Id);
        final String institudeId = institudeIdBox.getText().toString();
        final String roleId = roleIdBox.getText().toString();
        rId = roleId;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admin").child("Institude")
                .child("Ids").child(institudeId);


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                id = dataSnapshot.getValue(IdTable.class);
                if (id != null) {
                    if (id.getNoUsers() <= 50) {
                        //if (id.getLId() == roleId || id.getMId() == roleId || id.getHId() == roleId) {
                        Log.i("LOG", id.getLId() + id.getMId() + id.getHId());
                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Admin").child("Institude")
                                .child(institudeId);
                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Admin").child("UserTable")
                                .child(Profile.getCurrentProfile().getId().toString());

                        user = new UserTable(Profile.getCurrentProfile().getId().toString(), institudeId, "India", roleId, "Low");
                        UserMeta meta = new UserMeta(Profile.getCurrentProfile().getId());
                        if (rId.equals(id.getLId())) {
                            chat_all_table = new Chat_All_Table(Profile.getCurrentProfile().getFirstName(),
                                    Profile.getCurrentProfile().getId().toString(), "Low");
                            user = new UserTable(Profile.getCurrentProfile().getId().toString(), institudeId, "India", roleId, "Low");
                        } else if (rId.equals(id.getMId())) {
                            chat_all_table = new Chat_All_Table(Profile.getCurrentProfile().getFirstName(),
                                    Profile.getCurrentProfile().getId().toString(), "Medium");
                            user = new UserTable(Profile.getCurrentProfile().getId().toString(), institudeId, "India", roleId, "Medium");
                        } else if (rId.equals(id.getHId())) {
                            chat_all_table = new Chat_All_Table(Profile.getCurrentProfile().getFirstName(),
                                    Profile.getCurrentProfile().getId().toString(), "High");
                            user = new UserTable(Profile.getCurrentProfile().getId().toString(), institudeId, "India", roleId, "High");
                        }
                        id.setNoUsers(id.getNoUsers() + 1);
                        id.setHId(id.getHId());
                        id.setMId(id.getMId());
                        id.setLId(id.getLId());
                        ref2.setValue(id);
                        ref1.setValue(user);
                        ref1 = FirebaseDatabase.getInstance().getReference().child(institudeId).child("User").child("meta")
                                .child(Profile.getCurrentProfile().getFirstName());
                        ref1.setValue(meta);
                        ref1 = FirebaseDatabase.getInstance().getReference().child(institudeId).child("User").child("meta")
                                .child(Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName());
                        ref1.setValue(meta);
                        ref1 = FirebaseDatabase.getInstance().getReference().child(institudeId).child("Chat").child("List")
                                .child(Profile.getCurrentProfile().getId());
                        ref1.setValue(chat_all_table);
                        ref1 = FirebaseDatabase.getInstance().getReference().child(institudeId).child("Admin").child("Usertable")
                                .child(Profile.getCurrentProfile().getId());
                        AdminUser adminUser = new AdminUser(Profile.getCurrentProfile().getName(), Profile.getCurrentProfile().getId(),
                                DateFormat.getDateTimeInstance().format(new Date()), AdminUser.ONLINE, AdminUser.NEWS, 0, 0, 0,
                                AdminUser.NOTBANNED);
                        ref1.setValue(adminUser);
                        editor.putString("id", institudeId);
                        editor.putBoolean("Bhhhooo_lId", true);
                        editor.putBoolean("Bhhhooo_mId", true);
                        editor.putBoolean("Bhhhooo_hId", true);
                        editor.putBoolean("chat_lId", true);
                        editor.putBoolean("chat_mId", true);
                        editor.putBoolean("chat_hId", true);
                        editor.commit();
                        if (rId.equals(id.getLId())) {
                            editor.putString("Role", "Low");
                            editor.commit();
                        } else if (rId.equals(id.getMId())) {
                            editor.putString("Role", "Medium");
                            editor.commit();
                        } else if (rId.equals(id.getHId())) {
                            editor.putString("Role", "High");
                            editor.commit();
                        }
                        //editor.commit();

                        graphEventTrigger = new GraphEventTrigger(institudeId, getApplicationContext());
                        graphEventTrigger.loggedIn(); //triggers the graph events

                        Intent i = new Intent(Login.this, Main.class);
                        i.putExtra("id", institudeId);
                        i.putExtra("register", true);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Already exceeded the user limit", Toast.LENGTH_LONG).show();
                    }
                    //}
                } else {
                    Toast.makeText(getApplicationContext(), "No such Institude exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);


    }

    public void userClick(){
        registerUser();
        if(id == null){
            //Toast.makeText(getApplicationContext(), "No such institude exist", Toast.LENGTH_SHORT).show();
            //registerUser();
        }
    }

    @Override
    public void onBackPressed(){
        if (pressed + 2000 > System.currentTimeMillis()){

            super.onBackPressed();
        }
        else{
            if(clicked){
                //Translate the object back to its previous position
                ObjectAnimator signupAnimation = ObjectAnimator.ofFloat(singup, "translationY", 650, 0);
                ObjectAnimator iconAnimation = ObjectAnimator.ofFloat(icon, "translationY", 0, -600);
                ObjectAnimator subIconAnimation = ObjectAnimator.ofFloat(subIcon, "translationY", 0, -600);
                login.setVisibility(View.VISIBLE);
                signupAnimation.setDuration(1000);
                iconAnimation.setDuration(1000);
                subIconAnimation.setDuration(1000);
                signupAnimation.reverse();
                iconAnimation.reverse();
                subIconAnimation.reverse();
                LoginManager.getInstance().logOut();
                clicked = false;
            }
            Toast.makeText(getBaseContext(), "Press back again to exit!", Toast.LENGTH_SHORT).show();
            pressed = System.currentTimeMillis();
        }
    }
}
