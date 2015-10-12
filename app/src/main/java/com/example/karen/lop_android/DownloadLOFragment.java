package com.example.karen.lop_android;

import java.net.URL;
import java.net.HttpURLConnection;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.LinearLayout;
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

String myHttpURL = "http://s23.postimg.org/ip87nyy4r/Arianny.jpg";
String downloadLoURL = "http://localhost:8080/InformatronYX/informatron/LO/availableLearningObjects";
JSONArray arr;
JSONObject strRoot;
String test = null;
View rootView;
 LinearLayout ll;
 TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            Button btnTrad = (Button)rootView.findViewById(R.id.btnTrad);
            Button btnDM = (Button)rootView.findViewById(R.id.btnDM);

        btnTrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new myTask().execute();
            }
        });

        btnDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return rootView;
    }





    private class myTask extends AsyncTask< Void, Void, Void>{
        private boolean finished = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL myUrl = new URL(myHttpURL);

                HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("GET");
                connection.connect();
                File rootDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "My Pictures");

                if(!rootDirectory.exists()){
                        rootDirectory.mkdirs();

                }

                String nameOfFile = URLUtil.guessFileName(myHttpURL,null, MimeTypeMap.getFileExtensionFromUrl(myHttpURL));
                File file = new File(rootDirectory,nameOfFile);
                file.createNewFile();

                InputStream inputStream = connection.getInputStream();

                FileOutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int byteCount = 0;

                while((byteCount = inputStream.read(buffer))>0){
                    outputStream.write(buffer,0,byteCount);
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

            Toast.makeText(getActivity(), "Completed",Toast.LENGTH_SHORT ).show();
        }




    }

    @Override
    public void onInflate(AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(attrs, savedInstanceState);
    }
}
