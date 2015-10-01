package com.example.karen.lop_android;

import android.content.Context;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by hebi5 on 10/1/2015.
 */
public class VideoManager {
    public VideoView myVideoView;
    private int position = 0;
    private MediaController mediaControls;

    public VideoManager(Context context,Uri uri) {
        if (mediaControls == null) {
            mediaControls = new MediaController(context);
        }
        myVideoView = new VideoView(context);
        try {
            myVideoView.setMediaController(mediaControls);
            mediaControls.setMediaPlayer(myVideoView);
            myVideoView.setVideoURI(uri);

        } catch (Exception e) {
            e.printStackTrace();
        }
        myVideoView.requestFocus();
    }
    public VideoView getPlayer(){
        VideoView v = null;
        if(myVideoView!=null)
            v= myVideoView;
        return v;}
}
