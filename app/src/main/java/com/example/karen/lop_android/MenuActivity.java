package com.example.karen.lop_android;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MenuActivity extends ActionBarActivity {
    Fragment currentFrag;
    public MainMenuFragment mmf = new MainMenuFragment();
    ListView lv;
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

        setContentView(R.layout.activity_menu);

        lv = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sample_list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        currentFrag = new MyLibraryFragment();
                        replaceFragment(currentFrag);
                        break;
                    case 1:
                        currentFrag = new StoreFragment();
                        replaceFragment(currentFrag);
                        break;
                    case 5:
                        currentFrag = new AboutFragment();
                        replaceFragment(currentFrag);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        removeFragment(currentFrag);
    }

    public void removeFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
        lv.setVisibility(View.VISIBLE);
    }

    public void replaceFragment(Fragment fragment){
        lv.setVisibility(View.INVISIBLE);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
