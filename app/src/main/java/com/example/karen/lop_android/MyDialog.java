package com.example.karen.lop_android;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hebi5 on 10/17/2015.
 */
public class MyDialog extends DialogFragment {

    View rootView;

    TextView textFolder;
    Button buttonUp;
    Button createFolder;
    EditText folderName;

    File root;
    File curFolder;

    ListView dialog_ListView;
    private List<String> fileList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_layout, container, false);


        dialog_ListView = (ListView)rootView.findViewById(R.id.dialogList);
        textFolder = (TextView)rootView.findViewById(R.id.folder);
        buttonUp = (Button)rootView.findViewById(R.id.paluyo);

        buttonUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ListDir(curFolder.getParentFile());
            }
        });
        folderName =(EditText)rootView.findViewById(R.id.folder_name);
        createFolder=(Button)rootView.findViewById(R.id.create_folder);
        createFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "ni sud", Toast.LENGTH_LONG).show();

                File rootDirectory = new File(Environment.getExternalStorageDirectory(), folderName.getText().toString());

                if(!rootDirectory.exists()){
                    Toast.makeText(getActivity(),rootDirectory.mkdir()+"",Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File selected = new File(fileList.get(position));
                if(selected.isDirectory()) {
                    ListDir(selected);
                } else {
                    Toast.makeText(getActivity(),selected.toString() + " mao ni siya",
                            Toast.LENGTH_LONG).show();
                    getDialog().dismiss();
                }
            }
        });


        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        curFolder = root;

        return rootView;
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

        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(getActivity(), R.layout.folder_list,R.id.folder_dir_txt,fileList);
        dialog_ListView.setAdapter(directoryList);

    }
}
