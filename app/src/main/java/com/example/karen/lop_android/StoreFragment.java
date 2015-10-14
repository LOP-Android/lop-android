package com.example.karen.lop_android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by hebi5 on 8/22/2015.
 */
public class StoreFragment extends MyLibraryFragment {
    //tentative pani.
    View rootView;
    ListView lv;
    ExpandableListView elv;
    LinearLayout.LayoutParams lparams;
    LinearLayout ll;

    String[] loList = {
            "Algebra",
            "Calculus",
            "Physics II",
            "Java Programming",
            "C Programming",
            "Web Development"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(Color.WHITE);

        lv = new ListView(getActivity());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, loList);

        lv.setAdapter(adapter);

        ll.addView(lv);
        rootView = ll;
        return rootView;
    }
}


