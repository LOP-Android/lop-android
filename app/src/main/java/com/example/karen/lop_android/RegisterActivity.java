package com.example.karen.lop_android;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends ActionBarActivity {


    // https://www.youtube.com/watch?v=5fmcmxbDLhg



    Context c;
    //Initiate variables to be used
    EditText first;
    EditText last;
    EditText user;
    EditText pass;
    EditText pass1;
    EditText email;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // locate respective variables
        first = (EditText)findViewById(R.id.etFirstName);
        last = (EditText)findViewById(R.id.etLastName);
        user = (EditText)findViewById(R.id.etUserName);
        pass = (EditText)findViewById(R.id.etPassword);
        pass1 = (EditText)findViewById(R.id.etPassword1);
        email = (EditText)findViewById(R.id.etEmail);
        register = (Button)findViewById(R.id.btnRegister);
        c = this;

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( (pass.getText().toString().equals(pass1.getText().toString()))== false ) {
                    toast("Passwords do not match");
                    return;
                }
                else {
                    getJSONTask g = new getJSONTask();
                    g.execute();
                }

            }
        });

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


    public class getJSONTask extends AsyncTask {
        //must get informatron URL to work
        String url = "Enter Informatron's Url";

        public static final int STATE_REGISTER = 1;
        @Override
        protected Object doInBackground(Object[] params) {

            getJSON(url,STATE_REGISTER);

            return null;
        }
    }


    private void getJSON(String url, int state) {

        //Below are the bssics for accessing JSON
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();



        boolean valid = false;

        switch(state){
            case getJSONTask.STATE_REGISTER :


                   /* This function is in coordinate with the Back end.
                    The "username" is required by the backend
                    The username is supplied by the user.
                    */
                     postParameters.add(new BasicNameValuePair("userName",user.getText().toString()));
                     postParameters.add(new BasicNameValuePair("password",pass.getText().toString()));
                     postParameters.add(new BasicNameValuePair("firstName",first.getText().toString()));
                     postParameters.add(new BasicNameValuePair("lastName",last.getText().toString()));
                     postParameters.add(new BasicNameValuePair("email",email.getText().toString()));
                     valid = true;

                break;
            default :
                toast("Error State");

        }

        if(valid == true) {

            StringBuffer sb = new StringBuffer("");
            BufferedReader br = null;
            try{
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParameters);
                request.getEntity();
                HttpResponse response = httpclient.execute(request);

                br  = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line = "";
                String lineSeparator = System.getProperty("line.separator");

                while((line = br.readLine())!=null){
                    sb.append(line+lineSeparator);
                }
                br.close();;


            }
            catch (Exception e){
                e.printStackTrace();
            }

            ////////


            decodeResultIntoJSON(sb.toString());


            /////
        }
        else{

        }





    }

    //Toast Maker
    public void toast(final String s) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }


    //Transfroms response data into JSON data
    private void decodeResultIntoJSON(String response) {

        if(response.contains("error")){
            try{
                JSONObject jo = new JSONObject(response);
                //response from the server/informatron
                String error = jo.getString("error");
                //Option to print error. an error we get from the server.
                toast(error);
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        else{
            // no error detected

        }
/*
        Example JSON format from inofmatron
        {
        "id":null
        ,"username":"Test username"
        ,"password":"Test password"
        ,"firstName":null
        ,"lastName":null
        ,"email":null
        ,"lastLogin":null
        ,"lastDownloadDate":null
        ,"lastDownloadId":null
        ,"liableLearningObjects":[]
        ,"token":null
        ,"approved":false
        ,"blocked":false
        ,"userType":"Guest"
        ,"functionType":0}
*/
        try{
            JSONObject jo = new JSONObject(response);

            //Gets the name in JSON (example purposes only)
            //String id        = jo.getString("id");
            String username  = jo.getString("username");
            String password  = jo.getString("password");
            String firstName = jo.getString("firstName");
            String lastName  = jo.getString("lastName");
            String email     = jo.getString("email");
            //etc

            String outputText = "Username : " + username +
                                "\n Password : " + password +
                                "\n First Name :  " + firstName +
                                "\n Last Name : "+lastName +
                                "\n Email : " + email;

            toast(outputText);
            toast("Registration Successful");

        }
        catch(JSONException e){
            e.printStackTrace();
        }

    }



}


