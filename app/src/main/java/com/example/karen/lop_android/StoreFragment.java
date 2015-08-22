package com.example.karen.lop_android;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hebi5 on 8/22/2015.
 */
public class StoreFragment extends MyLibraryFragment {
    View rootView;
    ExpandableListView elv;

    String[] loList = {
            "Algebra",
            "Calculus",
            "Physics II",
            "Java Programming",
            "C Programming",
            "Web Development",
            "How To Speak Nihingo"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.explist_base_layout, container, false);


        //elv = (ExpandableListView)getActivity().findViewById(R.id.exp_list);
        elv = new ExpandableListView(getActivity());

        elv.setAdapter(new LOInfoAdapter(getActivity(), loList));

        rootView = elv;
        return rootView;
    }
}


