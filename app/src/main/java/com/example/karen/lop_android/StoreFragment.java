package com.example.karen.lop_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by hebi5 on 8/22/2015.
 */
public class StoreFragment extends MyLibraryFragment {
    //tentative pani.
    private View rootView;
    private ListView lv;
    private Animation anim;
    private ExpandableListView elv;
    private LinearLayout.LayoutParams lparams;
    private LinearLayout ll;

    String[] loList = {
            "Sample LO",
            "Algebra",
            "Calculus",
            "Physics II",
            "Java Programming",
            "C Programming",
            "Web Development"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        anim = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
        anim.setDuration(500);

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));

        lv = new ListView(getActivity());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, loList);
        lv.setAdapter(adapter);
        lv.setSelector(R.drawable.custom_list_selector);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lv.getChildAt(position).startAnimation(anim);
                switch(position) {
                    case 0: MenuActivity.currentFrag = new DialogFragmentTestFragment();
                            addFragment(MenuActivity.currentFrag);break;
                        //startActivity(new Intent(getActivity(), DialogFragmentTestFragment.class));
                }

            }
        });

        ll.addView(lv);
        rootView = ll;
        return rootView;
    }
}


