package com.example.karen.lop_android;

import android.app.Fragment;
import android.content.Intent;
import android.os.StrictMode;
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
    private Fragment currentFrag;
    private MainMenuFragment mmf = new MainMenuFragment();
    private ListView lv;
    private int fragRemoved = 0;
    private String downloadLoURL = "http://192.168.1.43:8080/InformatronYX/informatron/LO/availableLearningObjects";
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

        setContentView(R.layout.activity_menu);

        lv = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sample_list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:currentFrag = new MyLibraryFragment();
                        replaceFragment(currentFrag);break;
//                    case 1: currentFrag = new DownloadLOFragment();
//                        replaceFragment(currentFrag);break;
                    case 1: if(testDownloadJSONObject() != null){

                            Toast.makeText(getApplicationContext(),testDownloadJSONObject().toString(),Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"FUCK U",Toast.LENGTH_LONG).show();

                    }
                        break;
                    case 2:currentFrag = new StoreFragment();
                        replaceFragment(currentFrag);break;
                    case 5:currentFrag = new AboutFragment();
                        replaceFragment(currentFrag);break;
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
        if(fragRemoved == 1){
            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            removeFragment(currentFrag);
        }
    }

    public void removeFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
        lv.setVisibility(View.VISIBLE);
        fragRemoved = 1;
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
