package com.example.karen.lop_android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hebi5 on 8/22/2015.
 */
public class AboutFragment extends Fragment {
    View rootView;
    TextView aboutTxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.base_layout, container, false);

        aboutTxt = new TextView(getActivity());
        aboutTxt.setText("Learning Object Player is a bla bla bla..\n\n\nVersion 1.0");

        rootView = aboutTxt;
        return rootView;
    }
}
