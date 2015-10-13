package com.example.karen.lop_android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity {
    private static int ONCLICK_LOGIN = 1;
    private static int ONCLICK_REGISTER = 2;

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
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private TextView registerBtn;

    boolean userReg = false;
    private Animation anim;

    public Register r;

    //private String username:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize animation utility for usability in any edit text
        initAnimationUtils();

        inflateViews(false);

        onClickLogin = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                //if(user.getText().toString().equals("admin") && pass.getText().toString().equals("admin")) {
                    startActivity(intent);
                //}
                //else{
                //    Toast.makeText(getApplicationContext(), "Error: Incorrect username/password", Toast.LENGTH_SHORT).show();
                //}
            }
        };

        onClickRegstr = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                r = new Register();
                //set on click registerBtn to post json to informatron
                try {
                    r.setUrl(SettingsFragment.URLString);
                    r.registerUser(getApplicationContext(),
                            user.getText().toString(),
                            pass.getText().toString(),
                            email.getText().toString(),
                            firstName.getText().toString(),
                            lastName.getText().toString());
                    Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "error: "+e.toString(), Toast.LENGTH_SHORT).show();
                };
            }
        };

        this.setContentView(layout);
    }

    public void initAnimationUtils(){
        anim = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        anim.setInterpolator(getApplicationContext(), android.R.anim.bounce_interpolator);
        anim.setDuration(1000);

    }

    private void switchOnClickListener(View.OnClickListener ocl, Button btn){
        btn.setOnClickListener(ocl);
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
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                //if(user.getText().toString().equals("admin") && pass.getText().toString().equals("admin")) {
                    startActivity(intent);
                //}
                //else{
                //    Toast.makeText(getApplicationContext(), "Error: Incorrect username/password", Toast.LENGTH_SHORT).show();
                //}
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

    private void refreshContentView(){
        this.setContentView(layout);
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

        //title registerBtn Firstname
        firstName = new EditText(this);
        firstName.setBackground(getResources().getDrawable(R.drawable.edittext_states));
        firstName.setHint("FIRSTNAME");
        firstName.setTextSize(15);
        firstName.setGravity(Gravity.CENTER_HORIZONTAL);
        firstName.startAnimation(anim);

        //title registerBtn lastname
        lastName = new EditText(this);
        lastName.setBackground(getResources().getDrawable(R.drawable.edittext_states));
        lastName.setHint("LASTNAME");
        lastName.setTextSize(15);
        lastName.setGravity(Gravity.CENTER_HORIZONTAL);
        lastName.startAnimation(anim);

        //title registerBtn uploadDate
        email = new EditText(this);
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

            //for username text area
            user = new EditText(this);
            user.setBackground(getResources().getDrawable(R.drawable.edittext_states));
            user.setHint("USERNAME");
            user.setTextSize(15);
            user.setGravity(Gravity.CENTER_HORIZONTAL);
            user.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user, 0, 0, 0);

            //for password text area
            pass = new EditText(this);
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
