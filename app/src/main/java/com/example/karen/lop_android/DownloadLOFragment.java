package com.example.karen.lop_android;

import java.net.URL;
import java.net.HttpURLConnection;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.RelativeLayout;
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

/**
 * Created by Meik on 9/24/2015.
 */
public class DownloadLOFragment extends Fragment {

public static String myHttpURL = "http://www.noiseaddicts.com/samples_1w72b820/4245.mp3";
String downloadLoURL = "http://localhost:8080/InformatronYX/informatron/LO/availableLearningObjects";
JSONArray arr;
JSONObject strRoot;
String test = null;

private LinearLayout.LayoutParams lparams;
private LinearLayout.LayoutParams btnlparams;
private LinearLayout layout;
private Button setURL;
private EditText e;
LinearLayout ll;
TextView tv;
Button btnTrad;
TextView pValue;
ProgressBar progressBar;
View rootView;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        lparams.gravity = Gravity.CENTER_HORIZONTAL;

        btnlparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnlparams.gravity = Gravity.CENTER_HORIZONTAL;

        btnTrad = new Button(getActivity());
        btnTrad.setText("download");
        btnTrad.setLayoutParams(btnlparams);
        btnTrad.setBackground(getResources().getDrawable(R.drawable.button_states));
        btnTrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadTask(getActivity(), myHttpURL).execute();
            }
        });

        layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(lparams);

        e = new EditText(getActivity());
        e.setGravity(Gravity.CENTER_HORIZONTAL);
        e.setHint("input url here");

        setURL = new Button(getActivity());
        setURL.setBackground(getResources().getDrawable(R.drawable.button_states));
        setURL.setGravity(Gravity.CENTER_HORIZONTAL);
        setURL.setLayoutParams(btnlparams);
        setURL.setText("connect");
        setURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHttpURL = e.getText()+"";
                Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
            }
        });

        pValue = new TextView(getActivity());
        pValue.setGravity(Gravity.CENTER_HORIZONTAL);
        pValue.setTextSize(20);

        progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setPadding(50,100,50,0);
        progressBar.setVisibility(View.INVISIBLE);


        layout.addView(e);
        layout.addView(setURL);
        layout.addView(btnTrad);
        layout.addView(progressBar);
        layout.addView(pValue);

        rootView = layout;
        return rootView;
    }



    private class DownloadTask extends AsyncTask< Void, Integer, Void>{
        private boolean finished = false;
        private Context context;
        private String URL;

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
            Toast.makeText(context, "downloading..", Toast.LENGTH_SHORT).show();
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
                File rootDirectory = new File(Environment.getExternalStorageDirectory(), "My Pictures");

                if(!rootDirectory.exists()){
                        rootDirectory.mkdirs();

                }

                String nameOfFile = URLUtil.guessFileName(myHttpURL,null, MimeTypeMap.getFileExtensionFromUrl(myHttpURL));
                File file = new File(rootDirectory,nameOfFile);
                file.createNewFile();

                InputStream inputStream = connection.getInputStream();

                FileOutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                long total = 0;
                int byteCount = 0;


                while((byteCount = inputStream.read(buffer))>0){
                    total+=byteCount;
                    publishProgress((int)(total * 100 / connection.getContentLength()));
                    outputStream.write(buffer, 0, byteCount);
                }

                outputStream.close();

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
                getActivity().sendBroadcast(intent);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Toast.makeText(getActivity(), "Download complete!",Toast.LENGTH_SHORT ).show();
        }




    }

    @Override
    public void onInflate(AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(attrs, savedInstanceState);
    }
}
