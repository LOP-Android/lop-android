package com.example.karen.lop_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hebi5 on 10/17/2015.
 */
public class LearningElement {
    private String id;
    private String fileExtension;

    public LearningElement(JSONObject json) throws JSONException {
        this.id = json.optString("id");
        this.fileExtension = json.optString("fileExtension");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
