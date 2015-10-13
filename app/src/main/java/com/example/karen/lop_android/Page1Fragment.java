package com.example.karen.lop_android;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

/**
 * Created by hebi5 on 10/11/2015.
 */
@SuppressWarnings("deprecation")
public class Page1Fragment extends Fragment {
    View rootView;
    LinearLayout.LayoutParams lparams;
    LinearLayout ll;
    LinearLayout ll2;
    LinearLayout videoLayout;
    Button showVid;
    TextView tv;
    TextView tv2;
    ImageView im;
    ImageView im2;
    GridView gv;
    ScrollView sv;
    VideoManager vm;
    AudioPlayer ap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vm = new VideoManager(getActivity(), Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/lo7/vid/sample.mp4"));
        try {
            ap = new AudioPlayer(getActivity(), Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/lo7/aud/samplemp3.mp3"));
        }catch(IOException e){}

        inflateViews();
        //ll2.addView(vm.getPlayer());
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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void inflateViews(){

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        gv = new GridView(getActivity());
        gv.setVerticalScrollBarEnabled(true);
        gv.setLayoutParams(lparams);

        sv = new ScrollView(getActivity());
        sv.setVerticalScrollBarEnabled(true);
        sv.setLayoutParams(lparams);

        ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));

        ll2 = new LinearLayout(getActivity());
        ll2.setOrientation(LinearLayout.VERTICAL);
        ll2.setLayoutParams(lparams);
        ll2.setBackgroundColor(Color.RED);

        tv = new TextView(getActivity());
        tv.setText("THIS IS PAGE 1");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);

        im = new ImageView(getActivity());
        im.setImageDrawable(getResources().getDrawable(R.drawable.penguin));

        tv2 = new TextView(getActivity());
        tv2.setText("THIS IS PAGE 1");
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextSize(20);

        im2 = new ImageView(getActivity());
        im2.setImageDrawable(getResources().getDrawable(R.drawable.penguin));

        showVid = new Button(getActivity());
        showVid.setText("play video");
        showVid.setBackground(getResources().getDrawable(R.drawable.button_states));

        showVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    public void addFragment(android.app.Fragment fragment){
        MenuActivity.fragmentStack.push(fragment);
        getActivity().getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }
}
