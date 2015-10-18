package com.example.karen.lop_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hebi5 on 10/16/2015.
 */
public class UserSession {
    public String id;
    JSONArray liableLOArray;
    public ArrayList<LearningObject> liableLOList;

    public UserSession(JSONObject json) throws JSONException {
        this.id = json.optString("id");
        this.liableLOArray = json.getJSONArray("liableLearningObjects");

        liableLOList = new ArrayList<>();
        for(int i=0;i<liableLOArray.length();i++){
            liableLOList.add(new LearningObject(liableLOArray.getJSONObject(i)));
        }
    }

    public ArrayList<LearningObject> getLiableLOList() {
        return liableLOList;
    }

    public void setLiableLOList(ArrayList<LearningObject> liableLOList) {
        this.liableLOList = liableLOList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
