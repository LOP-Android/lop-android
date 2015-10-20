package com.example.karen.lop_android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends Activity {

    /**/
    private View.OnClickListener onClickLogin;
    private View.OnClickListener onClickRegstr;

    private LinearLayout layout;
    private LinearLayout layoutReg;
    private LinearLayout.LayoutParams lparams;
    private LinearLayout.LayoutParams lparams2;
    private EditText user;
    private EditText pass;
    private Button submitBtn;
    private Button jsontest;
    private TextView tv;
    private ImageView im;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private TextView registerBtn;
    /**/

    private boolean userReg = false;
    private Animation anim;

    private Informatron r;
    private String urlInformatron = "http://172.31.11.32:24119/InformatronYX/informatron/user/login";
    public static UserSession userSession;

    //private String username:

    public UserSession getUser(){
        return this.userSession;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //initialize animation utility for usability in any edit text
        initAnimationUtils();

        inflateViews(false);

        onClickRegstr = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                r = new Informatron();
                //set on click registerBtn to post json to informatron
                try {
                    r.setRegisterUrl(SettingsFragment.URL_Register);
                    r.registerUser(getApplicationContext(),
                            user.getText().toString(),
                            pass.getText().toString(),
                            email.getText().toString(),
                            firstName.getText().toString(),
                            lastName.getText().toString());
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "ERROR: "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        this.setContentView(layout);
    }

    public void initAnimationUtils(){
        anim = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        anim.setInterpolator(getApplicationContext(), android.R.anim.bounce_interpolator);
        anim.setDuration(1000);

    }

    //override back press for exiting activity


    @Override
    public void onBackPressed() {
        AlertDialog alertDialog;
        final Activity theActivity = this;


        alertDialog = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit LOP?")
                .setTitle("Leaving so soon?")
                .setIcon(getResources().getDrawable(R.drawable.exit))
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        theActivity.finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();

        alertDialog.show();

        //super.onBackPressed();
    }

    private void getUserJSON(){

    }

    public boolean validateLoginJSON(JSONObject loginJSON) throws JSONException {
        return ((loginJSON.optString("id"))!="null");
    }

    public String getUserId(JSONObject loginJSON) throws JSONException {
        return (loginJSON.optString("id"));
    }

    public static UserSession getUserSession(){
        return userSession;
    }

    //creates submitBtn button
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void createSbmtButton(){
        submitBtn = new Button(this);
        submitBtn.setBackground(getResources().getDrawable(R.drawable.button_states));
        submitBtn.setLayoutParams(lparams2);
        submitBtn.setGravity(Gravity.CENTER_HORIZONTAL);
        submitBtn.setText("SUBMIT");
        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(LoginActivity.this, MenuActivity.class));

                try {
                    r = new Informatron();
                    r.setRegisterUrl(urlInformatron);
                    //JSONObject theJson = new JSONObject(r.loginUser(getApplicationContext(), user.getText().toString(), pass.getText().toString()));
                    JSONObject dummyJson = new JSONObject(" {\n" +
                            "    \"id\": \"5621369344aea2994b852b66\",\n" +
                            "    \"username\": \"princeniko\",\n" +
                            "    \"password\": \"1234\",\n" +
                            "    \"firstName\": \"Prince\",\n" +
                            "    \"lastName\": \"Garces\",\n" +
                            "    \"email\": \" prince.nikogarces@gmail.com\",\n" +
                            "    \"lastLogin\": 1445018693655,\n" +
                            "    \"lastLoginString\": \"10 17, 15\",\n" +
                            "    \"lastDownloadDate\": null,\n" +
                            "    \"lastDownloadId\": null,\n" +
                            "    \"liableLearningObjects\": [\n" +
                            "        {\n" +
                            "            \"id\": \"56202874986cfb81a9ee3cf7\",\n" +
                            "            \"title\": \"Test 3\",\n" +
                            "            \"subject\": null,\n" +
                            "            \"description\": null,\n" +
                            "            \"downloads\": 0,\n" +
                            "            \"uploadDate\": null,\n" +
                            "            \"likes\": 0,\n" +
                            "            \"sequence\": [\n" +
                            "                [\n" +
                            "                    {\n" +
                            "                        \"title\": \"Picture 1\",\n" +
                            "                        \"type\": \"image\",\n" +
                            "                        \"fileExtension\": \".png\",\n" +
                            "                        \"description\": null,\n" +
                            "                        \"dateCreated\": null,\n" +
                            "                        \"id\": \"picture1\"\n" +
                            "                    },\n" +
                            "                    {\n" +
                            "                        \"title\": \"Picture 2\",\n" +
                            "                        \"type\": \"music\",\n" +
                            "                        \"fileExtension\": \".png\",\n" +
                            "                        \"description\": null,\n" +
                            "                        \"dateCreated\": null,\n" +
                            "                        \"id\": \"picture2\"\n" +
                            "                    },\n" +
                            "                    {\n" +
                            "                        \"title\": \"Picture 3\",\n" +
                            "                        \"type\": \"music\",\n" +
                            "                        \"fileExtension\": \".png\",\n" +
                            "                        \"description\": null,\n" +
                            "                        \"dateCreated\": null,\n" +
                            "                        \"id\": \"picture3\"\n" +
                            "                    }\n" +
                            "                ],\n" +
                            "\t\t[\n" +
                            "                    {\n" +
                            "                        \"title\": \"Picture 1_1\",\n" +
                            "                        \"type\": \"image\",\n" +
                            "                        \"fileExtension\": \".png\",\n" +
                            "                        \"description\": null,\n" +
                            "                        \"dateCreated\": null,\n" +
                            "                        \"id\": \"picture1_1\"\n" +
                            "                    },\n" +
                            "                    {\n" +
                            "                        \"title\": \"Picture 1_2\",\n" +
                            "                        \"type\": \"music\",\n" +
                            "                        \"fileExtension\": \".png\",\n" +
                            "                        \"description\": null,\n" +
                            "                        \"dateCreated\": null,\n" +
                            "                        \"id\": \"picture1_2\"\n" +
                            "                    }\n" +
                            "                ]\n" +
                            "            ],\n" +
                            "            \"price\": 0\n" +
                            "        },\n" +
                            "\t{\n" +
                            "            \"id\": \"2\",\n" +
                            "            \"title\": \"Test 2\",\n" +
                            "            \"subject\": null,\n" +
                            "            \"description\": null,\n" +
                            "            \"downloads\": 0,\n" +
                            "            \"uploadDate\": null,\n" +
                            "            \"likes\": 0,\n" +
                            "            \"sequence\": [\n" +
                            "                [\n" +
                            "                    {\n" +
                            "                        \"title\": \"Music Sample1\",\n" +
                            "                        \"type\": \"music\",\n" +
                            "                        \"fileExtension\": \".mp3\",\n" +
                            "                        \"description\": null,\n" +
                            "                        \"dateCreated\": null,\n" +
                            "                        \"id\": \"mp3One.mp3\"\n" +
                            "                    }\n" +
                            "                ],\n" +
                            "\t\t[\n" +
                            "                    {\n" +
                            "                        \"title\": \"Music Sample2\",\n" +
                            "                        \"type\": \"music\",\n" +
                            "                        \"fileExtension\": \".mp3\",\n" +
                            "                        \"description\": null,\n" +
                            "                        \"dateCreated\": null,\n" +
                            "                        \"id\": \"mp3Two.mp3\"\n" +
                            "                    }\n" +
                            "                ]\n" +
                            "            ],\n" +
                            "            \"price\": 0\n" +
                            "        },\n" +
                            "\t{\n" +
                            "            \"id\": \"3\",\n" +
                            "            \"title\": \"Test 1\",\n" +
                            "            \"subject\": null,\n" +
                            "            \"description\": null,\n" +
                            "            \"downloads\": 0,\n" +
                            "            \"uploadDate\": null,\n" +
                            "            \"likes\": 0,\n" +
                            "            \"sequence\": [\n" +
                            "                [\n" +
                            "                    {\n" +
                            "                        \"title\": \"Text 1\",\n" +
                            "                        \"type\": \"music\",\n" +
                            "                        \"fileExtension\": \".txt\",\n" +
                            "                        \"description\": null,\n" +
                            "                        \"dateCreated\": null,\n" +
                            "                        \"id\": \"text1\"\n" +
                            "                    }\n" +
                            "                ],\n" +
                            "\t\t[\n" +
                            "                    {\n" +
                            "                        \"title\": \"Text 2\",\n" +
                            "                        \"type\": \"music\",\n" +
                            "                        \"fileExtension\": \".txt\",\n" +
                            "                        \"description\": null,\n" +
                            "                        \"dateCreated\": null,\n" +
                            "                        \"id\": \"text2\"\n" +
                            "                    }\n" +
                            "                ]\n" +
                            "            ],\n" +
                            "            \"price\": 0\n" +
                            "        },\n" +
                            "\t{\n" +
                            "            \"id\": \"3\",\n" +
                            "            \"title\": \"Test 4\",\n" +
                            "            \"subject\": null,\n" +
                            "            \"description\": null,\n" +
                            "            \"downloads\": 0,\n" +
                            "            \"uploadDate\": null,\n" +
                            "            \"likes\": 0,\n" +
                            "            \"sequence\": [\n" +
                            "                [\n" +
                            "                    {\n" +
                            "                        \"title\": \"Video 1\",\n" +
                            "                        \"type\": \"music\",\n" +
                            "                        \"fileExtension\": \".mp4\",\n" +
                            "                        \"description\": null,\n" +
                            "                        \"dateCreated\": null,\n" +
                            "                        \"id\": \"sampleVideoOne\"\n" +
                            "                    }\n" +
                            "                ],\n" +
                            "\t\t[\n" +
                            "                    {\n" +
                            "                        \"title\": \"Video 2\",\n" +
                            "                        \"type\": \"music\",\n" +
                            "                        \"fileExtension\": \".mp4\",\n" +
                            "                        \"description\": null,\n" +
                            "                        \"dateCreated\": null,\n" +
                            "                        \"id\": \"sampleVideoTwo\"\n" +
                            "                    }\n" +
                            "                ]\n" +
                            "            ],\n" +
                            "            \"price\": 0\n" +
                            "        }\n" +
                            "\t]\n" +
                            "}\n");
                    //if(validateLoginJSON(theJson)){
                        userSession = new UserSession(dummyJson);

                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    //}
                    //else{
                    //    Toast.makeText(getApplicationContext(), "Error: Incorrect username/password", Toast.LENGTH_SHORT).show();
                    //}
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error: "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //create the json test button i delete rani puhon!
        jsontest = new Button(this);
        jsontest.setLayoutParams(lparams);
        jsontest.setGravity(Gravity.CENTER_HORIZONTAL);
        jsontest.setText("go to json test activity");
        jsontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JSONTestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void switchContentView(View view){
        this.setContentView(view);
    }

    private void createRegistrBtn() {
            /*
            Creates a TextView
            Sets it to Clickable
            Opens to another activity
             */
        registerBtn = new TextView(this);
        registerBtn.setText("Register");
        registerBtn.setTextSize(15);
        registerBtn.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
        registerBtn.setPaintFlags(registerBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        registerBtn.setGravity(Gravity.CENTER_HORIZONTAL);
        registerBtn.setClickable(true);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userReg = true;
                inflateViews(true);
                switchContentView(layoutReg);
                submitBtn.setOnClickListener(onClickRegstr);

                registerBtn.setText("Cancel registration");
                registerBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inflateViews(false);
                        switchContentView(layout);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void inflateViewsForRegister(){

        LinearLayout.LayoutParams etlparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        etlparams.setMargins(30,0,30,10);

        //title registerBtn Firstname
        firstName = new EditText(this);
        firstName.setLayoutParams(etlparams);
        firstName.setBackground(getResources().getDrawable(R.drawable.edittext_states));
        firstName.setHint("FIRSTNAME");
        firstName.setTextSize(15);
        firstName.setGravity(Gravity.CENTER_HORIZONTAL);
        firstName.startAnimation(anim);

        //title registerBtn lastname
        lastName = new EditText(this);
        lastName.setLayoutParams(etlparams);
        lastName.setBackground(getResources().getDrawable(R.drawable.edittext_states));
        lastName.setHint("LASTNAME");
        lastName.setTextSize(15);
        lastName.setGravity(Gravity.CENTER_HORIZONTAL);
        lastName.startAnimation(anim);

        //title registerBtn uploadDate
        email = new EditText(this);
        email.setLayoutParams(etlparams);
        email.setBackground(getResources().getDrawable(R.drawable.edittext_states));
        email.setHint("EMAIL");
        email.setTextSize(15);
        email.setGravity(Gravity.CENTER_HORIZONTAL);
        email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email, 0, 0, 0);
        email.startAnimation(anim);
        //ll.addView(register);
    }

    //this inflates the views dynamically into the activity
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private boolean inflateViews(boolean userReg){

        boolean flag = false;
        try {
            //create layout params for views
            lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams.gravity = Gravity.CENTER_HORIZONTAL;
            lparams.setMargins(0,100,0,50);


            lparams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams2.gravity = Gravity.CENTER_HORIZONTAL;

            //for layout
            layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setBackground(getResources().getDrawable(R.drawable.homebg));
            //for layout with registerBtn views
            layoutReg = new LinearLayout(this);
            layoutReg.setBackground(getResources().getDrawable(R.drawable.homebg));
            layoutReg.setOrientation(LinearLayout.VERTICAL);

            //for logo
            im = new ImageView(this);
            im.setLayoutParams(lparams);
            im.setImageDrawable(getResources().getDrawable(R.drawable.logo));
            tv = new TextView(this);
            tv.setText("Learning Object Player");
            tv.setGravity(Gravity.CENTER_HORIZONTAL);

            LinearLayout.LayoutParams etlparams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            etlparams2.setMargins(10,0,10,10);

            //for username text area
            user = new EditText(this);
            user.setLayoutParams(etlparams2);
            user.setBackground(getResources().getDrawable(R.drawable.edittext_states));
            user.setHint("USERNAME");
            user.setTextSize(15);
            user.setGravity(Gravity.CENTER_HORIZONTAL);
            user.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user, 0, 0, 0);

            //for password text area
            pass = new EditText(this);
            pass.setLayoutParams(etlparams2);
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pass.setBackground(getResources().getDrawable(R.drawable.edittext_states));
            pass.setHint("PASSWORD");
            pass.setTextSize(15);
            pass.setGravity(Gravity.CENTER_HORIZONTAL);
            pass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, 0, 0);

            createSbmtButton();
            createRegistrBtn();

            if(userReg){
                inflateViewsForRegister();
                layoutReg.addView(im);
                layoutReg.addView(user);
                layoutReg.addView(pass);
                layoutReg.addView(firstName);
                layoutReg.addView(lastName);
                layoutReg.addView(email);
                layoutReg.addView(submitBtn);
                layoutReg.addView(registerBtn);
            }
            else {
                layout.addView(im);
                layout.addView(user);
                layout.addView(pass);
                layout.addView(submitBtn);
                layout.addView(registerBtn);
                //layout.addView(jsontest);
            }

            flag = true;

        }catch(Exception e){}
        return flag;
    }
}
