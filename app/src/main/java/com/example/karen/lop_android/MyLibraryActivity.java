package com.example.karen.lop_android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjjjunjie on 9/25/2015.
 */
public class MyLibraryActivity extends ActionBarActivity {

    private View rootView;
    private EditText searchBar;
    private ListView lo_listView;
    private ImageButton open;
    private String[] loList = {
            "Sample LO"};
    private LinearLayout.LayoutParams lparams;
    private LinearLayout ll;

    Button buttonOpenDialog;
    Button buttonUp;
    TextView textFolder;

    String KEY_TEXTPSS = "TEXTPSS";
    static final int CUSTOM_DIALOG_ID = 0;
    ListView dialog_ListView;

    File root;
    File curFolder;

    private List<String> fileList = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inflateViews();

        lo_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: Intent i = new Intent(getApplicationContext(), LOPlayerActivity.class);
                        startActivity(i);
                }
            }
        });

        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        curFolder = root;
        this.setContentView(ll);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void inflateViews(){


        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        lo_listView = new ListView(getApplicationContext());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.custom_list_item, R.id.text1, loList);
        lo_listView.setSelector(R.drawable.custom_list_selector);
        lo_listView.setAdapter(arrayAdapter);

        searchBar = new EditText(getApplicationContext());
        searchBar.setHint("Search for LOs");

        ll = new LinearLayout(getApplicationContext());
        ll.setLayoutParams(lparams);
        ll.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));
        ll.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams btnlparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnlparams.gravity = Gravity.CENTER_HORIZONTAL;

        open = new ImageButton(getApplicationContext());
        open.setBackground(getResources().getDrawable(R.drawable.button_states));
        open.setImageDrawable(getResources().getDrawable(R.drawable.folderpng));
        open.setLayoutParams(btnlparams);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CUSTOM_DIALOG_ID);
                buttonUp.setText("back");
            }
        });

        ll.addView(searchBar);
        ll.addView(lo_listView);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> newList = new ArrayList<String>();
                for(int i = 0; i < loList.length; i++){

                    if(loList[i].toLowerCase().contains((s+"").toLowerCase())){
                        newList.add(loList[i]);
                    }
                }
                lo_listView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.custom_list_item, R.id.text1,newList));
            }

            @Override
            public void afterTextChanged(Editable s) {}

        });

    }

    @Override
    public Dialog onCreateDialog(int id) {
        Dialog dialog = null;

        switch(id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_layout);
                dialog.setTitle("Manage Files");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                textFolder = (TextView) dialog.findViewById(R.id.folder);
                buttonUp = (Button) dialog.findViewById(R.id.up);
                buttonUp.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ListDir(curFolder.getParentFile());
                    }
                });

                dialog_ListView = (ListView) dialog.findViewById(R.id.dialogList);
                dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File selected = new File(fileList.get(position));
                        if(selected.isDirectory()) {
                            ListDir(selected);
                        } else {
                            Toast.makeText(getApplicationContext(),selected.toString() + "selected",
                                    Toast.LENGTH_LONG).show();
                            //error cannot resolve method dismissDialog(int);
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }
                    }
                });
                break;
        }
        return dialog;
    }
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case CUSTOM_DIALOG_ID:
                ListDir(curFolder);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        else if (id == R.id.action_folders) {
            showDialog(CUSTOM_DIALOG_ID);
            buttonUp.setText("back");
            return true;
        }

        else {
            return super.onOptionsItemSelected(item);
        }
    }

    void ListDir(File f) {
        if(f.equals(root)) {
            buttonUp.setEnabled(false);
        }
        else {
            buttonUp.setEnabled(true);
        }
        curFolder = f;
        textFolder.setText(f.getPath());

        File[] files = f.listFiles();
        fileList.clear();

        for(File file: files) {
            fileList.add((file.getPath()));
        }

        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(getApplicationContext(), R.layout.folder_list,R.id.folder_dir_txt,fileList);
        dialog_ListView.setAdapter(directoryList);

    }
}
