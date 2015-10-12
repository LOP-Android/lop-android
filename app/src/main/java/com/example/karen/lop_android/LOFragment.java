package com.example.karen.lop_android;

import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

/**
 * Created by hebi5 on 10/1/2015.
 */
public class LOFragment extends Fragment {

    View rootView;
    LinearLayout.LayoutParams lparams;
    LinearLayout ll;
    ImageView im;
    ImageView im2;
    ScrollView sv;
    VideoManager vm;
    AudioPlayer ap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //rootView = inflater.inflate(R.layout.lo_fragment_layout, container, false);

        vm = new VideoManager(getActivity(), Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/lo7/vid/sample.mp4"));
        try {
            ap = new AudioPlayer(getActivity(), Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/lo7/aud/samplemp3.mp3"));
        }catch(IOException e){}


        inflateViews();
        ll.addView(im);
        ll.addView(im2);
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
        sv.setBackgroundColor(Color.RED);
        //sv.setVerticalScrollBarEnabled(true);
        sv.setLayoutParams(lparams);

        ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(Color.BLUE);
        ll.setLayoutParams(lparams);
        //ll.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));

        im = new ImageView(getActivity());
        //im.setImageDrawable(getResources().getDrawable(R.drawable.android));
        im2 = new ImageView(getActivity());
        //im2.setImageDrawable(getResources().getDrawable(R.drawable.android));

    }
}
