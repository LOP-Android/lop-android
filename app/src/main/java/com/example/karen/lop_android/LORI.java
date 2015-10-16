package com.example.karen.lop_android;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hebi5 on 10/12/2015.
 */
public class LORI extends Fragment {
    public String URL = "http://192.168.254.102:8080/InformatronYX/informatron/lori/submit";
    LinearLayout.LayoutParams lparams;
    LinearLayout.LayoutParams btnlparams;
    LinearLayout.LayoutParams elvParams;
    LinearLayout ll;
    ScrollView sv;
    ImageView im;
    View rootView;
    LORIInfoAdapter loriAdapter;
    Button send;
    String jsonLORI = "";
    ExpandableListView elv;
    String[] questions = {
            "Content Quality",
            "Learning Goal Alignment",
            "Feedback and Adaptation",
            "Motivation",
            "Presentation Design",
            "Interaction Usability",
            "Accessibility",
            "Reusability",
            "Standards Compliance",
            "Nihingo"
    };


    public void setLORIUrl(String url){
        this.URL = url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inflateViews();

        rootView = ll;
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void inflateViews(){

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        elvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 500);
        elvParams.setMargins(30,50,30,50);

        btnlparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnlparams.gravity = Gravity.CENTER_HORIZONTAL;

        sv = new ScrollView(getActivity());
        sv.setVerticalScrollBarEnabled(true);
        sv.setLayoutParams(lparams);
        sv.setBackgroundColor(Color.WHITE);

        ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(Color.WHITE);

        im = new ImageView(getActivity());
        im.setImageDrawable(getResources().getDrawable(R.drawable.lori_logo));

        loriAdapter = new LORIInfoAdapter(getActivity(), questions);

        elv = new ExpandableListView(getActivity());
        elv.setAdapter(loriAdapter);
        elv.setLayoutParams(elvParams);

        send = new Button(getActivity());
        send.setText("SUBMIT");
        send.setLayoutParams(btnlparams);
        send.setBackground(getResources().getDrawable(R.drawable.button_states));
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLORIUrl(SettingsFragment.URL_LORI);
                sendLORI(loriAdapter.getJSONArray(), 123);
            }
        });

        ll.addView(im);
        ll.addView(elv);
        ll.addView(send);
    }

    public void sendLORI(JSONArray jsonArray, int loid){
        InputStream inputStream = null;
        String result = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", 1234);
            jsonObject.put("evaluation", jsonArray);
            jsonObject.put("learningObjectId", 123);
            jsonObject.put("subject", "boto");
            jsonObject.put("errorList", null);
            jsonObject.put("loris", null);
            jsonObject.put("reviewId", 69);


            jsonLORI = jsonObject.toString();

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);

            StringEntity se = new StringEntity(jsonLORI);
            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();
            result = convertInputStreamToString(inputStream);

            Toast.makeText(getActivity(),"mao ni "+ result, Toast.LENGTH_LONG).show();

        }
        catch(HttpHostConnectException e){
            Toast.makeText(getActivity(), "Must establish a connection to Host! \nERROR: "+e.toString(), Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Toast.makeText(getActivity(), "ERROR: "+e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
