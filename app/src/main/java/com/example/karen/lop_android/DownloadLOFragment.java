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
import android.util.Log;
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
import java.util.ArrayList;

/**
 * Created by Meik on 9/24/2015.
 */
public class DownloadLOFragment extends Fragment {

public static String myHttpURL = "";
private String downloadLoURL = "http://172.31.11.32:24119/InformatronYX/informatron/connect/download/le/";
private String rootFolderLO = "lo7";

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
private String loName = "";
private String fileExtension = "";


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
                downloadLO(0);
            }
        });

        layout = new LinearLayout(getActivity());
        layout.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(lparams);

        pValue = new TextView(getActivity());
        pValue.setGravity(Gravity.CENTER_HORIZONTAL);
        pValue.setTextSize(20);

        progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setPadding(50,100,50,0);
        progressBar.setVisibility(View.INVISIBLE);

        layout.addView(btnDownload);
        layout.addView(progressBar);
        layout.addView(pValue);

        rootView = layout;
        return rootView;
    }

    public void downloadLO(int lo_number){
        loName = LoginActivity.userSession.getLiableLOList().get(lo_number).getTitle();

        for(int p=0;p< LoginActivity.userSession.getLiableLOList().get(lo_number).getPage().size();p++){

            LearningElement[] page = LoginActivity.userSession.getLiableLOList().get(lo_number).getPage().get(p);
            for(int i=0;i<page.length;i++){
                LearningElement element =  page[i];
                fileExtension =element.getFileExtension();

                myHttpURL = downloadLoURL+LoginActivity.userSession.getId()+"/"+LoginActivity.userSession.getLiableLOList().get(lo_number).getId()+"/"+element.getId();

                new DownloadTask(getActivity(),myHttpURL, element.getId(), fileExtension).execute();

            }
        }
    }

    public void setDownloadURL(String url){
        this.myHttpURL = url;
    }

    private class DownloadTask extends AsyncTask< Void, Integer, Void>{
        private boolean finished = false;
        private Context context;
        private String URL;
        private String fileExtension;
        private String fileName;
        InputStream stream;
        private Exception ex;
        private String filename;

        public DownloadTask(Context ctx, String url, String fileName, String fileExtension){
            this.context = ctx;
            this.URL = url;
            this.fileExtension = fileExtension;
            this.fileName = fileName;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setMax(100);
            progressBar.setProgress(values[0]);
            pValue.setText(values[0]+"");
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

                HttpClient client = new DefaultHttpClient();

                try {
                    HttpGet req = new HttpGet(myUrl.toURI());
                    HttpResponse response = client.execute(req);
                    stream = response.getEntity().getContent();

                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }

                File rootDirectory = new File(Environment.getExternalStorageDirectory()+"/"+rootFolderLO, loName);

                if(!rootDirectory.exists()){
                    rootDirectory.mkdir();
                }

                String nameOfFile = URLUtil.guessFileName(myHttpURL,null, MimeTypeMap.getFileExtensionFromUrl(myHttpURL));

                File file = new File(rootDirectory,fileName+fileExtension);
                if(!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                long currentBytes = 0;
                int byteCount;

                while((byteCount = stream.read(buffer))>0){
                    currentBytes+=byteCount;
                    publishProgress((int)(currentBytes));
                    outputStream.write(buffer, 0, byteCount);
                }

                stream.close();
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
                Toast.makeText(context, "download complete! "+fileName + fileExtension, Toast.LENGTH_LONG).show();
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
