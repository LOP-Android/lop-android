package com.example.karen.lop_android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Karen on 8/21/2015.
 */
public class MyLibraryFragment extends Fragment {
    View rootView;
    EditText searchBar;
    ListView lv;
    Animation anim;
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
        rootView = inflater.inflate(R.layout.base_layout, container, false);

        anim = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_out_right);
        anim.setDuration(500);


        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lv = new ListView(getActivity());
        searchBar = new EditText(getActivity());
        searchBar.setHint("Search for LOs");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, loList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lv.getChildAt(position).startAnimation(anim);
            }
        });

        LinearLayout ll = new LinearLayout(getActivity());
        ll.setLayoutParams(lparams);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(searchBar);
        ll.addView(lv);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> newList = new ArrayList<String>();
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                for(int i = 0; i < loList.length; i++){

                    if(loList[i].toLowerCase().contains((s+"").toLowerCase())){
                        newList.add(loList[i]);
                    }
                }
                lv.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, newList));
            }

            @Override
            public void afterTextChanged(Editable s) {}

        });


        rootView = ll;
        return rootView;
    }
}
