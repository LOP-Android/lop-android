package com.example.karen.lop_android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class RegisterUser extends ActionBarActivity{

private LinearLayout.LayoutParams lparams;
private LinearLayout ll;
private TextView tv;
private EditText username;
private EditText password;
private EditText firstName;
private EditText lastName;
private EditText email;
private Button reg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.gravity = Gravity.CENTER_HORIZONTAL;

        ll = new LinearLayout(this);
         this.setContentView(ll);
        ll.setOrientation(LinearLayout.VERTICAL);

        //calling onRegister() method
            onRegister();
        //register button stores all the user input data
        reg = new Button(this);
        reg.setLayoutParams(lparams);
        reg.setText("Submit");
        reg.setTextSize(23);
        reg.setGravity(Gravity.CENTER_HORIZONTAL);

        reg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Register Successfully! ", Toast.LENGTH_LONG).show();

            }
        });





        ll.addView(firstName);
        ll.addView(lastName);
        ll.addView(username);
        ll.addView(password);
        ll.addView(email);
        ll.addView(reg);

    }

    public void onRegister(){


        //user register Firstname
        firstName = new EditText(this);
        firstName.setHint("FIRSTNAME");
        firstName.setTextSize(23);
        firstName.setGravity(Gravity.CENTER_HORIZONTAL);

        //user register lastname
        lastName = new EditText(this);
        lastName.setHint("LASTNAME");
        lastName.setTextSize(23);
        lastName.setGravity(Gravity.CENTER_HORIZONTAL);

        //user register username
        username = new EditText(this);
        username.setHint("USERNAME");
        username.setTextSize(23);
        username.setGravity(Gravity.CENTER_HORIZONTAL);

        //user register password
        password = new EditText(this);
        password.setHint("PASSWORD");
        password.setTextSize(23);
        password.setGravity(Gravity.CENTER_HORIZONTAL);

        //user register email
        email = new EditText(this);
        email.setHint("EMAIL");
        email.setTextSize(23);
        email.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public String POST(String url){
            InputStream inputStream = null;
            String result = "";
        try{
            String json = "";
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);
            JSONObject j = new JSONObject("");
            j.put("firstName", firstName.getText());
            j.put("lastName", lastName.getText());
            j.put("username", username.getText());
            j.put("password", password.getText());
            j.put("email", email.getText());

            //Convert JsonObject to String
            json = j.toString();
            //set Json to StringEntity
            StringEntity se = new StringEntity(json);
            //set HttpPost Entity
            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content", "application/json");

            HttpResponse httpResponse = httpClient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        }catch(JSONException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

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





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
}
