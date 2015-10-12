package com.example.karen.lop_android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

/**
 * Created by hebi5 on 8/22/2015.
 */
public class StoreFragment extends MyLibraryFragment {
    View rootView;
    ExpandableListView elv;
    LinearLayout.LayoutParams lparams;
    LinearLayout ll;

    String[] loList = {
            "Algebra",
            "Calculus",
            "Physics II",
            "Java Programming",
            "C Programming",
            "Web Development",
            "How To Speak Nihingo",
            "How To Speak Nihingo",
            "How To Speak Nihingo",
            "How To Speak Nihingo",
            "How To Speak Nihingo",
            "How To Speak Nihingo",
            "How To Speak Nihingo"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.explist_base_layout, container, false);

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(Color.WHITE);

        //elv = (ExpandableListView)getActivity().findViewById(R.id.exp_list);
        elv = new ExpandableListView(getActivity());

        elv.setAdapter(new LORIInfoAdapter(getActivity(), loList));

        ll.addView(elv);
        rootView = ll;
        return rootView;
    }
}


