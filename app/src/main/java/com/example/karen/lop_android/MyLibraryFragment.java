package com.example.karen.lop_android;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

/**
 * Created by Karen on 8/21/2015.
 */
public class MyLibraryFragment extends Fragment {
    private int fragRemoved = 0;
    private LinearLayout.LayoutParams lparams;
    private View rootView;
    private EditText searchBar;
    private ListView lv;
    private ImageButton open;
    private Animation anim;
    private String rootFolderLO = "lo7";
    private LinearLayout ll;
    private ArrayList<String> loTitleList = new ArrayList<>();
    private ArrayList<String> loSubtitleList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createTitleList();

        final int lo_number = 0;

        for(int p=0;p< LoginActivity.userSession.getLiableLOList().get(lo_number).getPage().size();p++){

            LearningElement[] page = LoginActivity.userSession.getLiableLOList().get(lo_number).getPage().get(p);

            for(int i=0;i<page.length;i++){

                LearningElement element =  page[i];
                String fileExtension = element.getFileExtension();
                String name = element.getId();

                //Toast.makeText(getActivity(), "page: "+p+" filename: "+name+fileExtension, Toast.LENGTH_SHORT).show();
           }
        }

        inflateViews();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int loListCount = LoginActivity.userSession.getLiableLOList().size();
                for(int i = 0; i < loListCount;i++) {
                    if(position == i){
                        startActivity(new Intent(getActivity(), LOPlayerActivity.class));
                        LOPlayerActivity.lo_number = i;
                        break;
                    }
                }
            }
        });

        rootView = ll;
        setHasOptionsMenu(true);
        return rootView;
    }

    public void createTitleList(){

        for(int i=0;i<LoginActivity.userSession.getLiableLOList().size(); i++){
            loTitleList.add(LoginActivity.userSession.getLiableLOList().get(i).getTitle());
            loSubtitleList.add(Environment.getExternalStorageDirectory()+"/"+rootFolderLO+"/"+LoginActivity.userSession.getLiableLOList().get(i).getTitle());
       }
    }

    public void addFragment(Fragment fragment){
        MenuActivity.fragmentStack.push(fragment);
        getActivity().getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void inflateViews(){


        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        lv = new ListView(getActivity());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, loTitleList);
        lv.setAdapter(adapter);
        lv.setSelector(getResources().getDrawable(R.drawable.custom_list_selector));

        searchBar = new EditText(getActivity());
        searchBar.setHint("Search for LOs");

        ll = new LinearLayout(getActivity());
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(searchBar);
        ll.addView(lv);

        /*searchBar.addTextChangedListener(new TextWatcher() {
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

        });*/

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_library, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_folders:
                MyDialog myDialog = new MyDialog();
                myDialog.show(getActivity().getFragmentManager(), "Folders");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
