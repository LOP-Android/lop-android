package com.example.karen.lop_android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


public class MenuActivity extends ActionBarActivity {
    public static Stack<Fragment> fragmentStack = new Stack<Fragment>();
    public static FragmentManager fragmentManager;
    public static Fragment currentFrag;
    private MainMenuFragment mmf = new MainMenuFragment();
    private ListView lv;
    private int fragRemoved = 0;
    private String downloadLoURL = "http://192.168.1.43:8080/InformatronYX/informatron/user/get";
    JSONObject strRoot;
    JSONArray arr;
    String test = null;

    String[] sample_list = {
            "My Library",
            "Download LO",
            "LO Store",
            "Favorites",
            "Recent",
            "Settings",
            "About LOP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Welcome!");

        setContentView(R.layout.activity_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sample_list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0: getSupportActionBar().setTitle("My Library");
                            addFragment(new MyLibraryFragment());break;
//                    case 1: currentFrag = new DownloadLOFragment();
//                        replaceFragment(currentFrag);break;
                    case 1: getSupportActionBar().setTitle("Download");
                            addFragment(new DownloadLOFragment());


                        break;
                    case 2: getSupportActionBar().setTitle("LOStore");
                            addFragment(new StoreFragment());break;
                    case 5: getSupportActionBar().setTitle("Settings");
                            addFragment(new SettingsFragment());break;
                    case 6: getSupportActionBar().setTitle("About");
                            addFragment(new AboutFragment());break;
                }
                fragRemoved = 0;
            }
        });
    }

    public JSONObject testDownloadJSONObject(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        HttpPost myConnection = new HttpPost(downloadLoURL);


        try {
            response = myClient.execute(myConnection);
            test = EntityUtils.toString(response.getEntity(), "UTF-8");
            arr = new JSONArray(test);
            strRoot = arr.getJSONObject(0);


        }
        catch (IOException e) {
            Toast.makeText(getApplicationContext(),"FUCK U "+e.toString(),Toast.LENGTH_LONG).show();}
        catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"FUCK U "+e.toString(),Toast.LENGTH_LONG).show();}

        //startProgressBar();
        return strRoot;
    }



    @Override
    public void onBackPressed() {
        if(fragmentStack.empty()){
            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
            startActivity(intent);
            //NavUtils.navigateUpFromSameTask(getParent());
        }
        else {
            if(fragmentStack.size() == 1){
                getSupportActionBar().setTitle("Welcome!");
            }
            removeFragment(fragmentStack.pop());
            //getFragmentManager().popBackStack();
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
        lv.setVisibility(View.INVISIBLE);
        fragmentStack.push(fragment);
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
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
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        else if (id == R.id.action_settings) {
            return true;
        }

        else {
            return super.onOptionsItemSelected(item);
        }
    }
}
