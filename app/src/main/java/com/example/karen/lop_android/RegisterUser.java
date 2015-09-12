package com.example.karen.lop_android;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
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
private String ianSignUpURL = "http://192.168.1.52:8080/InformatronYX/informatron/user/signup";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inflate the views into the layout
        inflateViews();

        this.setContentView(ll);
    }

    private void initLayoutParams() {
        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.gravity = Gravity.CENTER_HORIZONTAL;
    }

    private void inflateViews() {
        initLayoutParams();

        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        //call onRegister to create the views
        onRegister();
        //create register button and set onclick for post json
        createRegistrBtn();

        ll.addView(firstName);
        ll.addView(lastName);
        ll.addView(username);
        ll.addView(password);
        ll.addView(email);
        ll.addView(reg);
    }

    //register button stores all the user input data
    private void createRegistrBtn() {
        reg = new Button(this);
        reg.setLayoutParams(lparams);
        reg.setText("Submit");
        reg.setTextSize(23);
        reg.setGravity(Gravity.CENTER_HORIZONTAL);

        reg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "register data: " + POST(ianSignUpURL, getApplicationContext()), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void onRegister(){

        //user register Firstname
        firstName = new EditText(this);
        firstName.setLayoutParams(lparams);
        firstName.setHint("FIRSTNAME");
        firstName.setTextSize(23);
        firstName.setGravity(Gravity.CENTER_HORIZONTAL);

        //user register lastname
        lastName = new EditText(this);
        lastName.setLayoutParams(lparams);
        lastName.setHint("LASTNAME");
        lastName.setTextSize(23);
        lastName.setGravity(Gravity.CENTER_HORIZONTAL);

        //user register username
        username = new EditText(this);
        username.setLayoutParams(lparams);
        username.setHint("USERNAME");
        username.setTextSize(23);
        username.setGravity(Gravity.CENTER_HORIZONTAL);

        //user register password
        password = new EditText(this);
        password.setLayoutParams(lparams);
        password.setHint("PASSWORD");
        password.setTextSize(23);
        password.setGravity(Gravity.CENTER_HORIZONTAL);

        //user register email
        email = new EditText(this);
        email.setLayoutParams(lparams);
        email.setHint("EMAIL");
        email.setTextSize(23);
        email.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    /**this is for posting json onto server
     *
     *
     * references: http://hmkcode.com/android-send-json-data-to-server/
     *
     *
    **/
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
            Toast.makeText(ctx, "ni error"+e.toString(), Toast.LENGTH_LONG).show();
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

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
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
