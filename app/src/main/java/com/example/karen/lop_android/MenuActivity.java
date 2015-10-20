package com.example.karen.lop_android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Stack;


public class MenuActivity extends ActionBarActivity {
    public static Stack<Fragment> fragmentStack = new Stack<Fragment>();
    public static FragmentManager fragmentManager;
    public static Fragment currentFrag;
    private ListView lv;
    private int fragRemoved = 0;
    private String downloadLoURL = "http://192.168.1.43:8080/InformatronYX/informatron/user/get";
    JSONObject strRoot;
    JSONArray arr;
    String test = null;

    String[] sample_list = {
            "My Library",
            "LO Store",
            "Favorites",
            "Recent",
            "Settings",
            "About LOP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Welcome, "+LoginActivity.userSession.getFirstName()+" "+LoginActivity.userSession.getLastName());

        setContentView(R.layout.activity_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sample_list);
        lv.setAdapter(adapter);
        lv.setSelector(R.drawable.custom_list_selector);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0: setActionBarTitle("My Library");
                            addFragment(new MyLibraryFragment());break;
                    case 1: setActionBarTitle("LOStore");
                            addFragment(new StoreFragment());break;
                    case 4: setActionBarTitle("Settings");
                        addFragment(new SettingsFragment());break;
                    case 5: setActionBarTitle("About");
                            addFragment(new AboutFragment());break;
                }
                fragRemoved = 0;
            }
        });
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if(fragmentStack.empty()){
            super.onBackPressed();
        }
        else {
            if(fragmentStack.size() == 1){
                getSupportActionBar().setTitle("Welcome!");
            }
            removeFragment(fragmentStack.pop());
        }
    }

    public void removeFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
        lv.setVisibility(View.VISIBLE);
    }

    public void addFragment(Fragment fragment){
        if(!fragmentStack.contains(fragment)) {
            lv.setVisibility(View.INVISIBLE);
            fragmentStack.push(fragment);
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //item.setVisible(true);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        else {
            return super.onOptionsItemSelected(item);
        }
    }
}
