package com.example.karen.lop_android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Karen on 8/20/2015.
 */
public class MainMenuFragment extends Fragment {
    private View rootView;
    private LinearLayout ll;
    private ListView lv;
    private String[] sample_list = {
            "My Library",
            "LO Store",
            "Favorites",
            "Recent",
            "Settings",
            "About LOP"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_menu_layout, container, false);

        ll = new LinearLayout(getActivity());
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        lv = new ListView(getActivity());
        lv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sample_list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            MenuActivity menu = new MenuActivity();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(), "item "+position+" is clicked", Toast.LENGTH_LONG).show();

                switch(position){
                    case 0:getFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyLibraryFragment()).commit();break;
                    case 1:break;
                    case 2:break;
                    case 3:break;
                    case 4:break;
                    case 5:break;
                }
            }
        });

        ll.addView(lv);
        rootView = ll;
        return rootView;
    }
}
