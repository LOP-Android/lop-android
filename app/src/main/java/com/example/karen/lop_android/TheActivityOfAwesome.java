package com.example.karen.lop_android;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.HttpURLConnection;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.List;

/**
 * Created by Meik on 9/24/2015.
 */
public class TheActivityOfAwesome extends ActionBarActivity {

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
    private Button createFolder;
    private Button deleteFolder;
    private EditText folderName;
private TextView pValue;
private ProgressBar progressBar;
private View rootView;

    Button buttonOpenDialog;
    Button buttonUp;
    TextView textFolder;

    String KEY_TEXTPSS = "TEXTPSS";
    static final int CUSTOM_DIALOG_ID = 0;

    File root;
    File curFolder;

    ListView dialog_ListView;
    private List<String> fileList = new ArrayList<String>();


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //======================================
        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        lparams.gravity = Gravity.CENTER_HORIZONTAL;

        btnlparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnlparams.gravity = Gravity.CENTER_HORIZONTAL;

        btnDownload = new Button(getApplicationContext());
        btnDownload.setText("download");
        btnDownload.setLayoutParams(btnlparams);
        btnDownload.setBackground(getResources().getDrawable(R.drawable.button_states));
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(CUSTOM_DIALOG_ID);

                //setDownloadURL(SettingsFragment.URL_Download);
                //new DownloadTask(getActivity(), myHttpURL).execute();

            }
        });

        layout = new LinearLayout(getApplicationContext());
        layout.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(lparams);

        e = new EditText(getApplicationContext());
        e.setGravity(Gravity.CENTER_HORIZONTAL);
        e.setHint("input url here");

        LinearLayout.LayoutParams tvlparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvlparams.gravity = Gravity.CENTER;

        dlIndicator = new TextView(getApplicationContext());
        dlIndicator.setLayoutParams(tvlparams);

        setDlURL = new Button(getApplicationContext());
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
                    Toast.makeText(getApplicationContext(), "error: " + e.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        pValue = new TextView(getApplicationContext());
        pValue.setGravity(Gravity.CENTER_HORIZONTAL);
        pValue.setTextSize(20);

        progressBar = new ProgressBar(getApplicationContext(), null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setPadding(50,100,50,0);
        progressBar.setVisibility(View.INVISIBLE);


        layout.addView(e);
        layout.addView(dlIndicator);
        layout.addView(setDlURL);
        layout.addView(btnDownload);
        layout.addView(progressBar);
        layout.addView(pValue);



        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        curFolder = root;

        this.setContentView(layout);
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
                sendBroadcast(intent);

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


    void ListDir(File f) {
        if(f.equals(root)) {
            buttonUp.setEnabled(false);
        }
        else {
            buttonUp.setEnabled(true);
        }
        curFolder = f;
        textFolder.setText(f.getPath());

        File[] files = f.listFiles();
        fileList.clear();

        for(File file: files) {
            fileList.add((file.getPath()));
        }

        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(getApplicationContext(), R.layout.folder_list,R.id.folder_dir_txt,fileList);
        dialog_ListView.setAdapter(directoryList);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        else if (id == R.id.action_folders) {
            showDialog(CUSTOM_DIALOG_ID);
            buttonUp.setText("back");
            return true;
        }

        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Dialog onCreateDialog(int id) {
        Dialog dialog = null;

        switch(id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_layout);
                dialog.setTitle("Manage Files");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                textFolder = (TextView) dialog.findViewById(R.id.folder);
                buttonUp = (Button) dialog.findViewById(R.id.up);
                buttonUp.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ListDir(curFolder.getParentFile());
                    }
                });
                folderName =(EditText)dialog.findViewById(R.id.folder_name);
                createFolder=(Button)dialog.findViewById(R.id.create_folder);
                createFolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"nisud",Toast.LENGTH_LONG).show();

                        File rootDirectory = new File(Environment.getExternalStorageDirectory(), folderName.getText().toString());

                        if(!rootDirectory.exists()){
                            Toast.makeText(getApplicationContext(),rootDirectory.mkdir()+"",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                deleteFolder = (Button)dialog.findViewById(R.id.delete_folder);
                deleteFolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"nisud",Toast.LENGTH_LONG).show();

                        File rootDirectory = new File(Environment.getExternalStorageDirectory(), folderName.getText().toString());

                        if(!rootDirectory.exists()){
                            Toast.makeText(getApplicationContext(),rootDirectory.mkdir()+"",Toast.LENGTH_LONG).show();
                        }
                    }
                });


                dialog_ListView = (ListView) dialog.findViewById(R.id.dialogList);
                dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File selected = new File(fileList.get(position));
                        if(selected.isDirectory()) {
                            ListDir(selected);
                        } else {
                            Toast.makeText(getApplicationContext(),selected.toString() + "selected",
                                    Toast.LENGTH_LONG).show();
                            //error cannot resolve method dismissDialog(int);
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }
                    }
                });
                break;
        }
        return dialog;
    }
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case CUSTOM_DIALOG_ID:
                ListDir(curFolder);
                break;
        }

    }


    //function that deletes folder and files
    void DeleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);

        fileOrDirectory.delete();
    }
}
