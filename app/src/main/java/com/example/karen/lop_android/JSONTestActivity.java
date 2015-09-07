package com.example.karen.lop_android;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    TextView name;
    TextView mmr;
    Button b;
    String test = null;
    JSONObject strRoot;
    JSONArray arr;
    private ProgressDialog pdia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsontest);

        id = (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.name);
        mmr = (TextView) findViewById(R.id.mmr);
        b = (Button) findViewById(R.id.get);

        //display json data
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), (strRoot.toString()!=null)?strRoot.toString():"null", Toast.LENGTH_LONG).show();

                Toast.makeText(getApplicationContext(), "fetching data from http://demos.tricksofit.com..", Toast.LENGTH_SHORT).show();
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

    private class JSONParseTask extends AsyncTask<String,String,JSONObject>{
        private boolean finished = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdia = new ProgressDialog(JSONTestActivity.this);
            pdia.setMessage("Fetching data from ");
            pdia.setIndeterminate(false);
            pdia.setCancelable(true);
            pdia.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            //pdia.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            return getJSONFromURL();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if(jsonObject!=null) {
                id.setText("Name: " + jsonObject.optString("name"));
                name.setText("ID: " + jsonObject.optString("id"));
                mmr.setText("URL: " + jsonObject.optString("url"));
            }
            else{
                Toast.makeText(getApplicationContext(), "json is null", Toast.LENGTH_LONG).show();
            }


            Toast.makeText(getApplicationContext(), "done!", Toast.LENGTH_SHORT).show();
        }

        public JSONObject getJSONFromURL(){
            //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            //StrictMode.setThreadPolicy(policy);

            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost("http://demos.tricksofit.com/files/json.php");

            try {
                response = myClient.execute(myConnection);
                test = EntityUtils.toString(response.getEntity(), "UTF-8");
                arr = new JSONArray(test);
                strRoot = arr.getJSONObject(0);
                finished = true;

            }
            catch (IOException e) {e.printStackTrace();}
            catch (JSONException e) {e.printStackTrace();}


            return strRoot;
        }
    }
}
