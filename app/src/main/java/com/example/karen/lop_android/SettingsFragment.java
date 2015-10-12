package com.example.karen.lop_android;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by hebi5 on 10/9/2015.
 */
public class SettingsFragment extends Fragment {

    private LinearLayout.LayoutParams lparams;
    private LinearLayout layout;
    private View rootView;
    private Button setURL;
    private EditText e;

    public static String URLString = "";
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //rootView = inflater.inflate(R

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        lparams.gravity = Gravity.CENTER_HORIZONTAL;
        lparams.setMargins(0,100,0,50);

        layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(lparams);

        e = new EditText(getActivity());
        e.setGravity(Gravity.CENTER_HORIZONTAL);
        e.setHint("input url here");
        setURL = new Button(getActivity());
        setURL.setBackground(getResources().getDrawable(R.drawable.button_states));
        setURL.setGravity(Gravity.CENTER_HORIZONTAL);
        setURL.setText("connect");

        setURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URLString = e.getText()+"";
                Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
            }
        });


        layout.addView(e);
        layout.addView(setURL);
        rootView = layout;

        return rootView;
    }
}
