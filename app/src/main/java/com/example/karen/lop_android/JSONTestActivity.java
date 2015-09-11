package com.example.karen.lop_android;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


@SuppressWarnings("deprecation")
public class JSONTestActivity extends ActionBarActivity {
    TextView id;
    TextView user;
    TextView pass;
    TextView fname;
    TextView lname;
    TextView email;
    TextView lastLogin;
    TextView lastDownloadDate;
    TextView lastDownloadId;
    TextView liableLOs;
    TextView token;
    TextView approved;
    TextView blocked;
    TextView userType;
    TextView guest;
    TextView functionType;

    TextView pValue;
    Button b;
    String urlTest="http://demos.tricksofit.com/files/json.php";
    String urlIan="http://192.168.1.52:8080/InformatronYX/informatron/test";
    String test = null;
    JSONObject strRoot;
    JSONArray arr;
    ProgressBar pbar;
    private ProgressDialog pdia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsontest);

        id = (TextView) findViewById(R.id.id);
        user = (TextView) findViewById(R.id.user);
        pass = (TextView) findViewById(R.id.pass);
        fname = (TextView) findViewById(R.id.fname);
        lname = (TextView) findViewById(R.id.lname);
        email = (TextView) findViewById(R.id.email);
        lastLogin = (TextView) findViewById(R.id.last_login);
        lastDownloadDate = (TextView) findViewById(R.id.last_download_date);
        lastDownloadId = (TextView) findViewById(R.id.last_download_id);
        liableLOs = (TextView) findViewById(R.id.liable_los);
        token = (TextView) findViewById(R.id.token);
        approved = (TextView) findViewById(R.id.approved);
        blocked = (TextView) findViewById(R.id.blocked);
        userType = (TextView) findViewById(R.id.user_type);
        guest = (TextView) findViewById(R.id.guest);
        functionType = (TextView) findViewById(R.id.function_type);

        pValue = (TextView) findViewById(R.id.progressValue);
        b = (Button) findViewById(R.id.get);
        pbar = (ProgressBar) findViewById(R.id.progressBar);

        //display json data
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), (strRoot.toString()!=null)?strRoot.toString():"null", Toast.LENGTH_LONG).show();

                Toast.makeText(getApplicationContext(), "fetching data from informatron..", Toast.LENGTH_SHORT).show();
                new JSONParseTask().execute();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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

    private class JSONParseTask extends AsyncTask<String,Integer,JSONObject>{
        private boolean finished = false;

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pbar.setProgress(values[0]);
            pValue.setText(values[0]+"%");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pValue.setVisibility(View.VISIBLE);
            /*pdia = new ProgressDialog(JSONTestActivity.this);
            pdia.setMessage("Fetching data from ");
            pdia.setIndeterminate(false);
            pdia.setCancelable(true);
            pdia.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);*/

            //pdia.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            return getJSONFromURL();
        }

        public void startProgressBar(){
            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject){
            pValue.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
                if (jsonObject != null) {
                    id.setText(((jsonObject.optInt("id") + "") != null) ?"id: " + jsonObject.optString("id") : "null");
                user.setText(((jsonObject.optString("username"))!=null)?"username: " +jsonObject.optString("user"):"null");
                pass.setText(((jsonObject.optString("password"))!=null)?"password: " +jsonObject.optString("pass"):"null");
                fname.setText(((jsonObject.optString("firstName"))!=null)?"first name: " +jsonObject.optString("fname"):"null");
                lname.setText(((jsonObject.optString("lastName"))!=null)?"last name: " +jsonObject.optString("lname"):"null");
                email.setText(((jsonObject.optString("email"))!=null)?"email: " +jsonObject.optString("email"):"null");
                lastLogin.setText(((jsonObject.optString("lastLogin"))!=null)?"last login: " +jsonObject.optString("lastLogin"):"null");
                lastDownloadDate.setText(((jsonObject.optString("lastDownloadDate"))!=null)?"last download date: " +jsonObject.optString("lastDownloadDate"):"null");
                lastDownloadId.setText(((jsonObject.optString("lastDownloadId"))!=null)?"last download id: " +jsonObject.optString("lastDownloadId"):"null");
                liableLOs.setText(((jsonObject.optString("liableLearningObjects"))!=null)?"liable learning object: " +jsonObject.optString("liableLOs"):"null");
                token.setText(((jsonObject.optString("token"))!=null)?"token: " +jsonObject.optString("token"):"null");
                approved.setText(((jsonObject.optBoolean("approved")+"")!=null)?"approved: " +jsonObject.optString("approved"):"null");
                blocked.setText(((jsonObject.optBoolean("blocked")+"")!=null)?"blocked: " +jsonObject.optString("blocked"):"null");
                userType.setText(((jsonObject.optString("userType"))!=null)?"user type: " +jsonObject.optString("userType"):"null");
                guest.setText(((jsonObject.optString("guest"))!=null)?"guest: " +jsonObject.optString("guest"):"null");
                functionType.setText(((jsonObject.optInt("functionType")+"")!=null)?"function type: " +jsonObject.optString("functionType"):"null");
                } else {
                    Toast.makeText(getApplicationContext(), "json is null", Toast.LENGTH_LONG).show();
                }


            Toast.makeText(getApplicationContext(), "done!", Toast.LENGTH_SHORT).show();
        }

        public JSONObject getJSONFromURL(){
            //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            //StrictMode.setThreadPolicy(policy);

            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost(urlIan);

            try {
                response = myClient.execute(myConnection);
                test = EntityUtils.toString(response.getEntity(), "UTF-8");
                arr = new JSONArray("["+test+"]");
                strRoot = arr.getJSONObject(0);
                finished = true;

            }
            catch (IOException e) {e.printStackTrace();}
            catch (JSONException e) {e.printStackTrace();}

            startProgressBar();
            return strRoot;
        }
    }
}
