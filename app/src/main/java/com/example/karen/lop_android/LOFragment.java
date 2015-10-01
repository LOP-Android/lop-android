package com.example.karen.lop_android;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by hebi5 on 10/1/2015.
 */
public class LOFragment extends Fragment {

    View rootView;
    LinearLayout.LayoutParams lparams;
    LinearLayout ll;
    TextView tv;
    VideoManager vm;
    AudioPlayer ap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //rootView = inflater.inflate(R.layout.base_layout, container, false);

        vm = new VideoManager(getActivity(), Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/lo7/vid/sample.mp4"));
        try {
            ap = new AudioPlayer(getActivity(), Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/lo7/aud/samplemp3.mp3"));
        }catch(IOException e){}

        inflateViews();
        ll.addView(vm.getPlayer());
        ll.addView(ap.getPlayer());


        rootView = ll;
        return rootView;
    }

    public void inflateViews(){

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        ll = new LinearLayout(getActivity());
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));
        ll.setOrientation(LinearLayout.VERTICAL);

    }
}
