package com.example.karen.lop_android;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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
    TextView title;
    TextView subject;
    TextView description;
    TextView downloads;
    TextView uploadDate;
    TextView likes;
    TextView sequence;
    TextView price;
    TextView errorList;

    TextView pValue;
    Button b;
    String urlTest="http://demos.tricksofit.com/files/json.php";
    String urlIan="http://192.168.1.43:8080/InformatronYX/informatron/LO/availableLearningObjects";
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
        title = (TextView) findViewById(R.id.user);
        subject = (TextView) findViewById(R.id.pass);
        description = (TextView) findViewById(R.id.fname);
        downloads = (TextView) findViewById(R.id.lname);
        uploadDate = (TextView) findViewById(R.id.email);
        likes = (TextView) findViewById(R.id.last_login);
        sequence = (TextView) findViewById(R.id.last_download_date);
        price = (TextView) findViewById(R.id.last_download_id);
        errorList = (TextView) findViewById(R.id.liable_los);

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
                //Toast.makeText(getApplicationContext(), "hehe "+getJSONFromURL(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    public JSONObject getJSONFromURL(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        HttpPost myConnection = new HttpPost(urlTest);

        try {
            response = myClient.execute(myConnection);
            test = EntityUtils.toString(response.getEntity(), "UTF-8");
            arr = new JSONArray(test);
            strRoot = arr.getJSONObject(0);
            //finished = true;

        }
        catch (IOException e) {
            Toast.makeText(getApplicationContext(), "error1: "+e.toString(), Toast.LENGTH_LONG).show();
        }
        catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "error2: "+e.toString(), Toast.LENGTH_LONG).show();
        }

        //startProgressBar();
        return strRoot;
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
            Toast.makeText(getApplicationContext(), strRoot.toString(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
                /*if (jsonObject != null) {
                    id.setText(((jsonObject.optInt("id") + "") != null) ?"id: " + jsonObject.optString("id") : "null");
                title.setText(((jsonObject.optString("title")) != null) ? "title: " + jsonObject.optString("title") : "null");
                subject.setText(((jsonObject.optString("subject")) != null) ? "subject: " + jsonObject.optString("subject") : "null");
                description.setText(((jsonObject.optString("description")) != null) ? "description: " + jsonObject.optString("description") : "null");
                downloads.setText(((jsonObject.optString("downloads")) != null) ? "downloads: " + jsonObject.optString("downloads") : "null");
                uploadDate.setText(((jsonObject.optString("uploadDate")) != null) ? "uploadDate: " + jsonObject.optString("uploadDate") : "null");
                likes.setText(((jsonObject.optString("likes")) != null) ? "likes: " + jsonObject.optString("likes") : "null");
                sequence.setText(((jsonObject.optString("sequence")) != null) ? "sequence: " + jsonObject.optString("sequence") : "null");
                price.setText(((jsonObject.optString("price")) != null) ? "price " + jsonObject.optString("price") : "null");
                errorList.setText(((jsonObject.optString("errorList")) != null) ? "errorList: " + jsonObject.optString("errorList") : "null");
                } else {
                    Toast.makeText(getApplicationContext(), "json is null", Toast.LENGTH_LONG).show();
                }*/


            Toast.makeText(getApplicationContext(), "done!", Toast.LENGTH_SHORT).show();
        }

        public JSONObject getJSONFromURL(){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost(urlIan);

            try {
                response = myClient.execute(myConnection);
                test = EntityUtils.toString(response.getEntity(), "UTF-8");
                arr = new JSONArray(test);
                strRoot = arr.getJSONObject(0);
                finished = true;

            }
            catch (IOException e) {
                //Toast.makeText(getApplicationContext(), "error1: "+e.toString(), Toast.LENGTH_LONG).show();
            }
            catch (JSONException e) {
                //Toast.makeText(getApplicationContext(), "error2: "+e.toString(), Toast.LENGTH_LONG).show();
            }

            startProgressBar();
            return strRoot;
        }
    }
}
