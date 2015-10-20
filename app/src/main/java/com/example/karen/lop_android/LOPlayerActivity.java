package com.example.karen.lop_android;

import android.animation.LayoutTransition;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;


public class LOPlayerActivity extends ActionBarActivity {

    private boolean flagVideo;
    private String folder_path = "lo7";
    public static int lo_number;
    private int currentPage = 0;
    private ScrollView scrollView;
    private LinearLayout pageContainer;
    private ImageButton forward;
    private ImageButton back;
    private String loName;
    private ImageButton evaluate;

    private int pageCount = LoginActivity.userSession.getLiableLOList().get(lo_number).getPage().size();
    private LinearLayout[] layouts = new LinearLayout[pageCount];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loplayer);
        loName = LoginActivity.userSession.getLiableLOList().get(lo_number).getTitle();
        scrollView = new ScrollView(this);
        scrollView.setVerticalScrollBarEnabled(true);
        scrollView.setLayoutTransition(new LayoutTransition());
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        pageContainer = (LinearLayout)findViewById(R.id.pageContainer);
        getSupportActionBar().setTitle(loName);
        evaluate = (ImageButton)findViewById(R.id.evaluate);
        evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new LORIFragment());
            }
        });


        pageContainer.setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeLeft() {
                if(currentPage<pageCount-1){
                    if(flagVideo){
                        currentPage++;
                        pageContainer.removeAllViews();
                        pageContainer.addView(layouts[currentPage]);
                    }
                    else{
                        currentPage++;
                        scrollView.removeAllViews();
                        scrollView.addView(layouts[currentPage]);
                    }
                }
            }

            @Override
            public void onSwipeRight() {
                if(currentPage>0){
                    if(flagVideo){
                        currentPage--;
                        pageContainer.removeAllViews();
                        pageContainer.addView(layouts[currentPage]);
                    }
                    else{
                        currentPage--;
                        scrollView.removeAllViews();
                        scrollView.addView(layouts[currentPage]);
                    }
                }
            }
        });

        scrollView.setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeLeft() {
                if(currentPage<pageCount-1){
                    if(flagVideo){
                        currentPage++;
                        pageContainer.removeAllViews();
                        pageContainer.addView(layouts[currentPage]);
                    }
                    else{
                        currentPage++;
                        scrollView.removeAllViews();
                        scrollView.addView(layouts[currentPage]);
                    }
                }
            }

            @Override
            public void onSwipeRight() {
                if(currentPage>0){
                    if(flagVideo){
                        currentPage--;
                        pageContainer.removeAllViews();
                        pageContainer.addView(layouts[currentPage]);
                    }
                    else{
                        currentPage--;
                        scrollView.removeAllViews();
                        scrollView.addView(layouts[currentPage]);
                    }
                }
            }
        });

        forward = (ImageButton)findViewById(R.id.forward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage<pageCount-1){
                    if(flagVideo){
                        currentPage++;
                        pageContainer.removeAllViews();
                        pageContainer.addView(layouts[currentPage]);
                    }
                    else{
                        currentPage++;
                        scrollView.removeAllViews();
                        scrollView.addView(layouts[currentPage]);
                    }

                }
            }
        });
        back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPage>0){
                    if(flagVideo){
                        currentPage--;
                        pageContainer.removeAllViews();
                        pageContainer.addView(layouts[currentPage]);
                    }
                    else{
                        currentPage--;
                        scrollView.removeAllViews();
                        scrollView.addView(layouts[currentPage]);
                    }
                }
            }
        });
        initLayouts();
        addViewsToLayout();

        if(flagVideo){
            pageContainer.addView(layouts[currentPage]);
        }else{
            scrollView.addView(layouts[currentPage]);
            pageContainer.addView(scrollView);
        }
    }

    public void addFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    public void initLayouts(){
        for(int i=0;i<pageCount;i++){
            layouts[i] = new LinearLayout(this);
            layouts[i].setOrientation(LinearLayout.VERTICAL);
        }
    }

    public void addViewsToLayout(){
        for(int p=0;p< pageCount;p++){

            LearningElement[] learningElements = LoginActivity.userSession.getLiableLOList().get(lo_number).getPage().get(p);

            for(int i=0;i<learningElements.length;i++){

                LearningElement element =  learningElements[i];
                String fileExtension = element.getFileExtension();
                String name = element.getId();

                switch(fileExtension){
                    case ".txt":
                        TextView tv = new TextView(this);
                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/"+folder_path+"/"+ loName+"/"+ name + fileExtension);
                        StringBuilder text = new StringBuilder();
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String line;
                            while ((line = br.readLine()) != null) {
                                text.append(line);
                                text.append('\n');
                            }
                            br.close();
                        }
                        catch (IOException e) {
                            //You'll need to add proper error handling here
                        }
                        tv.setText(text);
                        layouts[p].addView(tv);
                        break;
                    case ".png":
                        ImageView im = new ImageView(this);
                        im.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/"+folder_path+"/"+ loName+"/"+ name + fileExtension));
                        layouts[p].addView(im);
                        break;
                    case ".mp3":
                        try {
                            AudioPlayer ap = new AudioPlayer(this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/"+folder_path+"/"+ loName+"/"+name));
                            ap.setButtonStates(getResources().getDrawable(R.drawable.button_states), getResources().getDrawable(R.drawable.button_states));
                            layouts[p].addView(ap.getPlayer());
                        }catch(IOException e){}
                        break;
                    case ".mp4":
                        VideoManager vm = new VideoManager(this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/"+folder_path+"/"+ loName+"/"+name+fileExtension));
                        flagVideo = true;
                        layouts[p].addView(vm.getPlayer());
                        break;
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loplayer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}
