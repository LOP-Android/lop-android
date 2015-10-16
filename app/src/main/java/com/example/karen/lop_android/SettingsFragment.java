package com.example.karen.lop_android;

import android.annotation.TargetApi;
import android.app.Fragment;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hebi5 on 10/9/2015.
 */
public class SettingsFragment extends Fragment {
    //just for switching URL for RegisterUser.
    private LinearLayout.LayoutParams lparams;
    private LinearLayout.LayoutParams btnlparams;
    private LinearLayout layout;
    private View rootView;
    private Button setRGURL;
    private Button setLRURL;
    private EditText urlForRegister;
    private EditText urlForLORI;
    private TextView rgIndicator;
    private TextView loriIndicator;

    public static String URL_Download = "";
    public static String URL_Register = "";
    public static String URL_LORI = "";
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //rootView = inflater.inflate(R

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        lparams.gravity = Gravity.CENTER_HORIZONTAL;

        lparams.setMargins(0,100,0,50);

        btnlparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnlparams.gravity = Gravity.CENTER_HORIZONTAL;

        LinearLayout.LayoutParams etlparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        etlparams.setMargins(25,5,25,5);

        layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));
        layout.setLayoutParams(lparams);

        urlForRegister = new EditText(getActivity());
        urlForRegister.setLayoutParams(etlparams);
        urlForRegister.setGravity(Gravity.CENTER_HORIZONTAL);
        urlForRegister.setHint("input url for register here");

        LinearLayout.LayoutParams tvlparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvlparams.gravity = Gravity.CENTER;

        rgIndicator = new TextView(getActivity());
        rgIndicator.setLayoutParams(tvlparams);

        urlForLORI = new EditText(getActivity());
        urlForLORI.setLayoutParams(etlparams);
        urlForLORI.setGravity(Gravity.CENTER_HORIZONTAL);
        urlForLORI.setHint("input url for lori here");

        loriIndicator = new TextView(getActivity());
        loriIndicator.setLayoutParams(tvlparams);

        setRGURL = new Button(getActivity());
        setRGURL.setLayoutParams(btnlparams);
        setRGURL.setBackground(getResources().getDrawable(R.drawable.button_states));
        setRGURL.setGravity(Gravity.CENTER_HORIZONTAL);
        setRGURL.setText("connect");
        setRGURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:finish check if URL connection is ok and valid
                URL_Register = urlForRegister.getText() + "";
                //RG
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    URL rgurl = new URL(URL_Register);

                    HttpURLConnection rgconnection = (HttpURLConnection) rgurl.openConnection();

                    if (rgconnection.getResponseCode() == 200) {
                        rgIndicator.setText("connected");
                        rgIndicator.setBackgroundColor(Color.GREEN);
                    } else {
                        rgIndicator.setText(rgconnection.getResponseCode() + rgconnection.getResponseMessage());
                        rgIndicator.setBackgroundColor(Color.RED);
                    }

                } catch (MalformedURLException e) {
                    Toast.makeText(getActivity(), "error: " + e.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        setLRURL = new Button(getActivity());
        setLRURL.setLayoutParams(btnlparams);
        setLRURL.setBackground(getResources().getDrawable(R.drawable.button_states));
        setLRURL.setGravity(Gravity.CENTER_HORIZONTAL);
        setLRURL.setText("connect");
        setLRURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:finish check if URL connection is ok and valid
                URL_LORI = urlForLORI.getText() + "";
                //LORI
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    URL loriurl = new URL(URL_LORI);

                    HttpURLConnection loriconnection = (HttpURLConnection) loriurl.openConnection();

                    if (loriconnection.getResponseCode() == 200) {
                        loriIndicator.setText("connected");
                        loriIndicator.setBackgroundColor(Color.GREEN);
                    } else {
                        loriIndicator.setText(loriconnection.getResponseCode() + loriconnection.getResponseMessage());
                        loriIndicator.setBackgroundColor(Color.RED);
                    }

                } catch (MalformedURLException e) {
                    Toast.makeText(getActivity(), "error: " + e.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        layout.addView( urlForRegister);
        layout.addView( rgIndicator);
        layout.addView(setRGURL);
        layout.addView( urlForLORI);
        layout.addView( loriIndicator);
        layout.addView(setLRURL);
        rootView = layout;

        return rootView;
    }
}
