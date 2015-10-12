/**
 *  Author: Prince Niko U. Garces
 *  Date Created: 8/22/2015
 *  Revisions:
 *      8/22/2015 - initially added the fucking code ~ by Author
 *
 *
 *  Version 1.0
 *
 *  **Note: kuya cagot naa sa getChildView na override
 *          method and download na button, adto lang pag
 *          test2 haha. Ug ikaw nalay himo sa download
 *          na class kay ikaw may mag buot unsa ang iyang
 *          attributes.
 */

package com.example.karen.lop_android;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class LORIInfoAdapter extends BaseExpandableListAdapter {
    private JSONArray jsonArr = new JSONArray();
    private Context context = null;
    private String[] lo = null;

    public LORIInfoAdapter(Context context, String[] lo){
        this.context = context;
        this.lo = lo;
    }

    public JSONArray getJSONArray(){

        return this.jsonArr;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String lo_name = (String)getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_list_1, parent, false);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.lo_name);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setText(lo_name);
        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lo[groupPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return (lo!=null)?lo.length:0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String lo_info = (String)getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_list_2, parent, false);
        }
        TextView evalDesc = (TextView)convertView.findViewById(R.id.evalDesc);
        final TextView pbarValue = (TextView)convertView.findViewById(R.id.pbarValue);
        final SeekBar sb = (SeekBar)convertView.findViewById(R.id.seekBar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pbarValue.setText(progress + "");
                try {
                    jsonArr.put(groupPosition, progress);
                }catch(JSONException e){}
                //Toast.makeText(context, getJSONArray()+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        evalDesc.setText("INPUT EVALUATION DESCRIPTION HERE");
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
