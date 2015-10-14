package com.example.karen.lop_android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hebi5 on 8/22/2015.
 */
public class AboutFragment extends Fragment {
    private LinearLayout.LayoutParams lparams;
    private LinearLayout ll;
    private ImageView im;
    private View rootView;
    private TextView aboutTxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));

        aboutTxt = new TextView(getActivity());
        aboutTxt.setText("Learning Object Player is a bla bla bla..\n\n\nVersion 1.0");

        im = new ImageView(getActivity());
        im.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        im.setImageDrawable(getResources().getDrawable(R.drawable.about));

        ll.addView(aboutTxt);
        ll.addView(im);

        rootView = ll;
        return rootView;
    }
}
