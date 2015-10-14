package com.example.karen.lop_android;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

/**
 * Created by hebi5 on 10/11/2015.
 */
public class Page3Fragment extends Fragment {
    View rootView;
    LinearLayout.LayoutParams lparams;
    LinearLayout ll;
    TextView tv;
    TextView tv2;
    ImageView im;
    ImageView im2;
    ScrollView sv;
    VideoManager vm;
    AudioPlayer ap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vm = new VideoManager(getActivity(), Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/lo7/vid/sample.mp4"));
        try {
            ap = new AudioPlayer(getActivity(), Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/lo7/aud/samplemp3.mp3"));
            ap.setButtonStates(getResources().getDrawable(R.drawable.button_states), getResources().getDrawable(R.drawable.button_states));
        }catch(IOException e){}


        inflateViews();
        ll.addView(tv);
        //ll.addView(im);
        //ll.addView(tv2);
        //ll.addView(im2);
        ll.addView(vm.getPlayer());
        ll.addView(ap.getPlayer());

        //sv.addView(ll);
        rootView = ll;

        return rootView;
    }
    public void inflateViews(){

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        sv = new ScrollView(getActivity());
        sv.setVerticalScrollBarEnabled(true);
        //sv.setVerticalScrollBarEnabled(true);
        sv.setLayoutParams(lparams);

        ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));

        tv = new TextView(getActivity());
        tv.setText("THIS IS PAGE 3");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);

        im = new ImageView(getActivity());
        im.setImageDrawable(getResources().getDrawable(R.drawable.notbad));
        im2 = new ImageView(getActivity());

        tv2 = new TextView(getActivity());
        tv2.setText("THIS IS PAGE 3");
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextSize(20);

        im2.setImageDrawable(getResources().getDrawable(R.drawable.notbad));

    }
}
