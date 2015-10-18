package com.example.karen.lop_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by hebi5 on 10/17/2015.
 */
public class LearningObject {
    private String id;
    JSONArray sequenceArr;
    private String title;
    private ArrayList<LearningElement[]> page;
    ArrayList<LearningElement> learningElements;
    JSONArray pageJSON;

    public LearningObject(JSONObject json) throws JSONException {
        this.id = json.optString("id");
        sequenceArr = json.getJSONArray("sequence");
        this.title = json.optString("title");

        page = new ArrayList<>();
        for(int i=0;i<sequenceArr.length();i++){
            learningElements = new ArrayList<>();
            pageJSON =  sequenceArr.getJSONArray(i);
            for (int x=0;x<pageJSON.length();x++){
                learningElements.add(new LearningElement(pageJSON.getJSONObject(x)));
            }
            page.add(learningElements.toArray(new LearningElement[learningElements.toArray().length]));
        }
    }

    public String getTitle() {
        return title;
    }

    public JSONArray getPageJSON() {
        return pageJSON;
    }

    public ArrayList<LearningElement> getLearningElements() {
        return learningElements;
    }

    public ArrayList<LearningElement[]> getPage() {
        return page;
    }

    public LearningElement getElementInPage(int page, int elementNum){
        return this.page.get(page)[elementNum];
    }

    public ArrayList<LearningElement[]> getSequence() {
        return page;
    }

    public void setSequence(ArrayList<LearningElement[]> sequence) {
        this.page = sequence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
