package com.example.karen.lop_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class LoginActivity extends Activity {
    private static int ONCLICK_LOGIN = 1;
    private static int ONCLICK_REGISTER = 2;

    private View.OnClickListener onClickLogin;
    private View.OnClickListener onClickRegstr;

    private LinearLayout layout;
    private LinearLayout layoutReg;
    private LinearLayout.LayoutParams lparams;
    private EditText user;
    private EditText pass;
    private Button submit;
    private Button jsontest;
    private TextView tv;
    private ImageView im;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private TextView register;

    boolean userReg = false;
    private Animation anim;
    private String ianSignUpURL = "http://192.168.1.52:8080/InformatronYX/informatron/user/signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        anim = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        anim.setInterpolator(getApplicationContext(), android.R.anim.bounce_interpolator);
        anim.setDuration(1000);

        onClickLogin = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                if(user.getText().toString().equals("admin") && pass.getText().toString().equals("admin")) {
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error: Incorrect username/password", Toast.LENGTH_SHORT).show();
                }
            }
        };

        onClickRegstr = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(POST(ianSignUpURL, getApplicationContext()).length()>1) {
                    Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_LONG).show();
                }
            }
        };

        inflateViews(false);

        this.setContentView(layout);
    }

    private void switchOnClickListener(View.OnClickListener ocl, Button btn){
        btn.setOnClickListener(ocl);
    }

    //creates submit button
    private void createSbmtButton(){
        submit = new Button(this);
        submit.setLayoutParams(lparams);
        submit.setGravity(Gravity.CENTER_HORIZONTAL);
        submit.setText("SUBMIT");
        submit.setOnClickListener(onClickLogin);

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
        register = new TextView(this);
        register.setHint("Register");
        register.setTextSize(15);
        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register.setGravity(Gravity.CENTER_HORIZONTAL);
        register.setClickable(true);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userReg = true;
                inflateViews(true);
                switchContentView(layoutReg);
                submit.setOnClickListener(onClickRegstr);

                register.setText("Cancel registration");
                register.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inflateViews(false);
                        switchContentView(layout);
                        submit.setOnClickListener(onClickLogin);
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

    private void inflateViewsForRegister(){

        //user register Firstname
        firstName = new EditText(this);
        firstName.setHint("FIRSTNAME");
        firstName.setTextSize(15);
        firstName.setGravity(Gravity.CENTER_HORIZONTAL);
        firstName.startAnimation(anim);

        //user register lastname
        lastName = new EditText(this);
        lastName.setHint("LASTNAME");
        lastName.setTextSize(15);
        lastName.setGravity(Gravity.CENTER_HORIZONTAL);
        lastName.startAnimation(anim);

        //user register email
        email = new EditText(this);
        email.setHint("EMAIL");
        email.setTextSize(15);
        email.setGravity(Gravity.CENTER_HORIZONTAL);
        email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.email, 0, 0, 0);
        email.startAnimation(anim);
        //ll.addView(reg);
    }

    //this inflates the views dynamically into the activity
    private boolean inflateViews(boolean userReg){

        boolean flag = false;
        try {
            //create layout params for views
            lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams.gravity = Gravity.CENTER_HORIZONTAL;

            //for layout
            layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            //for layout with register views
            layoutReg = new LinearLayout(this);
            layoutReg.setOrientation(LinearLayout.VERTICAL);

            //for logo
            im = new ImageView(this);
            im.setImageDrawable(getResources().getDrawable(R.drawable.logo));
            tv = new TextView(this);
            tv.setText("Learning Object Player");
            tv.setGravity(Gravity.CENTER_HORIZONTAL);

            //for username text area
            user = new EditText(this);
            user.setHint("USERNAME");
            user.setTextSize(15);
            user.setGravity(Gravity.CENTER_HORIZONTAL);
            user.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user, 0, 0, 0);

            //for password text area
            pass = new EditText(this);
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
                layoutReg.addView(submit);
                layoutReg.addView(register);
            }
            else {
                layout.addView(im);
                layout.addView(user);
                layout.addView(pass);
                layout.addView(submit);
                layout.addView(register);
                //ll.addView(jsontest);
            }

            flag = true;

        }catch(Exception e){}
        return flag;
    }

    public String POST(String url, Context ctx){
        InputStream inputStream = null;
        String result = "";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username.getText());
            jsonObject.put("password", password.getText());
            jsonObject.put("firstName", firstName.getText());
            jsonObject.put("lastName", lastName.getText());
            jsonObject.put("email", email.getText());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            result = convertInputStreamToString(inputStream);

            // 10. convert inputstream to string
            /*if(inputStream != null) {
                Log.d("Input", "ni sud");
                //result = convertInputStreamToString(inputStream);
            }
            else {
                result = "Did not work!";
            }*/

        } catch (Exception e) {
            //e.printStackTrace();
            Toast.makeText(ctx, "ERROR: "+e.toString(), Toast.LENGTH_LONG).show();
        }

        // 11. return result
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
