package com.example.karen.lop_android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by hebi5 on 10/11/2015.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private int pageCount;
    public View pageView;
    AudioPlayer ap;
    LinearLayout.LayoutParams lparams;
    ScrollView sv;
    LinearLayout ll;
    ImageView im;
    Context context;

    public MyPagerAdapter(Context ctx, FragmentManager fm, int pageCount) {
        super(fm);
        this.context = ctx;
        this.pageCount = pageCount;

        initPageView(pageCount);
    }

    public void initPageView(int pageCount) {

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        sv = new ScrollView(context);
        sv.setVerticalScrollBarEnabled(true);
        sv.setLayoutParams(lparams);

        ll = new LinearLayout(context);
        ll.setPadding(20,20,20,20);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(Color.WHITE);

        for(int p=0;p<1;p++){

            LearningElement[] page = LoginActivity.userSession.getLiableLOList().get(LOPlayerActivity.lo_number).getPage().get(p);

            for(int i=0;i<page.length;i++){

                LearningElement element =  page[i];
                String fileExtension = element.getFileExtension();
                String name = element.getId();

                Toast.makeText(context, "page: "+p+" filename: "+name+fileExtension, Toast.LENGTH_SHORT).show();

                switch(fileExtension){
                    case ".png":
                        im = new ImageView(context);
                        im.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/lo7/"+name+fileExtension));
                        ll.addView(im);
                        break;
                    case ".mp3":
                        try {
                            ap = new AudioPlayer(context, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/lo7/"+name));
                        }catch(IOException e){}
                        ll.addView(ap.getPlayer());
                        break;
                }
            }
        }

        pageView = ll;
    }

    public View getPageView(){
        return this.pageView;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0: return new Page1Fragment();
            case 1: return new Page2Fragment();
            case 2: return new Page3Fragment();
            case 3: return new LORI();
            default: break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
