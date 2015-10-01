package com.example.karen.lop_android;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by hebi5 on 10/1/2015.
 */
public class AudioPlayer {
    MediaPlayer player;
    Button playPauseButton;
    Button stopButton;
    TextView title;
    LinearLayout playerControls;
    public AudioPlayer(Context context, Uri uri) throws IOException {
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDataSource(context, uri);
        player.prepare();
        initComponents(context);
    }
    public LinearLayout getPlayer(){
        LinearLayout temp = new LinearLayout(playerControls.getContext());
        temp.setOrientation(LinearLayout.VERTICAL);
        temp.addView(title);
        temp.addView(playerControls);
        return  temp;
    }
    public void setTitle(String s){ title.setText(s);}
    void initComponents(Context context){
        playPauseButton = new Button(context);
        playPauseButton.setText("PLAY/PAUSE");
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying())
                    player.pause();
                else
                    player.start();
            }
        });
        stopButton = new Button(context);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(0);
                player.pause();
            }
        });
        stopButton.setText("STOP");
        playerControls = new LinearLayout(context);
        playerControls.addView(playPauseButton);
        playerControls.addView(stopButton);
        title = new TextView(context);
    }
}
