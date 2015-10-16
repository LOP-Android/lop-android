package com.example.karen.lop_android;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.HttpURLConnection;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Meik on 9/24/2015.
 */
public class DownloadLOFragment extends Fragment {

public static String myHttpURL = "http://www.noiseaddicts.com/samples_1w72b820/4245.mp3";
private String downloadLoURL = "http://localhost:8080/InformatronYX/informatron/LO/availableLearningObjects";
private JSONArray arr;
private JSONObject strRoot;

private LinearLayout.LayoutParams lparams;
private LinearLayout.LayoutParams btnlparams;
private LinearLayout layout;
private Button setDlURL;
private EditText e;
    private TextView dlIndicator;
private Button btnDownload;
private TextView pValue;
private ProgressBar progressBar;
private View rootView;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        lparams.gravity = Gravity.CENTER_HORIZONTAL;

        btnlparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnlparams.gravity = Gravity.CENTER_HORIZONTAL;

        btnDownload = new Button(getActivity());
        btnDownload.setText("download");
        btnDownload.setLayoutParams(btnlparams);
        btnDownload.setBackground(getResources().getDrawable(R.drawable.button_states));
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDownloadURL(SettingsFragment.URL_Download);
                new DownloadTask(getActivity(), myHttpURL).execute();
            }
        });

        layout = new LinearLayout(getActivity());
        layout.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(lparams);

        e = new EditText(getActivity());
        e.setGravity(Gravity.CENTER_HORIZONTAL);
        e.setHint("input url here");

        LinearLayout.LayoutParams tvlparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvlparams.gravity = Gravity.CENTER;

        dlIndicator = new TextView(getActivity());
        dlIndicator.setLayoutParams(tvlparams);

        setDlURL = new Button(getActivity());
        setDlURL.setLayoutParams(btnlparams);
        setDlURL.setBackground(getResources().getDrawable(R.drawable.button_states));
        setDlURL.setGravity(Gravity.CENTER_HORIZONTAL);
        setDlURL.setText("connect");
        setDlURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:finish check if URL connection is ok and valid
                myHttpURL = e.getText() + "";
                //DL
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    URL dlurl = new URL(myHttpURL);

                    HttpURLConnection dlconnection = (HttpURLConnection) dlurl.openConnection();

                    if (dlconnection.getResponseCode() == 200) {
                        dlIndicator.setText("connected");
                        dlIndicator.setBackgroundColor(Color.GREEN);
                    } else {
                        dlIndicator.setText(dlconnection.getResponseCode() + dlconnection.getResponseMessage());
                        dlIndicator.setBackgroundColor(Color.RED);
                    }

                } catch (MalformedURLException e) {
                    Toast.makeText(getActivity(), "error: " + e.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        pValue = new TextView(getActivity());
        pValue.setGravity(Gravity.CENTER_HORIZONTAL);
        pValue.setTextSize(20);

        progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setPadding(50,100,50,0);
        progressBar.setVisibility(View.INVISIBLE);


        layout.addView(e);
        layout.addView(dlIndicator);
        layout.addView(setDlURL);
        layout.addView(btnDownload);
        layout.addView(progressBar);
        layout.addView(pValue);

        rootView = layout;
        return rootView;
    }

    public void setDownloadURL(String url){
        this.myHttpURL = url;
    }

    private class DownloadTask extends AsyncTask< Void, Integer, Void>{
        private boolean finished = false;
        private Context context;
        private String URL;
        InputStream stream;
        InputStream stream1;
        private Exception ex;

        public DownloadTask(Context ctx, String url){
            this.context = ctx;
            this.URL = url;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setMax(100);
            progressBar.setProgress(values[0]);
            pValue.setText(values[0]+"%");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "downloading learning elements..", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL myUrl = new URL(this.URL);

                HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("GET");
                connection.connect();

                //=====================for informatron
                HttpClient client = new DefaultHttpClient();
                HttpClient client1 = new DefaultHttpClient();

                try {
                    HttpGet req = new HttpGet(myUrl.toURI());
                    HttpResponse response = client.execute(req);
                    stream = response.getEntity().getContent();

                    HttpGet req1 = new HttpGet(myUrl.toURI());
                    HttpResponse response1 = client1.execute(req1);
                    stream1 = response1.getEntity().getContent();


                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
                //=====================for informatron

                File rootDirectory = new File(Environment.getExternalStorageDirectory(), "lo7");

                if(!rootDirectory.exists()){
                    rootDirectory.mkdir();
                }

                String nameOfFile = URLUtil.guessFileName(myHttpURL,null, MimeTypeMap.getFileExtensionFromUrl(myHttpURL));
                File file = new File(rootDirectory,nameOfFile);
                file.createNewFile();

                FileOutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                byte[] buffer1 = new byte[1024];
                long total = 0;
                long currentBytes = 0;
                int byteCount;
                int byteCount1;

                while((byteCount1 = stream1.read(buffer1))>0){
                    total += byteCount1;
                }

                while((byteCount = stream.read(buffer))>0){
                    currentBytes+=byteCount;
                    publishProgress((int)(currentBytes * 100 / total));
                    outputStream.write(buffer, 0, byteCount);
                }

                outputStream.close();

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
                getActivity().sendBroadcast(intent);

                finished = true;
            } catch (MalformedURLException e) {
                ex = e;
                //Toast.makeText(context, "e: "+e.toString(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                ex = e;
                //Toast.makeText(context, "e: "+e.toString(), Toast.LENGTH_LONG).show();
            }
        return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(finished) {
                Toast.makeText(context, "download complete!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context, "download failed!", Toast.LENGTH_LONG).show();
                Toast.makeText(context, "ex: "+ex.toString(), Toast.LENGTH_LONG).show();
            }

        }




    }

    @Override
    public void onInflate(AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(attrs, savedInstanceState);
    }
}
