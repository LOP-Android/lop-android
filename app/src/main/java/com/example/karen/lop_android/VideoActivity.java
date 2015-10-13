package com.example.karen.lop_android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;


public class VideoActivity extends Activity {

    LinearLayout.LayoutParams lparams;
    LinearLayout ll;
    public static VideoManager videoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));

        //ll.addView(videoManager.getPlayer());

        this.setContentView(ll);
    }

    public void setVideoManager(VideoManager vm){
        this.videoManager = vm;
    }
}
