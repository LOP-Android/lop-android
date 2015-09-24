package com.example.karen.lop_android;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Meik on 9/24/2015.
 */
public class DownloadLOFragment extends Fragment {

String downloadLoURL = "http://localhost:8080/InformatronYX/informatron/LO/availableLearningObjects";
JSONArray arr;
JSONObject strRoot;
String test = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        new JSONParseTask().execute();
        return super.onCreateView(inflater, container, savedInstanceState);

    }



    private class JSONParseTask extends AsyncTask<String, Integer, JSONObject>{
        private boolean finished = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            return getJSONFromURL();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }

        public JSONObject getJSONFromURL(){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost(downloadLoURL);

            try {
                response = myClient.execute(myConnection);
                test = EntityUtils.toString(response.getEntity(), "UTF-8");
                arr = new JSONArray(test);
                strRoot = arr.getJSONObject(0);
                finished = true;

            }
            catch (IOException e) {e.printStackTrace();}
            catch (JSONException e) {e.printStackTrace();}

            //startProgressBar();
            return strRoot;
        }
    }

    @Override
    public void onInflate(AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(attrs, savedInstanceState);
    }
}
